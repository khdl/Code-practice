package com.yu.demo.controller;

import com.yu.demo.mapper.User;
import com.yu.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: UserController
 * @Auther: yu
 * @Date: 2019/1/11 16:05
 * @Description:
 */
@RestController
@RequestMapping("/user/")
public class UserController {

    @SuppressWarnings("all")
    @Autowired
    UserMapper userMapper;

    @GetMapping("list")
    public List<User> list() {
        return userMapper.listSample();
    }

    @GetMapping("list/{username}")
    public List<User> listByUsername(@PathVariable("username") String username) {
        return userMapper.listByUsername(username);
    }

    @GetMapping("get/{username}/{password}")
    public User get(@PathVariable("username") String username, @PathVariable("password") String password) {
        return userMapper.get(username, password);
    }

    @GetMapping("get/bad/{username}/{password}")
    public User getBadUser(@PathVariable("username") String username, @PathVariable("password") String password) {
        return userMapper.getBadUser(username, password);
    }
}
