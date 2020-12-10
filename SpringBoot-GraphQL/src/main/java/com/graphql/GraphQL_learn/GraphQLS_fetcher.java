package com.graphql.GraphQL_learn;

import com.google.common.collect.ImmutableMap;
import com.graphql.Model.User;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2019/12/10.
 */
@Component
public class GraphQLS_fetcher {

    private List<Map<String,Object>> list = Arrays.asList(
            ImmutableMap.of("id","stu1","name","艾尔肯","classes","name"),
            ImmutableMap.of("id","stu2","name","我慢慢的明白","classes","伤心"),
            ImmutableMap.of("id","stu1","name","我慢慢的明白","classes","伤心")
    );

    private List<Map<String,Object>> books = Arrays.asList(
            ImmutableMap.of("id","bk1","bookName","因为你","pay","多少的真爱"),
            ImmutableMap.of("id","bk2","bookName","情路等你那么长","pay","多少的真爱")
    );

    public DataFetcher  getDataFetcher(){
        return dataFetchingEnvironment -> {
            String id = dataFetchingEnvironment.getArgument("id");
            System.out.println(" baby "+id);
            return  books.stream().filter(stringObjectMap -> stringObjectMap.get("id").equals(id)).collect(Collectors.toList());
        };
    }

    //查询学生信息
    public  DataFetcher  getStuFetcher(){
        return dataFetchingEnvironment -> {
            String id = dataFetchingEnvironment.getArgument("id");
            System.out.println(" dangerous  "+id);
            return  list.stream().filter(map ->(map.get("id").equals(id))).collect(Collectors.toList());
        };
    }

    //新增学生信息
    public  DataFetcher  addBookInfo(){
        return  dataFetchingEnvironment -> {
            Map<String,Object> book = dataFetchingEnvironment.getArgument("book");
            System.out.println(" 学生添加信息 "+book.toString());
            return "success";
        };
    }
    //修改学生信息
    public DataFetcher  updateBook(){
        return  dataFetchingEnvironment -> {
            String ids = dataFetchingEnvironment.getArgument("id");
            Map<String,Object> map = dataFetchingEnvironment.getArgument("book");
            System.out.println(" 修改的主键信息  "+ids);
            System.out.println(
                    "修改的实体类信息 "+map.toString()
            );
          return  map;
        };
    }
    //删除学生信息
    public DataFetcher deleteBookById(){
        return dataFetchingEnvironment -> {
            String ids = dataFetchingEnvironment.getArgument("id");
            System.out.println("删除信息主键  "+ids);
            return true;
        };
    }

    //新增用户信息
    public DataFetcher addUser(){
        return dataFetchingEnvironment -> {
            System.out.println("新增用户");
           /* Map<String,Object> userInfo = dataFetchingEnvironment.getArgument("user");
            System.out.println("删除信息主键  "+userInfo);*/
            return new User();
        };
    }
}
