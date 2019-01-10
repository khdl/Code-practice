package com.yu.provider.service.impl;

import com.yu.HelloService;

/**
 * @ClassName: HelloServiceImpl
 * @Auther: yu
 * @Date: 2019/1/10 15:09
 * @Description:
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return "hello123" + name;
    }
}
