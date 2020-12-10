package com.graphql.message;

import com.sun.org.apache.regexp.internal.RE;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by rongrong on 2019/12/16.
 */
public class ResposeMsg {

    private int  code;

    private String  message;

    private Map<String,Object> map;

    public ResposeMsg(int code,String message,Map<String,Object> map){
        this.code = code;
        this.message = message;
        this.map = map;
    }
}
