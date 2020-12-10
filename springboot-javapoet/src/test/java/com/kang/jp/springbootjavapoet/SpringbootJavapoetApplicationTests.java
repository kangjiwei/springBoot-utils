package com.kang.jp.springbootjavapoet;

import com.kang.jp.springbootjavapoet.entity.Apple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootJavapoetApplicationTests {

    @Autowired
    private Apple apple;

    @Test
    void contextLoads() {
        apple.displayName();
    }

}
