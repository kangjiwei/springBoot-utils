package com.graphql.Model;

/**
 * Created by Administrator on 2019/12/11.
 */
public class Student {

    public  Student(String  id,String name,String classes){
        this.id = id;
        this.name = name;
        this.classes = classes;
    }

    public  Student(){

    }


 private String  id;
 private String  name;
 private String classes;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }
}
