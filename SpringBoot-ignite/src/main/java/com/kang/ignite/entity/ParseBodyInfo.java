package com.kang.ignite.entity;


import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.text.Collator;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author kangjw
 * @Date 2020/5/12.
 * @Describe 解析Webflux表单
 */
@Component
public class ParseBodyInfo {

    /**
     * multipart/form-data 排序
     *
     * @param bodyInfo
     * @return
     */
    public static String parseBodyFormData(String bodyInfo) {
        if (bodyInfo != null && bodyInfo.length() > 0) {
            Pattern p = Pattern.compile("\t|\r|\n");
            Matcher m = p.matcher(bodyInfo);
            bodyInfo = m.replaceAll("");
        }
        Map<String, Object> retMap1 = splitInfo(bodyInfo);
        //移除表单中的hmacheck
        retMap1.remove("hmacheck");
        //移除表单中的token
        retMap1.remove("token");
        Set<String> keys = retMap1.keySet();
        Comparator comparator = Collator.getInstance(Locale.CHINA);
        Arrays.sort(keys.toArray(), comparator);
        List<Object> formDatas = new ArrayList<>();
        keys.stream().forEach(key -> {
            formDatas.add(retMap1.get(key));
        });
        Object[] objects = formDatas.toArray(new String[formDatas.size()]);
        String retStr = org.apache.commons.lang.StringUtils.join(objects);
        return retStr;
    }

    public static Map<String, Object> splitInfo(String bodyInfo) {
        Map<String, Object> retMap = new HashMap<>();
        String[] names = bodyInfo.split("name=");
        for (String name : names) {
            if (name.indexOf("----------------------------") > 0) {
                String[] split = name.split("----------------------------");
                if (split.length > 0) {
                    String sp = split[0];
                    String[] split1 = sp.split("\"");
                    String keyStr = split1[1];
                    String valStr = sp.substring(keyStr.length() + 2, sp.length());
                    retMap.put(keyStr, valStr);
                }
            }
        }
        return retMap;
    }

    public static String parseBodyJson(String bodyInfo) {
        if (bodyInfo != null && bodyInfo.length() > 0) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(bodyInfo);
            bodyInfo = m.replaceAll("");
            Map map = JSON.parseObject(bodyInfo, Map.class);
            //移除表单中的hmacheck
            map.remove("hmacheck");
            //移除表单中的token
            map.remove("token");
            return JSON.toJSONString(map);
        }
        return null;
    }


}
