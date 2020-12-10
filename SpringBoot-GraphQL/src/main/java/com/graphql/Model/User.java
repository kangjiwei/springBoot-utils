package com.graphql.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by rongrong on 2019/12/9.
 */
@Getter
@Setter
public class User {
    private String name;
    private String sex;
    private String Intro;
    private String[] skills;

    public User(String name,String  sex, String intro ){
         this.name = name;
         this.sex= sex;
         this.Intro = intro;
    }

    public String[] getSkills() {
        return skills;
    }

    public void setSkills(String[] skills) {
        this.skills = skills;
    }

    public User(){
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIntro() {
        return Intro;
    }

    public void setIntro(String intro) {
        Intro = intro;
    }
}
