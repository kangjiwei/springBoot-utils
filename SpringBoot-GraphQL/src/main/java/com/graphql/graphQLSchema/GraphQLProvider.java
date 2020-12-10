package com.graphql.graphQLSchema;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

/**
 * @Author kangjiwei
 * @Date 2019/12/10
 */
@Component
public class GraphQLProvider {

    private GraphQL   graphQL;

    @Autowired
    GraphQLSchemas schema;

    @Bean
    public GraphQL  graphQL(){
         return  graphQL;
    }

    //项目启动的时候，就会执行该代码。项目启动的时候初始化。
    @PostConstruct
    public void init() throws IOException {
        URL resource = Resources.getResource("schemas.graphqls");
        String sdl = Resources.toString(resource, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = builderSchema(sdl);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private GraphQLSchema builderSchema(String sdl){
        return schema.buildSchema(sdl);
    }


}
