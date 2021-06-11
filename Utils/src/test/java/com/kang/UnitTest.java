package com.kang;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.util.UtilsApplication;
import lombok.Cleanup;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UtilsApplication.class)
public class UnitTest {

    public static int PRETTY_PRINT_INDENT_FACTOR = 4;
    public static String TEST_XML_STRING =
            "<?xml version=\"1.0\" ?><test attrib=\"moretest\">Turn this to JSON</test>";

    @Test
    public void book() {
        String jsonStr = "{\n" +
                "    \"type\":\"object\",\n" +
                "    \"required\":[\n" +
                "        \"流水号\"\n" +
                "    ],\n" +
                "    \"properties\":{\n" +
                "        \"姓名\":{\n" +
                "            \"type\":\"string\",\n" +
                "            \"title\":\"姓名\"\n" +
                "        },\n" +
                "        \"期次\":{\n" +
                "            \"type\":\"number\",\n" +
                "            \"title\":\"期次\"\n" +
                "        },\n" +
                "        \"流水号\":{\n" +
                "            \"type\":\"string\",\n" +
                "            \"title\":\"流水号\"\n" +
                "        },\n" +
                "        \"账单日\":{\n" +
                "            \"type\":\"string\",\n" +
                "            \"title\":\"账单日\"\n" +
                "        },\n" +
                "        \"身份证\":{\n" +
                "            \"type\":\"number\",\n" +
                "            \"title\":\"身份证\"\n" +
                "        },\n" +
                "        \"分期期数\":{\n" +
                "            \"type\":\"number\",\n" +
                "            \"title\":\"分期期数\"\n" +
                "        },\n" +
                "        \"发卡日期\":{\n" +
                "            \"type\":\"string\",\n" +
                "            \"title\":\"发卡日期\"\n" +
                "        },\n" +
                "        \"应还利息\":{\n" +
                "            \"type\":\"number\",\n" +
                "            \"title\":\"应还利息\"\n" +
                "        },\n" +
                "        \"应还时间\":{\n" +
                "            \"type\":\"string\",\n" +
                "            \"title\":\"应还时间\"\n" +
                "        },\n" +
                "        \"应还本金\":{\n" +
                "            \"type\":\"number\",\n" +
                "            \"title\":\"应还本金\"\n" +
                "        },\n" +
                "        \"待还利息\":{\n" +
                "            \"type\":\"number\",\n" +
                "            \"title\":\"待还利息\"\n" +
                "        },\n" +
                "        \"待还本金\":{\n" +
                "            \"type\":\"number\",\n" +
                "            \"title\":\"待还本金\"\n" +
                "        },\n" +
                "        \"待还罚息\":{\n" +
                "            \"type\":\"number\",\n" +
                "            \"title\":\"待还罚息\"\n" +
                "        },\n" +
                "        \"放款时间\":{\n" +
                "            \"type\":\"string\",\n" +
                "            \"title\":\"放款时间\"\n" +
                "        },\n" +
                "        \"放款金额\":{\n" +
                "            \"type\":\"number\",\n" +
                "            \"title\":\"放款金额\"\n" +
                "        },\n" +
                "        \"是否逾期\":{\n" +
                "            \"type\":\"string\",\n" +
                "            \"title\":\"是否逾期\"\n" +
                "        },\n" +
                "        \"核销标识\":{\n" +
                "            \"type\":\"string\",\n" +
                "            \"title\":\"核销标识\"\n" +
                "        },\n" +
                "        \"订单状态\":{\n" +
                "            \"type\":\"string\",\n" +
                "            \"title\":\"订单状态\"\n" +
                "        },\n" +
                "        \"账单状态\":{\n" +
                "            \"type\":\"string\",\n" +
                "            \"title\":\"账单状态\"\n" +
                "        },\n" +
                "        \"逾期天数\":{\n" +
                "            \"type\":\"number\",\n" +
                "            \"title\":\"逾期天数\"\n" +
                "        },\n" +
                "        \"首付金额\":{\n" +
                "            \"type\":\"number\",\n" +
                "            \"title\":\"首付金额\"\n" +
                "        },\n" +
                "        \"放款银行卡号\":{\n" +
                "            \"type\":\"number\",\n" +
                "            \"title\":\"放款银行卡号\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        Object read = JSONPath.read(jsonStr, "$['properties']");
        Map map = JSON.parseObject(JSON.toJSONString(read), Map.class);
        Set set = map.keySet();
        set.stream().forEach(key -> {
            System.out.println(key);
        });
        System.out.println(JSON.toJSONString(read));
        Object parse = JSON.parseObject(jsonStr);

    }

    @Test
    public void parseSQL() {
        String sql = "select name as name ,(select age from stu where name='1') from teacher where  height = '1' ";
        int i = sql.indexOf("(");
        if (i != -1) {
            int i1 = this.parseKuohao(sql, i);
            if (i1 == 0) {
                System.out.println("SQL语句不正确");
            }
        }
        System.out.println(i);
    }

    public int parseKuohao(String str, Integer index) {
        int length = str.length();
        int costLength = index;
        int endKuohaoIndex = 0;
        while (costLength <= length) {
            char c = str.charAt(costLength);
            if (c == '(') {
                endKuohaoIndex = this.parseKuohao(str, costLength);
            }
            if (c == ')') {
                endKuohaoIndex = costLength;
                //停止循环
                costLength = length + 1;
            }
            costLength++;
        }
        return endKuohaoIndex;
    }

    @Test
    public void ss() {
        String content = "def method(){}";

        String pattern = "def(\\s*)";

        boolean isMatch = Pattern.matches(pattern, content);
        if (isMatch) {
            System.out.println("有");
        } else {
            System.out.println("没有");
        }
    }

    @Test
    public void xmlToJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("C:\\Users\\huanhuan\\Desktop\\testxml\\testxml");
        File[] files = file.listFiles();
        for (File xmlFile : files) {
            System.out.println("------------------------------" + xmlFile.getName());
            try {
                if (!xmlFile.getName().endsWith(".xml")) {
                    System.out.println("文件不是xml文件: " + xmlFile.getName());
                    continue;
                }
                FileReader fileReader = new FileReader(xmlFile);
                JSONObject xmlJSONObj = XML.toJSONObject(fileReader);
                String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
                Map jsonMap = objectMapper.readValue(jsonPrettyPrintString, new TypeReference<Map>() {
                });
                String resultStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this.parseObj(jsonMap));
                System.out.println("测试数据: " + resultStr);
//                writeJsonFile(resultStr, xmlFile.getName(), ".json", "json");
//                writeJsonFile(resultStr, xmlFile.getName(), ".txt", "txt");
            } catch (JSONException | FileNotFoundException | JsonProcessingException je) {
                System.out.println("失敗： " + xmlFile.getName() + "  " + je.toString());
            }
        }

    }


    /**
     * 解析Object
     *
     * @param object
     */
    public Object parseObj(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        if (object instanceof ArrayList) {
            List<Object> list = (List) object;
            if (list.size() == 0) {
                return null;
            }
            for (Object map : list) {
                if (map instanceof Integer) {
                    list.set(list.indexOf(map), map + "");
                    continue;
                }

                if (map instanceof Long) {
                    list.set(list.indexOf(map), map + "");
                    continue;
                }
                if (map instanceof String) {
                    continue;
                }
                Map<String, Object> resultMap = objectMapper.readValue(objectMapper.writeValueAsString(parseObj(map)), new TypeReference<Map>() {
                });
                list.set(list.indexOf(map), resultMap);
            }
        } else if (object instanceof Map) {
            Map<String, Object> getMap = (Map<String, Object>) object;
            Set<Map.Entry<String, Object>> entries = getMap.entrySet();
            entries.forEach(map -> {
                if ("PA01BI01".equals(map.getKey())) {
                    Object value = map.getValue();
                    if (value instanceof String) {
                        System.out.println("字符串类型" + value);
                    } else if (value instanceof Integer) {
                        System.out.println("数值类型" + value);
                    }
                }
                //System.out.println("get value " + map.getValue());
                boolean isStr = map.getValue() instanceof String;
                if (map.getValue() instanceof Integer) {
                    map.setValue(map.getValue() + "");
                } else if (map.getValue() instanceof Long) {
                    map.setValue(map.getValue() + "");
                }
                if (!isStr) {
                    try {
                        map.setValue(this.parseObj(map.getValue()));
                    } catch (JsonProcessingException e) {
                        System.out.println(map.getValue());
                        e.printStackTrace();
                    }
                }
            });
        }
        return object;
    }


    /**
     * @param fileName
     */
    public void writeJsonFile(String content, String fileName, String suffix, String jsonOrTxt) {
        try {
            File targetFile = new File("D:\\KuGou\\" + jsonOrTxt + "\\" + fileName.replace(".xml", suffix));
            @Cleanup RandomAccessFile randomFile = new RandomAccessFile(targetFile, "rw");
            //RandomAccessFile  默认编码格式"ISO-8859-1" 需要设置为"utf-8"
            randomFile.write(content.getBytes("gbk"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
      /*  String jsonStr = "[\n" +
                "    {\n" +
                "        \"norm\":[\n" +
                "            {\n" +
                "                \"sources\":[\n" +
                "                    {\n" +
                "                        \"version\":\"V1.0.0\",\n" +
                "                        \"__typename\":\"KeyIndexSource\",\n" +
                "                        \"sourceName\":\"2代征信数据源\",\n" +
                "                        \"sourceUuid\":\"3dcae194-3cc6-4f30-9f15-9d998a919ba2\",\n" +
                "                        \"sourceSchema\":[\n" +
                "                            {\n" +
                "                                \"type\":\"2\",\n" +
                "                                \"mapping\":\"ORDERNO\",\n" +
                "                                \"paramName\":\"order_no\",\n" +
                "                                \"__typename\":\"SourceSchema\"\n" +
                "                            }\n" +
                "                        ]\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"indexName\":\"到期一次性还本信用贷款机构数_未结清\",\n" +
                "                \"indexUuid\":\"4fde269cec024ceea10c4e430e22ce49\",\n" +
                "                \"__typename\":\"KeyIndex\",\n" +
                "                \"indexVersion\":\"V1.0.0\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"nameCn\":\"订单1\",\n" +
                "        \"nameEn\":\"ORDERNO\",\n" +
                "        \"__typename\":\"KeyGroup\"\n" +
                "    }\n" +
                "]";
        Object read = JsonPath.parse(jsonStr).read("$.*.[?(@.nameEn == 'ORDERNO')].norm.*.[?(@.indexUuid == '4fde269cec024ceea10c4e430e22ce49')].sources.*.[?(@.sourceUuid == '3dcae194-3cc6-4f30-9f15-9d998a919ba2')]sourceSchema");
        System.out.println(read);
        List<Map<String, Object>> read1 = JsonPath.parse(read).read("$.*.norm[0].sources[0].sourceSchema");
        for (Map<String, Object> map : read1) {
            System.out.println(map);
        }*/
    }
}
