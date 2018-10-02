package com.yu.mytomcat;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: yu
 * @Date: 2018/10/2 14:11
 * @Description:
 */
public class ServletMappingConfig {
    public  static List<ServletMapping> servletMappingList = new ArrayList<>();

    static {
        servletMappingList.add(new ServletMapping("findGirl","/girl","com.yu.mytomcat.FindGirlServlet"));
        servletMappingList.add(new ServletMapping("helloWord","/word","com.yu.mytomcat.HelloWordServlet"));
    }
}
