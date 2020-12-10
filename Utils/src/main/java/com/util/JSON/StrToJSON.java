package com.util.JSON;

import com.util.utils.ObjToMap;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONString;

/**
 * Created by rongrong on 2019/12/19.
 */
public class StrToJSON {


    //json 格式样例

    public static void main(String[] args) {

        String  jsonStr ="[" +
                "{index_info_uuid:\'uuid\',index_info_name:\'name\',unit_uuid:\'unit_uuid\'}," +
                "{index_info_uuid:\'uuid\',index_info_name:\'name\',unit_uuid:\'unit_two\'}" +
                "]";
        JSONArray jsonArray = JSONArray.fromObject(jsonStr);
        jsonArray.add("{index_info_uuid:\'那个女孩\',index_info_name:\'张细则\',unit_uuid:\'unit_uuid\'}");
        if(jsonArray.size()>0 ){
            for(int i= 0;i<jsonArray.size();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Object index_info_uuid = jsonObject.get("index_info_uuid");
                System.out.println(index_info_uuid);
            }
        }
        Object o = JSONArray.toArray(jsonArray);
        System.out.println(jsonArray.toString());
    }
}
