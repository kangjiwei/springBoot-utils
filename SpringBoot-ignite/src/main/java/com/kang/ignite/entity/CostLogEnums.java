package com.kang.ignite.entity;

public enum CostLogEnums {

    PYTHON_FILE_LOADING_ORDER("1"),
    PYTHON_FILE_LOADING_ORDERss("3"),
    PYTHON_FILE_LOADING_ORDERs("2");
    private final String param;

    private CostLogEnums(String param) {
        this.param = param;
    }

    public String getValue(){
        return param;
    }

}
