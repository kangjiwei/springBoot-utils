package com.graphql.graphQLSchema;

import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

/**
 *  @Author kangjiwei
 *  @Date 2019/12/10
 *  生成schema
 */
@Component
public class GraphQLSchemas {

    @Autowired
    GraphQLDataFetchers  graphQLDataFetchers;

    // 创建 schema
    public GraphQLSchema buildSchema(String sdl) {
        //读取 graphqls  文件
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring(){
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query").dataFetcher("bookById",graphQLDataFetchers.getBookByIdDataFetcher()))
                .type(newTypeWiring("Query").dataFetcher("author",graphQLDataFetchers.getAuthorDataFetcher()))
                .build();
    }

}
