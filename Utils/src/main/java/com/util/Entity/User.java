package com.util.Entity;

/**
 * Created by rongrong on 2019/12/17.
 */
public class User {

    public User(String userName,String  userSex,String classes){
        this.userName = userName;
        this.userSex = userSex;
        this.classes = classes;
    }
    public User(){}

    private String  userName;

    private String userSex;

    private String classes;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }
}
