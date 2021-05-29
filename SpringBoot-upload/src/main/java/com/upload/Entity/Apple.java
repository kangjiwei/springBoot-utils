package com.upload.Entity;


import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class Apple {

     private String name;

     public void showName(){
         System.out.println(name);
     }
}
