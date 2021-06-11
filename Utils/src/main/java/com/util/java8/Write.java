package com.util.java8;

/**
 * @Author: XiongDa
 * @Date: 2021/06/06 11:06
 */
public interface Write {

    String type = "Writer";

    default void result() {
        System.out.println(String.format("邓紫棋--%s", "回忆的沙漏"));
    }
}
