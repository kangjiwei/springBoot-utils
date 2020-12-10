package com.demo.action;

import com.demo.annoation.KAutoWrite;
import com.demo.annoation.KController;
import com.demo.annoation.KRequestMapping;
import com.demo.service.interfaces.IService;

@KController
public class controller {

    @KAutoWrite
    private IService service;


    @KRequestMapping
    public String firstReq(){

        return null;
    }

}
