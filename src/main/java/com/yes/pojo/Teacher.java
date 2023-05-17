package com.yes.pojo;

/**
 * Created by huangJin on 2023/5/16.
 */
public class Teacher {

    private Student student;

    public Teacher() {
        System.out.println("无参构造器");
    }

    public Teacher(Student student) {
        System.out.println("有参构造器");
        this.student = student;
    }
}
