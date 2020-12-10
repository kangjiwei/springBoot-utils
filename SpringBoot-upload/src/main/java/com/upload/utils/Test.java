package com.upload.utils;

public class Test {

    public void showInfo(String...  name){
        System.out.println(name);
    }
    
    public static void main(String[] args) {
        Test test = new Test();
        test.showInfo("name","name","name");
    }


}
