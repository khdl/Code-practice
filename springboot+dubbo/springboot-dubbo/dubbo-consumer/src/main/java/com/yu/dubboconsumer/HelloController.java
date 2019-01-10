package com.yu.dubboconsumer;

import com.yu.HelloService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: HelloController
 * @Auther: yu
 * @Date: 2019/1/10 15:17
 * @Description:
 */
@RestController
public class HelloController {
    @Reference
    private HelloService helloService;

    @RequestMapping("/hello")
    public String hello(){
        String hello = helloService.sayHello("word");
        System.out.println(helloService.sayHello("555555"));
        return hello;
    }
}
