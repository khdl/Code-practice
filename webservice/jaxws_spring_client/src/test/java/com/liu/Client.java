package com.liu;

import com.liu.services.HelloService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @ClassName: Client
 * @Auther: yu
 * @Date: 2019/2/19 12:58
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class Client {
    @Resource
    private HelloService helloService;

    @Test
    public void  remote(){
        System.out.println(helloService.getClass());
        String content =  helloService.sayHello("xiaohei");
        System.out.println(content);
    }
}
