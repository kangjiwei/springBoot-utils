package com.kang.minio.utils;

import com.sun.javaws.exceptions.InvalidArgumentException;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

/**
 * @Author kangjiwei
 * @Date 2020/7/31
 */
@Component
public class MinioUtil {

    private String bucketName = "zlr-bucket";

    public MinioClient minioClient = new MinioClient("http://192.168.20.10:9000", "admin", "admin123");

    /**
     * 上传文件
     *
     * @param objectName 上传后存储在bucket中的文件名
     * @param filePath   上传的本地文件路径
     */
    public String uploadFile(String objectName, String filePath) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        // MinioClient client = new MinioClient("http://192.168.128.128:9000", "minio", "minio123");
        try {
            // 若不存在bucket，则新建
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!isExist) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            } else {
                System.out.println("Bucket already exists.");
            }
            //PutObjectOptions  options = new PutObjectOptions(1000,3000);
            // 使用 putObject 上传文件
            minioClient.putObject(bucketName, objectName, filePath, null);
            System.out.println("上传文件成功！");

            String objectUrl = minioClient.getObjectUrl(bucketName, objectName);
            return objectUrl;
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
        }
        return null;
    }


    /**
     * 下载文件
     *
     * @param objectName   上传后存储在bucket中的文件名
     * @param downloadPath 下载文件保存路径
     */
    public void downloadFile(String objectName, String downloadPath) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        File file = new File(downloadPath);
        OutputStream out = null;
        InputStream inputStream = null;
        try {
            out = new FileOutputStream(file);
            inputStream = minioClient.getObject(bucketName, objectName);
            byte[] tempbytes = new byte[1024];
            int byteread = 0;
            while ((byteread = inputStream.read(tempbytes)) != -1) {
                out.write(tempbytes, 0, byteread);
            }
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
        } finally {
            if(out != null){
                out.close();
            }
            if(inputStream != null){
                inputStream.close();
            }
        }

    }

    /**
     * 罗列文件
     *
     * @param bucketName
     */
    public void listFile(String bucketName) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(bucketName);
            Iterator<Result<Item>> iterator = results.iterator();
            while (iterator.hasNext()) {
                Item item = iterator.next().get();
                System.out.println(item.objectName() + ", " + item.objectName() + "B");
            }
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
        }
    }


    /**
     * 删除对象
     *
     * @param bucketName
     * @param objectName
     * @return
     */
    public boolean removeFile(String bucketName, String objectName) {
        try {
            minioClient.removeObject(bucketName, objectName);
            System.out.println("删除成功！");
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        }
        return true;
    }


}
