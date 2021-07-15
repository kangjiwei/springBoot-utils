package com.kang.ignite.entity;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

public class FieldEntity {

    @QuerySqlField(index = true)
    private int id;

    @QuerySqlField
    private String name;

    private int level;

    public FieldEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Class [id=" + id + ", name=" + name + ", level=" + level + "]";
    }
}
