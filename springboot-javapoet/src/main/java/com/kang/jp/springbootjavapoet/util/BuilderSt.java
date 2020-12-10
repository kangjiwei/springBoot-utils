package com.kang.jp.springbootjavapoet.util;

import com.squareup.javapoet.*;
import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;
import org.junit.Test;

import javax.lang.model.element.Modifier;
import java.text.StringCharacterIterator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuilderSt {


    @Test
    public void buildMethod() {
        CodeBlock sumOfTen = CodeBlock.builder()
                .addStatement("int sum = 0")
                .beginControlFlow("for (int i = 0; i <= 10; i++)")
                .addStatement("sum += i")
                .endControlFlow()
                .build();
        MethodSpec methodSpec = MethodSpec.methodBuilder("sumOfThen").addCode(sumOfTen).build();
        String s1 = methodSpec.code.toString();
        System.out.println(s1);
    }


    @Test
    public void fieldSpecification() {
        FieldSpec spec = FieldSpec.builder(String.class, "fieldName").addModifiers(Modifier.PRIVATE).build();
        System.out.println(spec.toString());
        FieldSpec defaultName = FieldSpec
                .builder(String.class, "DEFAULT_NAME")
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                .initializer("\"Alice\"")
                .build();
        System.out.println(defaultName.toString());
    }

    @Test
    public void parametersSpec() {
        ParameterSpec strings = ParameterSpec.builder(
                ParameterizedTypeName.get(ClassName.get(Map.class),
                        TypeName.get(String.class),
                        ParameterizedTypeName.get(ClassName.get(HashMap.class), TypeName.get(Integer.class), TypeName.get(String.class))), "strings"
        ).build();
        MethodSpec methodSpec = MethodSpec.methodBuilder("sumOfTen")
                .addParameter(int.class, "num")
                .addParameter(strings)
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                .build();
        System.out.println(methodSpec.toString());
    }


    @Test
    public void generatingClass() {
        FieldSpec fieldSpec = FieldSpec.builder(
                String.class, "name"
        ).addModifiers(Modifier.PUBLIC).build();

        TypeSpec typeSpec = TypeSpec
                .classBuilder("Person")
                .addModifiers(Modifier.PUBLIC)
                .addField(fieldSpec)
                .addMethod(MethodSpec
                        .methodBuilder("getName")
                        .addModifiers(Modifier.PUBLIC)
                        .returns(String.class).addStatement("return this.name").build())
                .addMethod(
                        MethodSpec.methodBuilder("setName")
                                .addParameter(TypeName.get(String.class), "name")
                                .addModifiers(Modifier.PUBLIC)
                                .returns(String.class)
                                .addStatement("this.name = name")
                                .build())
                .addMethod(MethodSpec.methodBuilder("showInfo")
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(TypeName.get(String.class),"name")
                        .addCode("System.println.out(\"name\")")
                        .build()
                ).build();

        System.out.println(typeSpec.toString());
    }


}
