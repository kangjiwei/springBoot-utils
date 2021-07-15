package com.kang.minio;

import com.kang.minio.controller.minioController;
import com.kang.minio.utils.MinioBucket;
import com.kang.minio.utils.MinioUtil;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import io.minio.http.Method;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/**
 *  1，url加密。
 *  2，
 */
//@SpringBootTes
public class MinioApplicationTests {

    private MinioUtil minioUtil = new MinioUtil("http://192.168.128.128:9000", "minio", "minio123","");

    @Test
    public void upload() {
        try {
            //Minio 根据文件名称判断文件是否已经存在
            //MinioClient client = new MinioClient("http://192.168.128.128:9000", "minio", "minio123");
            String objectName = minioUtil.uploadFile("jdk.exe", "D:\\SOFTWARE\\jdk\\jdk-8u111-windows-x64.exe");
            System.out.println("文件路径！" + objectName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.println("指标");

    }


    /**
     * bucket下面的所有文件
     */
    @Test
    public void getBucketList(){
        try {
            minioUtil.listFile("bucket");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void removeFile(){
        minioUtil.removeFile("bucket","settings.xml");
        getBucketList();

    }


    @Test
    public void listBucket(){
        MinioBucket.listBucket();
    }


    @Test
    public void createBucket(){
        MinioBucket.makeBucket("zlr-bucket","china");
    }

    @Test
    public void downLoadFile(){
        try {
            long stTime = System.currentTimeMillis();
            minioUtil.downloadFile("jdk.exe","C:\\Kang\\jdk.exe");
            System.out.println("下载成功！");
            System.out.println("花费时间："+ (System.currentTimeMillis() - stTime ));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getObjectNum() throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, InvalidExpiresRangeException, ServerException, InternalException, NoSuchAlgorithmException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
        MinioClient minioClient = MinioClient.builder()
                .endpoint("http://192.168.10.254:3100")
                .credentials("minio1234567890", "minio1234567890")
                .build();
        String objectUrl = minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket("zlr-bucket")
                        .object("download/file/test/36c4908e-1f99-4c24-8b4f-0eb54f7438c5.py")
                        .expiry(7, TimeUnit.MINUTES)
                        .build());
        System.out.println(objectUrl);
    }


}
