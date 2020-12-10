package com.util;


import lombok.extern.slf4j.Slf4j;

import java.io.*;
/**
 * Created by rongrong on 2019/12/17.
 */
@Slf4j
public class Test {


    public static void main(String[] args) {
        log.info("数据增加成功！");
        log.error("错误信息！");

  /*      User user =  new User("最爱的你","美丽的故事","大宗师");
        ObjToMap  objToMap = new ObjToMap();
        Map<String,ObjToMap> map = objToMap.toMap(user);
        System.out.println(map.toString());*/

   /*     BuilderExcel  excel = new BuilderExcel();
        excel.operation();*/
        /*Test test = new Test();
        test.readFileContent();*/
        //三目运算法，如果条件成立事条件1 ，否则是条件2
        System.out.println(true ? "hao" : "no");

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
        log.info("");
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
