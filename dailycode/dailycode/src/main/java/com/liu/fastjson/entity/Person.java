package com.liu.fastjson.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * @className: Person
 * @author: yu.liu
 * @date: 2019/4/28 14:27
 * @description:
 */
public class Person {

    @JSONField(name = "AGE", ordinal = 3)
    private int age;

    @JSONField(name = "FUll NAME" , ordinal = 2)
    private String name;

    //@JSONField(name = "DATE OF BIRTH", format = "dd/MM/yyyy", ordinal = 1)
    @JSONField(name = "Date of birth")
    private Date dateofBirth;

    public Person(int age, String name, Date dateofBirth){
        this.age = age;
        this.name = name;
        this.dateofBirth = dateofBirth;
    }
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateofBirth() {
        return dateofBirth;
    }

    public void setDateofBirth(Date dateofBirth) {
        this.dateofBirth = dateofBirth;
    }
}
