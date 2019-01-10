package com.liu.practise.springaop.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @ClassName: LogAspect
 * @Auther: yu
 * @Date: 2018/10/21 10:29
 * @Description:
 */
@Aspect
@Component
public class LogAspect {
    /**
     * 拦截对这个包下的所有访问
     */
    @Pointcut("execution(* com.liu.practise.springaop.controller.*.*(..))")
    public  void loginLog(){

    }

    /**
     * 前置通知
     * @param joinPoint
     */
    @Before("loginLog()")
    public void  loginBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        System.out.println("请求路劲:"+ request.getRequestURL());
        System.out.println("请求方式:"+ request.getMethod());
        System.out.println("方法名:"+ joinPoint.getSignature().getName());
        System.out.println("类路劲:"+ joinPoint.getSignature().getDeclaringTypeName());
        System.out.println("参数:"+ Arrays.toString(joinPoint.getArgs()));
    }

    /**
     *   方法退出时执行
     */
    @AfterReturning(returning = "object" ,pointcut = "loginLog()")
    public  void doAfterReturning(Object object){
        System.out.println("方法的返回值:"+object);
    }

    /**
     * 方法发生异常执行
     * @param e
     * @param joinPoint
     */
    @AfterThrowing(throwing = "e",pointcut = "loginLog()")
    public void throwExecute(JoinPoint joinPoint,Exception e){
        System.out.println("方法执行异常:"+ e.getMessage());
    }

    /**
     * 后置通知
     */
    @After("loginLog()")
    public  void afterInform(){
        System.out.println("后置通知结束");
    }

    /**
     * 环绕通知
     * @param proceedingJoinPoint
     * @return
     */
    @Around("loginLog()")
    public Object surroundingInform(ProceedingJoinPoint proceedingJoinPoint){
        System.out.println("环绕通知开始：");

        try {
            Object object = proceedingJoinPoint.proceed();
            System.out.println("方法环绕proceed,结果是:" + object);
            return  object;
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }
}
