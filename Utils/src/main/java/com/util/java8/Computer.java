package com.util.java8;

/**
 * @Author: XiongDa
 * @Date: 2021/06/06 10:37
 */
public interface Computer {
    //必须是初始化之后不能更改
    // 默认是使用public static final
    String attribute = "attr";

    void print();

    default boolean isPrint() {
        System.out.println(String.format("打印成员变量:%s", attribute));
        return true;
    }
}
