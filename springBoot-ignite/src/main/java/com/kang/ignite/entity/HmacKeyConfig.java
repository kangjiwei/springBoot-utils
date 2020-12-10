package com.kang.ignite.entity;

import org.springframework.context.annotation.Configuration;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @Author kangjw
 * @Date 2020/4/28.
 */

@Configuration
public class HmacKeyConfig {

    // 获取 HmacMD5 Key
    public static byte[] getHmacMd5Key() {
        return getHmacKey("HmacMD5");
    }

    // 获取 HmacSHA256
    public static byte[] getHmacSha256Key() {
        return getHmacKey("HmacSHA256");
    }

    // 获取 HmacSHA512
    public static byte[] getHmacSha512Key() {
        return getHmacKey("HmacSHA512");
    }

    // 获取 HMAC Key
    public static byte[] getHmacKey(String type) {
        try {
            // 1、创建密钥生成器
            KeyGenerator keyGenerator = KeyGenerator.getInstance(type);
            // 2、产生密钥
            SecretKey secretKey = keyGenerator.generateKey();
            // 3、获取密钥
            byte[] key = secretKey.getEncoded();
            return key;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // HMAC MD5 加密
    public static String encryptHmacMD5(byte[] data, byte[] key) {
        return encryptHmac(data, key, "HmacMD5");
    }

    // HMAC SHA256 加密
    public static String encryptHmacSHA256(byte[] data, byte[] key) {
        return encryptHmac(data, key, "HmacSHA256");
    }

    // HMAC SHA521 加密
    public static String encryptHmacSHA512(byte[] data, byte[] key) {
        return encryptHmac(data, key, "HmacSHA512");
    }

    // 基础MAC 算法
    public static String encryptHmac(byte[] data, byte[] key, String type) {
        try {
            // 1、还原密钥
            SecretKey secretKey = new SecretKeySpec(key, type);
            // 2、创建MAC对象
            Mac mac = Mac.getInstance(type);
            // 3、设置密钥
            mac.init(secretKey);
            // 4、数据加密
            byte[] bytes = mac.doFinal(data);
            // 5、生成数据
            String rs = encodeHex(bytes);
            return rs;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 数据准16进制编码
    public static String encodeHex(final byte[] data) {
        return encodeHex(data, true);
    }

    // 数据转16进制编码
    public static String encodeHex(final byte[] data, final boolean toLowerCase) {
        final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        final char[] DIGITS_UPPER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        final char[] toDigits = toLowerCase ? DIGITS_LOWER : DIGITS_UPPER;
        final int l = data.length;
        final char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0x0F & data[i]];
        }
        return new String(out);
    }
}
