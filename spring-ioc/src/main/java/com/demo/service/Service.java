package com.demo.service;


import com.demo.annoation.KService;
import com.demo.service.interfaces.IService;

@KService
public class Service implements IService {

    @Override
    public String getInfo() {
        return "IOC";
    }


}
