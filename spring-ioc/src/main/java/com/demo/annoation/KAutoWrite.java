package com.demo.annoation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
public @interface KAutoWrite {
     public String getName() default "name";
}
