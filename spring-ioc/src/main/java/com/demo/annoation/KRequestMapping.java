package com.demo.annoation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
public @interface KRequestMapping {

    public String getName() default "ioc";

}
