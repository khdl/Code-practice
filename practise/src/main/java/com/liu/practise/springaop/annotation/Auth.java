package com.liu.practise.springaop.annotation;

import java.lang.annotation.*;

/**
 * @ClassName: Auth
 * @Auther: yu
 * @Date: 2018/10/21 13:58
 * @Description: 用户注解，默认false
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auth {
    boolean login() default  false;
}
