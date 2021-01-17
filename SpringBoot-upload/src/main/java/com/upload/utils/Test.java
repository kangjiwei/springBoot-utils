package com.upload.utils;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

import java.lang.ref.Reference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.TreeMap;

/**
 *
 */
public class Test {

    public String spring;

    public void showInfo(String... name) {
        System.out.println(name);
    }


    /**
     * <Comment>
     *   <t>ClassLoaders</t>
     *   <c>得到Class类的加载信息</c>
     * </Comment>
     */
     @org.junit.Test
    public void code1() {
        Field[] fields = Test.class.getFields();
        ClassLoader classLoader = Test.class.getClassLoader();
        Method[] methods = Test.class.getMethods();
        for (Method method : methods) {
            System.out.println("method " + method.getName());
        }
        for (Field field : fields) {
            System.out.println("常量: " + field.getName());
        }
    }

    @org.junit.Test
    public void code2(){
        try {
            Class<?> aClass = ClassUtils.forName("javax.inject.Provider", Test.class.getClassLoader());
            Method[] methods = aClass.getMethods();
            for(Method method:methods){
                System.out.println(method.getName());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("找不到！");
        }
    }

    transient private String name;

    /**
     * <null>
     *     @Nullable
     *
     * </null>
     */
    @org.junit.Test
    public void code3(){

        //Reference
    }

    public static void main(String[] args) {
        TreeMap
    }

}
