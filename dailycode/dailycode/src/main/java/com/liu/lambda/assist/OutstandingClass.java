package com.liu.lambda.assist;

import java.util.List;

/**
 * @className: OutstandingClass
 * @author: yu.liu
 * @date: 2019/10/25 13:45
 * @description:
 */
public class OutstandingClass {
    private String name;
    private List<Student> students;
    public OutstandingClass(){

    }

    public OutstandingClass(String name, List<Student> students) {
        this.name = name;
        this.students = students;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
