package com.graphql.GraphQL_learn;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

/**
 * Created by Administrator on 2019/12/10.
 */
@Component
public class GraphQLS_schema {


    @Autowired
    GraphQLS_fetcher  fetcher;

    @Autowired
    GraphQL   graphQL;

    @PostConstruct
    public  void  buildSchema() throws IOException {
        SchemaParser schemaParser = new SchemaParser();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        TypeDefinitionRegistry typeRegistry = schemaParser.parse(readGraphQLS());
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeRegistry, buildRuntimeWiring());
        graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    public GraphQL getGraphQL(){
        return graphQL;
    }

    public String  readGraphQLS() throws IOException {
        URL resource = Resources.getResource("schemas.graphqls");
        String sdl = Resources.toString(resource, Charsets.UTF_8);
        return sdl;
    }

    public RuntimeWiring  buildRuntimeWiring(){
        return  RuntimeWiring.newRuntimeWiring()
                .type(buildQueryRuntimeWiring())
                .type(buildMutationRuntimeWiring())
                .build();
    }

    //查询
    public TypeRuntimeWiring.Builder buildQueryRuntimeWiring() {
        TypeRuntimeWiring.Builder builder = newTypeWiring("Query")
                .dataFetcher("bookById",fetcher.getDataFetcher())
                .dataFetcher("queryStu", fetcher.getStuFetcher());
        return builder;
    }

    //查询
    public TypeRuntimeWiring.Builder buildMutationRuntimeWiring() {
        TypeRuntimeWiring.Builder builder = newTypeWiring("mutation")
                .dataFetcher("createBook",fetcher.addBookInfo())
                .dataFetcher("updateBook",fetcher.updateBook())
                .dataFetcher("deleteById",fetcher.deleteBookById())
                .dataFetcher("addUser",fetcher.addUser());
        return builder;
    }
}
