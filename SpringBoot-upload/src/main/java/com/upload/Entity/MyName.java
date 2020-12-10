package com.upload.Entity;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyName {
    String value() default "刘德华";
}
