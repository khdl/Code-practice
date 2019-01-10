package com.liu.practise.springaop.controller;

import com.liu.practise.springaop.annotation.Auth;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: LoginController
 * @Auther: yu
 * @Date: 2018/10/21 10:21
 * @Description:
 */
@RestController
public class LoginController {

    @GetMapping(value = "/userName")
    public  String getLoginName(String userName,Integer age){
        return  userName + "---" + age;
    }

    @Auth(login = true)
    @GetMapping(value = "/login")
    public String getUser() {

        return "用户已经登录";
    }
}
