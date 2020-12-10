package com.graphql.controller;

import com.graphql.GraphQL_learn.GraphQLS_schema;
import com.graphql.message.ResposeMsg;
import graphql.ExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * Created by rongrong on 2019/12/9.
 */
 @Controller
public class IndexController {

    @Autowired
    GraphQLS_schema  schema;

    @RequestMapping("/graphql")
/*    @ResponseBody
    @PutMapping//增加
    @DeleteMapping//删除
    @GetMapping//查询
    @PostMapping//修改*/
    public Map<String, Object> graphql(@RequestBody Map<String,Object> map){
        Object query = map.get("query");
         if(query == null) query = map.get("mutation");
        return  schema.getGraphQL().execute(query.toString()).getData();
    }


}
