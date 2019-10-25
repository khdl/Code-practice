package com.liu.lambda;


import com.liu.fastjson.entity.Person;

import java.math.BigDecimal;
import java.util.Date;
import java.util.function.*;

/**
 * @className: LambdaTest
 * @author: yu.liu
 * @date: 2019/10/25 13:11
 * @description:
 */
public class LambdaTest {
    public static void main(String[] args){
        Person p = new Person(10,"aaa",new Date());
        Predicate<Integer> predicate = x -> x > 185;//test()
        System.out.println(predicate.test(p.getAge()));

        Consumer<String> consumer = System.out::println;
        consumer.accept("1111");

        Function<Person,String> function = Person::getName;
        String name = function.apply(p);

        Supplier<Integer> supplier =
                () -> Integer.valueOf(BigDecimal.TEN.toString());
        System.out.println(supplier.get());

        UnaryOperator<Boolean> unaryOperator = uglily -> !uglily;
        Boolean apply2 = unaryOperator.apply(true);
        System.out.println(apply2);

        BinaryOperator<Integer> operator = (x, y) -> x * y;
        Integer integer = operator.apply(2, 3);
        System.out.println(integer);

        test(() -> "我是一个演示的函数式接口");
    }

    public static void test(Worker worker) {
        String work = worker.work();
        System.out.println(work);
    }


}
