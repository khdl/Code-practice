package com.liu.lambda.assist;

import java.util.List;

/**
 * @className: Student
 * @author: yu.liu
 * @date: 2019/10/25 13:45
 * @description:
 */
public class Student {
    private String  name;
    private int age;
    private int stature;
    private List<SpecialityEnum> Specialities;

    public Student(String name, int age, int stature) {
        this.name = name;
        this.age = age;
        this.stature = stature;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getStature() {
        return stature;
    }

    public void setStature(int stature) {
        this.stature = stature;
    }

    public List<SpecialityEnum> getSpecialities() {
        return Specialities;
    }

    public void setSpecialities(List<SpecialityEnum> specialities) {
        Specialities = specialities;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", stature=" + stature +
                ", Specialities=" + Specialities +
                '}';
    }
}
