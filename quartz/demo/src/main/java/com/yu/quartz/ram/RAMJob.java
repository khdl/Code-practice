package com.yu.quartz.ram;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @className: RAMJob
 * @author: yu.liu
 * @date: 2019/7/19 14:34
 * @description:
 */
public class RAMJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("执行了定时任务。。。。");
    }
}
