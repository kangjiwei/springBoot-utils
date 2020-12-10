package com.upload.Entity;


import org.springframework.stereotype.Component;

public class Apple {

     @MyName("梦")
     private String name;

     public void showName(){
         System.out.println(name);
     }
}
