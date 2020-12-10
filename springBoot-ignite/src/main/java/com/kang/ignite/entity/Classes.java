package com.kang.ignite.entity;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

public class Classes {

    @QuerySqlField(index = true)
    private int id;

    @QuerySqlField
    private String name;

    private int level;

    public Classes(int id, String name)
    {
        this.id = id;
        this.name = name;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    @Override
    public String toString() {
        return "Class [id=" + id + ", name=" + name + ", level=" + level + "]";
    }
}
