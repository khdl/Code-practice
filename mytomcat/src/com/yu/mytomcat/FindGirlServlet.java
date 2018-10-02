package com.yu.mytomcat;

import java.io.IOException;

/**
 * @Auther: yu
 * @Date: 2018/10/2 14:04
 * @Description:servlet具体实现，为了后面测试
 */
public class FindGirlServlet extends  MyServlet {
    @Override
    public void doGet(MyRequest myRequest, MyResponse myResponse) {
        try {
            myResponse.write("get girl...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(MyRequest myRequest, MyResponse myResponse) {
        try {
            myResponse.write("get girl...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
