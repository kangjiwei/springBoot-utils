package com.util.Entity;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by rongrong on 2019/12/23.
 */
public class HttpTemplate {

    private String  httpUrl = "http://localhost:8081/hello";


    @Test
    public void  httpGet(){
        System.out.println("hahah");
    }

    public static void main(String[] args) {
    /*  String  httpUrl = "http://localhost:8081/hello";
        try {
            URL url = new URL(httpUrl + "/getTest?name=001");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setDoOutput(true); // 设置该连接是可以输出的
            connection.setRequestMethod("GET"); // 设置请求方式
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line = null;
            StringBuilder result = new StringBuilder();
            while ((line = br.readLine()) != null) { // 读取数据
                result.append(line + "\n");
            }
            connection.disconnect();

            System.out.println(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        */

/*    String[]  mans = {"123123"};
        System.out.println("huanjue  "+mans.toString());
        Map<String,Object> map = new HashMap();
        map.put("one",map);

        System.out.println(map.get("one")+"");*/
        Callable   cll = new Callable() {
            @Override
            public Object call() throws Exception {
                java.lang.Thread.sleep(5000);
                System.out.println("执行完毕！");
                return null;
            }
        };
        FutureTask<String> ft1 = new FutureTask<String>(cll);
        new Thread(ft1).start();
        System.out.println("异步执行！");

    }

}
