package com.util.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author kangjiwei
 * @Date 2019/12/17 15:54
 */
public class ObjToMap<T> {


    /**
     *  对象转换成 map 集合
     * @param obj
     * @return
     */
    public  Map<String,Object> toMap(T obj){
        Map<String,Object>  map = new HashMap<>();
        Class<?> accountClass = obj.getClass();
        Arrays.stream(accountClass.getDeclaredFields()).forEach(field -> {
            String   name = field.getName();
            Object  value =  "";
            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if(value == null ) value = "";
            map.put(name,value);
        });
        return map;
    }




}
