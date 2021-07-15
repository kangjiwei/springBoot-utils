package com.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.saasquatch.jsonschemainferrer.*;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

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

    public void parseSql() throws IOException {
        String filePath = "D:\\工作文件\\replace.sql";
        int bufSize = 1024;
        byte[] tempbytes = new byte[bufSize];
        int byteread = 0;

        BufferedReader br = new BufferedReader(new FileReader(filePath));
        StringBuffer stringBuffer = new StringBuffer();
        String sqlStr = null;
        // 读入多个字节到字节数组中，byteread为一次读入的字节数
        while ((sqlStr = br.readLine()) != null) {
            if (sqlStr.contains("insert")) {
                sqlStr = sqlStr.replace(",", "");
                String sqlContent = stringBuffer.toString();
                if (!sqlContent.contains("from_unixtime")) {
                    String[] split = sqlContent.split(",");
                    String replaceBase = split[split.length - 1];
                    sqlContent = sqlContent.replace(replaceBase, "from_unixtime(" + replaceBase + ")");
                }

                System.out.print(sqlContent + "\n");
                File f = new File("D:\\dd.sql");
                FileWriter fw = new FileWriter(f, true);
                PrintWriter pw = new PrintWriter(fw);
                pw.println(sqlContent + "\n");
                pw.flush();
                stringBuffer.setLength(0);
            }
            stringBuffer.append(sqlStr.replace("\r\n", "")
                    .replace("\r\n1", "")
                    .replace("\r\n0", "")
                    .replace("\r\nselect", ""));
        }

    }

    public static void main(String[] args) throws Exception {
        Test test = new Test();
        test.update("kang","7");
      /*  for (int i = 1; i < 100000000; i++) {
            test.insert("麦田怪圈" + i);
        }*/
        System.out.println("---------操作结束----------");
    }


    /**
     * 新增数据
     *
     * @param values
     */
    public void insert(String values) {
        String URL = "jdbc:oracle:thin:@192.168.0.213:1521:orcl";
        String USER = "alignment_estage";
        String PASSWORD = "icbcgd";
        Statement st = null;
        Connection conn = null;
        try {
            //1.加载驱动程序
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //2.获得数据库链接
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            //3.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
            st = conn.createStatement();
            boolean rs = st.execute("insert into kang_temp values('" + values + "')");
            if (rs)
                System.out.println("新增数据库成功");
            else
                System.out.println("新增数据库失败");
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 新增数据
     *
     * @param values
     */
    public void update(String values, String whereCol) {
        String URL = "jdbc:oracle:thin:@192.168.0.213:1521:orcl";
        String USER = "alignment_estage";
        String PASSWORD = "icbcgd";
        Statement st = null;
        Connection conn = null;
        try {
            //1.加载驱动程序
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //2.获得数据库链接
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            //3.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
            st = conn.createStatement();
            boolean rs = st.execute("update  kang_temp k set temp_code = '" + values + "' where temp_code = '" + whereCol + "'");
            if (rs)
                System.out.println("修改数据库成功");
            else
                System.out.println("修改数据库失败");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
