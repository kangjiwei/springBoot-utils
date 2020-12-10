package com.kang.jp.springbootjavapoet.entity;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyName {
    String value() default "";
}
