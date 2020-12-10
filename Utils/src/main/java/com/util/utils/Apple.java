package com.util.utils;


import com.util.Entity.MyName;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Apple {

     @MyName("梦")
     private String name;

     public void showName(){
         System.out.println(name);
     }
}
