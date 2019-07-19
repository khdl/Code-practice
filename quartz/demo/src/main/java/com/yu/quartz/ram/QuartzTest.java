package com.yu.quartz.ram;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * @className: QuartzTest
 * @author: yu.liu
 * @date: 2019/7/19 14:37
 * @description:
 */
public class QuartzTest {

    public static void main(String[] args) throws SchedulerException {

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        // 获取调度器实例
        Scheduler scheduler = schedulerFactory.getScheduler();

       //创建JobDetail
        JobDetail jobDetail = JobBuilder.newJob(RAMJob.class)
                .withDescription("this is a ram job")
                .withIdentity("ramJob", "ramGroup")
                .build();

        //任务运行的时间，SimpleSchedle类型触发器有效，3秒后启动任务
        long time=  System.currentTimeMillis() + 3*1000L;
        Date statTime = new Date(time);

        Trigger trigger = TriggerBuilder.newTrigger()
                .withDescription("")
                .withIdentity("ramTrigger","ramTriggerGroup")
                .startAt(statTime)
                .withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?"))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);

        scheduler.start();

        System.out.println("启动时间， " + new Date());

    }
}
