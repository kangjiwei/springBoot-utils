package com.util.JSON;

import com.util.utils.ObjToMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Created by rongrong on 2019/12/19.
 */
public class StrToJSON {


    //json 格式样例

    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:5010/middle/app/testApi", String.class, "");
        StringBuffer sb = new StringBuffer();
        HttpStatus statusCode = responseEntity.getStatusCode();
        String body = responseEntity.getBody();
        System.out.println(body);
    }
    
}
