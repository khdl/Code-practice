package com.yu.demo.quartzlog;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @className: TestQuartz
 * @author: yu.liu
 * @date: 2019/7/18 13:41
 * @description:
 */
@Component
public class TestQuartz {

    @Scheduled(cron = "*/5 * * * * ? ")
    public void cacheClear(){
        System.out.println("执行。。。。。");
    }

}
