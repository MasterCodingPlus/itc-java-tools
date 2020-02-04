package itc.common.tools.slidingwindow;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.subjects.BehaviorSubject;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/**
 * @ProjectName: java-tools
 * @Package: itc.common.tools.slidingwindow
 * @ClassName: SlidingWindowCounter
 * @Description: 滑动窗口
 * @Author: Mastercoding
 * @CreateDate: 2019/5/8 10:55
 * ***********************************************************
 * @UpdateUser: Mastercoding
 * @UpdateDate: 2019/5/8 10:55
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * ***********************************************************
 * Copyright: Copyright (c) 2019
 **/
public class SlidingWindowCounter {

    public static final Func2<Long, Long, Long> PUBLIC_SUM = (long1, long2) -> long1 + long2;
    /**
     * observable类似list是个数据集  scan将数累加起来
     * 例如 1,2,3,4,5 ->scan->1,3,4,10,15
     * 阻塞
     **/
    public static final Func1<Observable<Long>, Observable<Long>> WINDOW_SUM = window -> window.scan(PUBLIC_SUM);

    /***
     * reduce将数据集进行合并操作
     * 阻塞
     * **/
    public static final Func1<Observable<Long>, Observable<Long>> INNER_BUCKET_SUM = integerObservable -> integerObservable.reduce(Long.valueOf(0), PUBLIC_SUM);


    public static HashMap<String, BehaviorSubject<Long>> slidingWindowMap = new HashMap<>();

    /**
     * 初始化滑动窗口
     *
     * @param slidingName 滑动名称
     * @param timespan    时间长度
     * @param unit        时间单位
     * @param callBack    回调
     */
    public static void init(String slidingName, long timespan, TimeUnit unit, BiConsumer<String, Long> callBack) {
        BehaviorSubject<Long> behaviorSubject = BehaviorSubject.create();
        behaviorSubject
                // 1秒作为一个基本块,横向移动
                .window(timespan, unit)//将一个数据集list按照拆分规则分成list<list>
                //将flatMap汇总平铺成一个事件,然后累加成一个Observable<Integer>对象，比如说1s内有10个对象，被累加起来
                .flatMap(INNER_BUCKET_SUM)
                .window(1, 1)
                .flatMap(WINDOW_SUM)
                .subscribe((Long longSum) -> callBack.accept(slidingName, longSum)
                );
        slidingWindowMap.put(slidingName, behaviorSubject);
    }

    /**
     * 初始化滑动窗口
     * calltime>time 将会合并结果集 calltime<time 会丢弃一定的call窗口
     *
     * @param slidingName  滑动名称
     * @param timespan     时间长度
     * @param unit         时间单位
     * @param callBack     回调
     * @param callTimespan 回调时间长度
     * @param cUnit        回调时间单位
     */
    public static void init(String slidingName, long timespan, TimeUnit unit, BiConsumer<String, Long> callBack, long callTimespan, TimeUnit cUnit) {
        if (unit.equals(cUnit) && timespan == callTimespan) {
            init(slidingName, timespan, unit, callBack);
            return;
        }
        BehaviorSubject<Long> behaviorSubject = BehaviorSubject.create();
        BehaviorSubject<Long> cBehaviorSubject = BehaviorSubject.create();

        behaviorSubject
                // 1秒作为一个基本块,横向移动
                .window(timespan, unit)//将一个数据集list按照拆分规则分成list<list>
                //flatmap将集合平铺成一个个单独的元素处理后再合并 eg: list<list<int>> -> int -> list<int>
                .flatMap(INNER_BUCKET_SUM)//阻塞
                .subscribe((Long longSum) -> cBehaviorSubject.onNext(longSum)//监听方法 异步
                );

        //注册回调 当生产关闭一个窗口时 会将当前汇总的数据加入到回调rx中 同时回调rx以一定的窗口在移动
        cBehaviorSubject.window(callTimespan, cUnit).flatMap(INNER_BUCKET_SUM).subscribe(a -> callBack.accept(slidingName, a));
        slidingWindowMap.put(slidingName, behaviorSubject);
    }

    /**
     * 添加条数
     *
     * @param slidingName
     * @param count
     */
    public static void addCount(String slidingName, Long count) {
        if (slidingWindowMap.containsKey(slidingName)) {
            slidingWindowMap.get(slidingName).onNext(count);//发送数据
        }
    }

    /**
     * 移除滑动窗口
     *
     * @param slidingName
     */
    public static void delSlidingName(String slidingName) {
        slidingWindowMap.remove(slidingName);
    }
}
