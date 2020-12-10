package com.graphql.graphQLSchema;

import com.graphql.Model.User;
import graphql.GraphQL;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;

import java.util.HashMap;
import java.util.Map;

import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

/**
 *  @Author kangjiwei
 *  @Date 2019/12/9
 */
public class schema {


    public static void main(String[] args) {

        User user = new  User("zhangsan","男","用不明白！");
        Map<String,Object>  map = new HashMap<>();
        map.put("name","亢");
        map.put("sex","慢慢陷落");
        map.put("intro","不可以说不爱我");

        //定义GraphQL类型：graphQL 与User的 对应类型
        GraphQLObjectType userType = newObject()
                .name("User")
                .field(newFieldDefinition().name("name").type(GraphQLString))
                .field(newFieldDefinition().name("sex").type(GraphQLString))
                .field(newFieldDefinition().name("intro").type(GraphQLString))
                .build();

        //定义暴露给客户端的查询query api， user 值源
        GraphQLObjectType queryType = newObject()
                .name("userQuery")
                .field(newFieldDefinition().type(userType).name("user").staticValue(map))
                .field(newFieldDefinition().type(userType).name("sex").staticValue(map))
                .build();

        //创建Schema
        GraphQLSchema schema = GraphQLSchema.newSchema()
                .query(queryType)
                .build();

        //测试输出
        GraphQL graphQL = GraphQL.newGraphQL(schema).build();
        Map<String, Object> result = graphQL.execute("{user{intro}}").getData();
        System.out.println(result);

   }

    /**
     * 动态定义 graphQL-schema
     */
     public void  BuilderShema(){

         GraphQLObjectType fooType = newObject()
                 .name("Ci")
                 .field(newFieldDefinition()
                         .name("product_number")
                         .type(GraphQLString))
                 .build();
     }





}
