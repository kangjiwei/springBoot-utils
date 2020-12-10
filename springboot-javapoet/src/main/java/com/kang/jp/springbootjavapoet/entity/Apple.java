package com.kang.jp.springbootjavapoet.entity;


import org.springframework.stereotype.Component;

@Component
public class Apple {

     @MyName(value = "失望")
     public String name;


    @FruitName("Apple")
    private String appleName;

    @FruitColor(fruitColor= FruitColor.Color.RED)
    private String appleColor;

    public void setAppleColor(String appleColor) {
        this.appleColor = appleColor;
    }
    public String getAppleColor() {
        return appleColor;
    }


    public void setAppleName(String appleName) {
        this.appleName = appleName;
    }
    public String getAppleName() {
        return appleName;
    }

    public void displayName(){
        System.out.println(getAppleName());
    }
}
