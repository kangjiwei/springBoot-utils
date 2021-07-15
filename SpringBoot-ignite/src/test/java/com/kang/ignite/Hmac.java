package com.kang.ignite;

import com.kang.ignite.entity.HmacKeyConfig;
import com.kang.ignite.entity.ParseBodyInfo;
import org.junit.jupiter.api.Test;

public class Hmac {


    @Test
    public void TestHmac(){
        String  key = "o0Ew3OegDPhH3QbqrDsX30J18QRw6JN1XtHclm7x7sJaemTxEDRFcfPuY1ABhb3A9w0HC2dCGdd5lizKi4rn1A==";
        String jsonStr = "{\n" +
                "\t\"modelParam\": {\n" +
                "\t\t\"mvUuid\": \"9e0631f058d746f2b05b43f6579f039d\",\n" +
                "\t\t\"keyGroup\": {\n" +
                "\t\t\t\"cert_num\": \"91500113345970299C\",\n" +
                "\t\t\t\"ent_name\": \"重庆威顿华融科技有限公司\"\n" +
                "\t\t},\n" +
                "\t\t\"callUuid\": \"7ebb9f85177f4999940416a00122\"\n" +
                "\t},\n" +
                "\t\"sourceData\": {\n" +
                "\t\t\"keys\": \"91500113345970299C|重庆威顿华融科技有限公司\",\n" +
                "\t\t\"version\": \"2.0\",\n" +
                "\t\t\"resultStr\": {\n" +
                "\t\t\t\"order_serialno\": \"1020200509000002\",\n" +
                "\t\t\t\"cert_num\": \"91500000MA5UTMTB6F\",\n" +
                "\t\t\t\"ent_name\": \"重庆中链融科技有限公司\",\n" +
                "\t\t\t\"detail\": {\n" +
                "\t\t\t\t\"lx_term_max\": 36,\n" +
                "\t\t\t\t\"apply_lx_amount\": 1290.01,\n" +
                "\t\t\t\t\"lx_rate_max\": 0,\n" +
                "\t\t\t\t\"lx_amt\": 1000000,\n" +
                "\t\t\t\t\"lx_term\": 12,\n" +
                "\t\t\t\t\"lx_repay_type_cycle\": \"MONTH\",\n" +
                "\t\t\t\t\"apply_time\": \"2020-05-06\",\n" +
                "\t\t\t\t\"lx_amout_max\": 1000,\n" +
                "\t\t\t\t\"product_no\": \"002003\",\n" +
                "\t\t\t\t\"lx_discount_rate\": \"0.002\",\n" +
                "\t\t\t\t\"lx_term_min\": 3,\n" +
                "\t\t\t\t\"lx_due_date\": \"2020-10-06\",\n" +
                "\t\t\t\t\"lx_issuance_ent\": \"重庆数宜信信用管理有限公司\",\n" +
                "\t\t\t\t\"lx_rate_min\": 0,\n" +
                "\t\t\t\t\"lx_issuance_date\": \"2020-05-06\",\n" +
                "\t\t\t\t\"lx_amout_min\": 0,\n" +
                "\t\t\t\t\"repay_type\": \"REPAY_TYPE_2\",\n" +
                "\t\t\t\t\"lx_original_amout\": 4000000,\n" +
                "\t\t\t\t\"lx_ent_credit_amount\": 2000000\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t\"sourcecode\": \"lx\"\n" +
                "\t},\n" +
                "\t\"hmacheck\": \"b95f3d8962e20ee08575c7251ca17b65\",\n" +
                "\t\"token\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwYXNzV29yZCI6ImMzMzM2NzcwMTUxMWI0ZjYwMjBlYzYxZGVkMzUyMDU5IiwiYXVkIjoiQWxsIiwiY3VycmVudFVzZXIiOiJ6aG9uZ2xyIiwiaXNzIjoiemxyVXNlciIsImV4cCI6MTYwMTU0MDUzOSwidXNlcklkIjoiNTgwIiwiaWF0IjoxNjAxNDU0MTM5fQ.DhQRTmK-Vt1Z41DpgCvqszkkKe4y3rUhwdD4hwRcBtU\"\n" +
                "}";

        String formData = ParseBodyInfo.parseBodyJson(jsonStr);
        String hmacVal = HmacKeyConfig.encryptHmacMD5(formData.getBytes(), key.getBytes());
        System.out.println(hmacVal);
    }
}
