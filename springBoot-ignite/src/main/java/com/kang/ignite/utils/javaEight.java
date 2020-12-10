package com.kang.ignite.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.kang.ignite.entity.Student;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.lang.reflect.Array;
import java.util.*;

public class javaEight {


    @Test
    public void getName() {
        Student student = new Student(11, "aa", 1);
        /*Function<Student, Integer> getName = Student::getAge;
        showInfo(student, getName);*/
        List<Student> list = new ArrayList<>();
        list.add(student);
        String strSourceCode = StringUtils.collectionToCommaDelimitedString(list);
        System.out.println(strSourceCode);
    }

    public <T> T showInfo(Student stu, Function<Student, Integer> getName) {

        Integer apply = getName.apply(stu);

        System.out.println(apply);

        return null;
    }

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void jackson() {
        JavaType javaType = objectMapper
                .getTypeFactory()
                .constructParametricType(List.class, CptData.class);

        //reusable 可重用
        // thread-safe 线程安全
        JsonFactory  jsonFactory = new JsonFactory();
        JsonFactory enable = jsonFactory.enable(JsonParser.Feature.ALLOW_COMMENTS);

    }


    @Test
    public void  testMap(){

        Map<String,Integer> map = new HashMap<>();
        map.put("name",1);
         //遍历Map集合的 k , v
        Set<Map.Entry<String, Integer>> entries1 = map.entrySet();
        for(Map.Entry<String,Integer> entry:entries1){
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }

        Iterator<Map.Entry<String, Integer>> entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, Integer> entry = entries.next();
            String key = entry.getKey();
            int value = entry.getValue();
            System.out.println(key + " " + value);
        }

    }



}
