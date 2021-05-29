package com.graphql.controller;

import com.graphql.GraphQL_learn.GraphQLS_schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by rongrong on 2019/12/9.
 */
@Controller
public class IndexController {

    @Autowired
    GraphQLS_schema schema;

    @RequestMapping("/graphql")
    @ResponseBody
    public Map<String, Object> graphql(@RequestBody Map<String, Object> map) {
        Object query = map.get("query");
        if (query == null)
            query = map.get("mutation");
        return schema.getGraphQL().execute(query.toString()).getData();
    }


}
