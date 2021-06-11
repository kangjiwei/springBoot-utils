package com.util.java8;

/**
 * @Author: XiongDa
 * @Date: 2021/06/06 11:09
 */
public class Student extends People implements Talk, Write {

    public Student(String name, String sex) {
        super(name, sex);
    }

    @Override
    public void talk() {
        System.out.println(String.format("Hello world"));
    }

    @Override
    public void result() {
        System.out.println("你最温柔");
    }

    public static void main(String[] args) {
        Student student = new Student("邓紫棋", "小姐姐");
        System.out.println(String.format("姓名:%s", student.getName()));
        System.out.println(String.format("性别:%s", student.getSex()));
        student.talk();
        student.result();
    }

}
