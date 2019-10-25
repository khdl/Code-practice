package com.liu.lambda;

import com.liu.lambda.assist.Student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

/**
 * @className: Streamtest
 * @author: yu.liu
 * @date: 2019/10/25 17:22
 * @description: 常用的流
 */
public class StreamTest {
    public static void main(String[] args){
        //将流转换为list。还有toSet()，toMap()等。
       //collect(Collectors.toList())
        List<Student> studentList = Stream.of(new Student("路飞", 22, 175),
                new Student("红发", 40, 180),
                new Student("白胡子", 50, 185)).collect(Collectors.toList());
        System.out.println(studentList);

        //filter  起过滤筛选的作用。内部就是Predicate接口。
        List<Student> students = new ArrayList<>(3);
        students.add(new Student("路飞", 22, 175));
        students.add(new Student("红发", 40, 180));
        students.add(new Student("白胡子", 50, 185));
        List<Student> list = students.stream()
                .filter(stu -> stu.getStature() < 180)
                .collect(Collectors.toList());
        System.out.println(list);

        //map内部就是Function接口。
        List<String> names = students.stream().map(student -> student.getName())
                .collect(Collectors.toList());
        System.out.println(names);

        // flatMap 将多个Stream合并为一个Stream
        //调用Stream.of的静态方法将两个list转换为Stream，再通过flatMap将两个流合并为一个。
        List<Student> studentList1 = Stream.of(students,
                asList(new Student("艾斯", 25, 183),
                        new Student("雷利", 48, 176)))
                .flatMap(students1 -> students1.stream()).collect(Collectors.toList());
        System.out.println(studentList1);

        // max和min  经常会在集合中求最大或最小值，使用流就很方便
        //Optional对象，该对象是java8新增的类，专门为了防止null引发的空指针异常。
        Optional<Student> max = students.stream()
                .max(Comparator.comparing(stu -> stu.getAge()));
        Optional<Student> min = students.stream()
                .min(Comparator.comparing(stu -> stu.getAge()));
        //判断是否有值
        if (max.isPresent()) {
            System.out.println(max.get());
        }
        if (min.isPresent()) {
            System.out.println(min.get());
        }

        // count  统计功能，一般都是结合filter使用，因为先筛选出我们需要的再统计即可
        long count = students.stream().filter(s1 -> s1.getAge() < 45).count();
        System.out.println("年龄小于45岁的人数是：" + count);

        // reduce reduce 操作可以实现从一组值中生成一个值。
        Integer reduce = Stream.of(1, 2, 3, 4).reduce(0, (acc, x) -> acc+ x);
        System.out.println(reduce);

    }

}
