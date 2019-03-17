package com.liu.proxy.jdk;

/**
 * @ClassName: TestEat
 * @Auther: yu
 * @Date: 2019/3/17 14:30
 * @Description:
 */
public class TestEat {
    public static void main(String[] args){
      // new Zhangsan().eat();
        //拿到被代理对象的引用，然后获取他的接口
        //JDK代理重新生成一个类，同时实现我们给的代理对象所实现的接口
        //把被代理对象的引用也拿到了
        //重新动态生成一个class字节码
        //然后编译
        try {
            Person person = (Person) new PersonProxy().getInstance(new Zhangsan());
            person.eat();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
