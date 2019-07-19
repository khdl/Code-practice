package com.yu.quartz.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @className: Test
 * @author: yu.liu
 * @date: 2019/7/19 15:43
 * @description:
 */
public class Test {
    public static void main(String[] args){
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring_quartz.xml");
    }
}
