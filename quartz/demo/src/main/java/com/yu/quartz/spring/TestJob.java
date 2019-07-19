package com.yu.quartz.spring;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @className: TestJob
 * @author: yu.liu
 * @date: 2019/7/19 15:38
 * @description:
 */
public class TestJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("执行中。。。。");
    }
}
