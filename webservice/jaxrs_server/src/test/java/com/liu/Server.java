package com.liu;

import com.liu.service.UserServiceImpl;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;

/**
 * @ClassName: Server
 * @Auther: yu
 * @Date: 2019/2/19 16:16
 * @Description:
 */
public class Server {
    public static void main(String[] args){
        //创建发布服务的工厂
        JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
        //设置服务地址
        factory.setAddress("http://localhost:8080/ws/");

        //设置服务类
        factory.setServiceBean(new UserServiceImpl());
        //发布服务
        factory.create();
        System.out.println("发布服务端口成功");
    }
}
