package com.liu.services.impl;

import com.liu.services.HelloService;

/**
 * @ClassName: HelloServiceimpl
 * @Auther: yu
 * @Date: 2019/2/19 10:44
 * @Description:
 */
public class HelloServiceimpl  implements HelloService {
    /**
     * 对外发布服务接口的方法
     *
     * @param name
     * @return
     */
    @Override
    public String sayHello(String name) {
        return name + ",welcome!";
    }
}
