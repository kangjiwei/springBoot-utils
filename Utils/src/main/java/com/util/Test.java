package com.util;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saasquatch.jsonschemainferrer.*;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Arrays;

/**
 * Created by rongrong on 2019/12/17.
 */
@Slf4j
public class Test {


    private static final ObjectMapper mapper = new ObjectMapper();
    private static final JsonSchemaInferrer inferrer = JsonSchemaInferrer.newBuilder()
            .setSpecVersion(SpecVersion.DRAFT_06)
            // Requires commons-validator
            .addFormatInferrers(FormatInferrers.email(), FormatInferrers.ip())
            .setAdditionalPropertiesPolicy(AdditionalPropertiesPolicies.notAllowed())
            .setRequiredPolicy(RequiredPolicies.nonNullCommonFields())
            .addEnumExtractors(EnumExtractors.validEnum(java.time.Month.class),
                    EnumExtractors.validEnum(java.time.DayOfWeek.class))
            .build();

    public static void main(String[] args) {



    }


    /**
     * 日志，就是记录日志不同的日志
     * trace
     * warn
     * info
     * error
     * debug
     */
    public void readFileContent() {
        String filePath = "C:\\Users\\rongrong\\Desktop\\BuilderExcel.java";
        File file = new File(filePath);
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);

            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String getWorld = null;
                if ((getWorld = (getWorld(line))) != null)
                    System.out.println(getWorld);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getWorld(String content) {
        String indexName = "indexName";
        int i = content.lastIndexOf(indexName);

        if (i > -1) {
            String subStr = content.substring(i + indexName.length());
            System.out.println(" 元数据  " + content);
//            String[] split = content.split("; : , ， . 。 : \n " + indexName);
            String[] split = content.split(":|;|,|\n|" + indexName);
            System.out.println(split.length);
            System.out.println(split[1]);
            return subStr;
        }
        return null;
    }
}
