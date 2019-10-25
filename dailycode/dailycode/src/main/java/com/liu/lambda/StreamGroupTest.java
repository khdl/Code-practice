package com.liu.lambda;

import com.liu.lambda.assist.SpecialityEnum;
import com.liu.lambda.assist.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @className: StreamGroupTest
 * @author: yu.liu
 * @date: 2019/10/25 18:24
 * @description:
 */
public class StreamGroupTest {
    public static void main(String[] args){
       //转换成块  常用的流操作是将其分解成两个集合，Collectors.partitioningBy帮我们实现了，接收一个Predicate函数式接口。
        List<Student> students = new ArrayList<>(3);
        students.add(new Student("路飞", 22, 175));
        students.add(new Student("红发", 40, 180));
        students.add(new Student("白胡子", 50, 185));
        Map<Boolean, List<Student>> listMap = students.stream().collect(
                Collectors.partitioningBy(student -> student.getSpecialities().
                        contains(SpecialityEnum.SING)));

        //  数据分组 Collectors.groupingBy接收一个Function做转换。
        Map<SpecialityEnum, List<Student>> listMap1 =
                students.stream().collect(
                        Collectors.groupingBy(student -> student.getSpecialities().get(0)));

        //字符串拼接 joining接收三个参数，第一个是分界符，第二个是前缀符，第三个是结束符。也可以不传入参数Collectors.joining()，这样就是直接拼接。
        String names = students.stream()
                .map(Student::getName).collect(Collectors.joining(",","[","]"));
        System.out.println(names);
    }
}
