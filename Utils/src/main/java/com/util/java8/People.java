package com.util.java8;

/**
 * @Author: XiongDa
 * @Date: 2021/06/06 11:01
 */
public class People {

    private String name;
    private String sex;

    public People(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
