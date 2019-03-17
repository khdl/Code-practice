package com.liu.proxy.jdk;

/**
 * @ClassName: Zhangsan
 * @Auther: yu
 * @Date: 2019/3/17 14:23
 * @Description:
 */
public class Zhangsan implements  Person{
    private  String  name = "张三";
    private  String addr = "成都";
    @Override
    public void eat() {
        System.out.println("距离近");
        System.out.println("评分高");
        System.out.println("好看");
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
