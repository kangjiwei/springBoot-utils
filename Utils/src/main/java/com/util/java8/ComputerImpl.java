package com.util.java8;

/**
 * @Author: XiongDa
 * @Date: 2021/06/06 10:42
 */
public class ComputerImpl implements Computer {

    @Override
    public void print() {
        System.out.println(attribute);
    }

    public static void main(String[] args) {
        new ComputerImpl().print();
    }

}
