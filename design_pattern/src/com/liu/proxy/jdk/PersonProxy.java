package com.liu.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ClassName: PersonProxy
 * @Auther: yu
 * @Date: 2019/3/17 14:33
 * @Description: jdk的代理要实现InvocationHandler接口
 */
public class PersonProxy implements InvocationHandler {

    private  Person target;
    /**
     *  获取代理对象
     * @param target
     * @return
     * @throws Exception
     */
    public Object getInstance(Person target) throws Exception{
        this.target = target;
        Class clazz = target.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(),clazz.getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理对象信息");
        System.out.println(this.target.getAddr());
        System.out.println(this.target.getName());
        this.target.eat();
        return null;
    }
}
