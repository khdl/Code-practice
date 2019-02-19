package com.liu;

import com.liu.service.impl.HelloServiceimpl;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

/**
 * @ClassName: Server
 * @Auther: yu
 * @Date: 2019/2/19 10:47
 * @Description: 发布服务
 */
public class Server {
    public static void main(String[] args){
        //发布服务的工厂
        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
        //设置服务地址
        factory.setAddress("http://localhost:8080/ws/hello");

        //可以添加日志拦截器查看soap请求和响应的内容
        //设置服务类
        factory.setServiceBean(new HelloServiceimpl());
        //发布服务
        factory.create();

        System.out.println("发布服务成功，端口8080...");
    }
}
