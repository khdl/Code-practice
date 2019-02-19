package com.liu;

import com.liu.services.HelloService;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

/**
 * @ClassName: client
 * @Auther: yu
 * @Date: 2019/2/19 11:06
 * @Description:
 */
public class Client {
    public static void main(String[] args){
        //服务接口访问地址：http://localhost:8080/ws/hello

        //创建cxf代理工厂
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        //设置远程服务访问地址
        factory.setAddress("http://localhost:8080/ws/hello");
        //设置接口类型
        factory.setServiceClass(HelloService.class);
        //对接口生成代理对象
        HelloService helloService = factory.create(HelloService.class);
        //代理对象
        System.out.println(helloService.getClass());
        //远程访问服务端方法
        String content = helloService.sayHello("xiaoming");
        System.out.println(content);
    }

}
