package itc.common.tools.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * @ProjectName: java-tools
 * @Package: itc.common.tools.quartz
 * @ClassName: QuartzTools
 * @Description: 服务中心
 * @Author: Mastercoding
 * @CreateDate: 2019/4/28 12:39
 * ***********************************************************
 * @UpdateUser: Mastercoding
 * @UpdateDate: 2019/4/28 12:39
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * ***********************************************************
 * Copyright: Copyright (c) 2019
 **/
public class QuartzTools {
    private static StdSchedulerFactory factory = new StdSchedulerFactory();
    private static Scheduler scheduler;

    static {
        try {
            scheduler = factory.getScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建任务
     *
     * @param name     任务名称
     * @param jobClass 执行类
     * @param cronStr  cron表达式
     * @return
     */
    public static JobDetail createJob(String name, Class<? extends Job> jobClass, String cronStr) {
        //创建一个Trigger触发器的实例，定义该job立即执行，
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(name).startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule(cronStr))
                .build();
        return createJob(name, jobClass, trigger);
    }

    /**
     * 得到job
     *
     * @param name 名称
     * @return
     */
    public static JobDetail getJob(String name) {
        try {
            return scheduler.getJobDetail(JobKey.jobKey(name));
        } catch (SchedulerException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 得到触发器
     *
     * @param name
     * @return
     */
    public static Trigger getTrigger(String name) {
        try {
            return scheduler.getTrigger(TriggerKey.triggerKey(name));
        } catch (SchedulerException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 创建任务
     *
     * @param jobDetail
     * @param trigger
     * @return
     */
    public static JobDetail createJob(JobDetail jobDetail, CronTrigger trigger) {
        try {
            final Date date = scheduler.scheduleJob(jobDetail, trigger);
            return jobDetail;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 创建任务
     *
     * @param name
     * @param jobClass
     * @param trigger
     * @return
     */
    public static JobDetail createJob(String name, Class<? extends Job> jobClass, CronTrigger trigger) {
        //创建一个jobDetail的实例，将该实例与HelloJob Class绑定
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(name).build();
        return createJob(jobDetail, trigger);
    }

    /**
     * 删除job
     *
     * @param jobName
     */
    public static void delJob(String jobName) {
        try {
            scheduler.deleteJob(new JobKey(jobName));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
