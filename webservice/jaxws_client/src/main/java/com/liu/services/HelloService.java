package com.liu.services;

import javax.jws.WebService;

/**
 * @ClassName: HelloService
 * @Auther: yu
 * @Date: 2019/2/19 10:55
 * @Description:对外发布服务的接口
 */
@WebService
public interface HelloService {

    /**
     * 对外发布服务接口的方法
     * @param name
     * @return
     */
    public  String sayHello(String name);
}
