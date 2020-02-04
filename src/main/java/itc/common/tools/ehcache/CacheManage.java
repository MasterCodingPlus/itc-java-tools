package itc.common.tools.ehcache;

import com.google.common.cache.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @program: ywyk
 * @description:
 * @author: Chen QiuXu - CoderZero
 * @create: 2019-09-08 11:07
 **/
public class CacheManage {

    private static ConcurrentHashMap<String, LoadingCache<String, Object>> caches = new ConcurrentHashMap<>();

    static {
        cacheRefresh();
    }

    /**
     * 创建缓存
     *
     * @param duration 过期时长
     * @return 缓存对象
     */
    public static LoadingCache<String, Object> createCache(int duration, String cacheName, Consumer<RemovalNotification> removalNotificationCallBack) {
        LoadingCache<String, Object> cache = CacheBuilder.newBuilder()
                .expireAfterAccess(duration, TimeUnit.SECONDS)
                .maximumSize(Long.MAX_VALUE)
                .removalListener(new RemovalListener() {
                    @Override
                    public void onRemoval(RemovalNotification removalNotification) {
                        if (removalNotificationCallBack != null) {
                            removalNotificationCallBack.accept(removalNotification);
                        }
                    }
                })
                .build(new CacheLoader() {
                    @Override
                    public Object load(Object o) throws Exception {
                        return "";
                    }
                });
        caches.put(cacheName, cache);
        return cache;
    }

    /**
     * 获取缓存
     *
     * @param cacheName 缓存名称
     * @return 缓存对象
     */
    public static LoadingCache<String, Object> getCache(String cacheName) {
        if (!caches.containsKey(cacheName)) {
            return null;
        }
        return caches.get(cacheName);
    }

    /**
     * 添加至缓存
     *
     * @param cacheName
     * @param key
     * @param value
     */
    public static void addCache(String cacheName, String key, Object value) {
        if (!caches.containsKey(cacheName)) {
            return;
        } else {
            getCache(cacheName).put(key, value);
        }
    }

    /**
     * 获取值
     *
     * @param cacheName
     * @param key
     * @return
     */
    public static Object getCahe(String cacheName, String key) {
        if (!caches.containsKey(cacheName)) {
            return null;
        } else {
            Object value = null;
            try {
                value = getCache(cacheName).get(key);
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return value;
        }
    }

    /**
     * 缓存刷新线程
     */
    private static void cacheRefresh() {
        new Thread(() -> {
            while (true) {
                for (LoadingCache<String, Object> cache : caches.values()) {
                    cache.cleanUp();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "Cache-Refresh").start();
    }
}
