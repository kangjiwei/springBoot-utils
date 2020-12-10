package com.demo.annoation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface KController {

    public String clsName() default "name";

}
