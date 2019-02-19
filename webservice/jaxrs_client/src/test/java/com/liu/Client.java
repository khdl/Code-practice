package com.liu;

import com.liu.entity.User;
import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

/**
 * @ClassName: Client
 * @Auther: yu
 * @Date: 2019/2/19 16:27
 * @Description:
 */
public class Client {
    @Test
    public void testSave(){
        //通过WebClient对象远程调用服务端
        WebClient.create("http://localhost:8080/ws/userService/user")
                .post(new User());
    }
    @Test
    public void testGet(){
        User user =
        WebClient.create("http://localhost:8080/ws/userService/user/1")
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .get(User.class);
        System.out.println(user);
        System.out.println(user.getUsername());
    }
}
