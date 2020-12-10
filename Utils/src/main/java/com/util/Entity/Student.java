package com.util.Entity;

import lombok.Data;
import lombok.ToString;

/**
 * Created by rongrong on 2019/12/26.
 */
public class Student  {

    public Student(String userName,String userSex){
        this.userName = userName;
        this.userSex = userSex;
    }

    private String  userName;

    private String userSex;

    private String classes;

     public void showInfo(){
         System.out.println(userName + "  " + userSex);
     }

    public static void main(String[] args) {
        Student  student = new Student("userName","sex");
        student.showInfo();
    }

}
