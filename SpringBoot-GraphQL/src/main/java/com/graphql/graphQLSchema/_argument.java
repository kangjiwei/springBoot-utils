package com.graphql.graphQLSchema;

import com.graphql.Model.User;
import graphql.GraphQL;
import graphql.schema.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static graphql.Scalars.GraphQLInt;
import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLInputObjectField.newInputObjectField;
import static graphql.schema.GraphQLInputObjectType.newInputObject;
import static graphql.schema.GraphQLObjectType.newObject;

/**
 * Created by kangjiwei on 6/7/17.
 * 演示如何传参数(argument)来调用GraphQL api
 * <p>
 * blog: www.zhaiqianfeng.com
 */
public class _argument {
    public static void main(String[] args) {
      //服务端，数据源
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setName("zhaiqianfeng");
        user.setSex("男");
        user.setIntro("博主，专注于Linux,Java,nodeJs,Web前端:Html5,JavaScript,CSS3");
        users.add(user);

        user = new User();
        user.setName("zhaiqianfeng");
        user.setSex("男");
        user.setIntro("博主，专注于Linux,Java,nodeJs,Web前端:Html5,JavaScript,CSS3");
        users.add(user);

        // 定义 GraphQL 查询类型
        GraphQLObjectType userType = newObject()//定义User类型
                .name("User")
                .field(newFieldDefinition().name("name").type(GraphQLString))
                .field(newFieldDefinition().name("sex").type(GraphQLString))
                .field(newFieldDefinition().name("intro").type(GraphQLString))
                .field(newFieldDefinition().name("skills").type(new GraphQLList(GraphQLString)))
                .build();

        // 定义 GraphQL 增加类型
        GraphQLInputType userInput = newInputObject()// 定于输入类型
                .name("UserInput")
                .field(newInputObjectField().name("name").type(GraphQLString))
                .field(newInputObjectField().name("sex").type(GraphQLString))
                .field(newInputObjectField().name("intro").type(GraphQLString))
                .field(newInputObjectField().name("skills").type(new GraphQLList(GraphQLString)))//这个是数组查询
                .build();

        //定义暴露给客户端的查询 query api
        GraphQLObjectType queryType = newObject()//定义暴露给客户端的查询query api
                .name("userQuery")
                .field(newFieldDefinition()
                        .type(userType)
                        .name("user")
                        .argument(newArgument()
                                .name("id")
                                .type(new GraphQLNonNull(GraphQLInt)))
                        .dataFetcher(environment -> {
                            int id = environment.getArgument("id");
                            return users.get(id);
                        }))
                .field(newFieldDefinition()
                        .type(new GraphQLList(userType))
                        .name("users")
                        .dataFetcher(evn -> {
                            return users;
                        }))
                .build();

        //定义暴露给客户端的查询 mutaion api
        GraphQLObjectType mutaionType = newObject()//定义暴露给客户端的查询query api
                .name("userMutation")
                .field(newFieldDefinition()
                        .type(new GraphQLList(userType))
                        .name("addUserByInput") //增加方法
                        .argument(newArgument()
                                .name("userInfo") //增加实体类的key值。
                                .type(new GraphQLNonNull(userInput)))// 新增的实体类不可为空。
                        .dataFetcher(environment -> {//解析类型
                            Map<String, Object> userInfoMap = environment.getArgument("userInfo");
                            User userInfo = new User();
                            for (String key : userInfoMap.keySet()) {//遍历实体对象
                                switch (key) {
                                    case "name":
                                        userInfo.setName(userInfoMap.get("name").toString());
                                        break;
                                    case "sex":
                                        userInfo.setSex(userInfoMap.get("sex").toString());
                                        break;
                                    case "intro":
                                        userInfo.setIntro(userInfoMap.get("intro").toString());
                                        break;
                                    case "skills":  //等于数组情况下
                                        ArrayList<String> skillsList = (ArrayList<String>) userInfoMap.get("skills");
                                        String[] skills = new String[skillsList.size()];
                                        userInfo.setSkills(skillsList.toArray(skills));
                                        break;
                                }
                            }
                            users.add(userInfo);
                            return users;
                        }))
                .build();

        //创建Schema
        GraphQLSchema schema = GraphQLSchema.newSchema()
                .query(queryType)
                .mutation(mutaionType)
                .build();

        //测试输出
        GraphQL graphQL = GraphQL.newGraphQL(schema).build();

        //根据Id查询用户
        Map<String, Object> result = graphQL.execute("{user(id:0){name,sex,intro}}").getData();
        System.out.println("  根据用户id 查询用户 " + result);

        //查询用户列表
        result = graphQL.execute("{users{name}}").getData();
        System.out.println(" 查询用户列表 " + result);

        //添加用户列表
        result = graphQL.execute("mutation{addUserByInput(userInfo:{name:\"test2User\",sex:\"男\",intro:\"简介\",skills:[\"java\",\"nodejs\"]}){name　sex　intro}}").getData();
        System.out.println("添加用户列表  " + result);

    }
}