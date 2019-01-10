package com.liu.practise.springaop.aop;

import com.liu.practise.springaop.annotation.Auth;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @ClassName: loginAspect
 * @Auther: yu
 * @Date: 2018/10/21 14:00
 * @Description:验证是否登录
 */
@Aspect
@Component
public class loginAspect {

    @Before("@annotation(auth)")
    public void loginBefore( Auth auth){
        System.out.println("验证登录开始");
    }

    @Around("@annotation(auth)")
    public Object validate(ProceedingJoinPoint joinPoint, Auth auth) throws Throwable {
        //获取class对象
        Class<? extends Object> targetClass = joinPoint.getTarget().getClass();
        //获得方法名
        String methodName = joinPoint.getSignature().getName();

        Method trgetMethod = null;
        Method[] methods = targetClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                trgetMethod = method;
                break;
            }
        }
        //获得方法上的注解
        auth = trgetMethod.getAnnotation(Auth.class);
        if (auth.login() ){
            return  joinPoint.proceed();
        }else{
            return "用户没有登录";
        }

    }
}
