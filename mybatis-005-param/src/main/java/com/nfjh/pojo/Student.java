package com.nfjh.pojo;

import java.util.Date;

public class Student {
    private Long id;
    private String name;
    private Integer age;
    private  Double height;
    private Date brith ;
    private  Character sex;

    public Student() {
    }

    public Student(Long id, String name, Integer age, Double height, Date brith, Character sex) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.height = height;
        this.brith = brith;
        this.sex = sex;
    }

    public Student(String name, Integer age, Double height, Date brith, Character sex) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.brith = brith;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", brith=" + brith +
                ", sex=" + sex +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Date getBrith() {
        return brith;
    }

    public void setBrith(Date brith) {
        this.brith = brith;
    }

    public Character getSex() {
        return sex;
    }

    public void setSex(Character sex) {
        this.sex = sex;
    }
}
