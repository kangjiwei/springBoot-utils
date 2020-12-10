package com.kang.minio.utils;

import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Bucket;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @Author kangjiwei
 * @Date 2020/8/3
 * @Describe bucket管理
 */
public class MinioBucket {

    /* play.min.io for test and development. */
   private static MinioClient minioClient =
            MinioClient.builder()
                    .endpoint("http://192.168.20.10:9000")
                    .credentials("admin", "admin123")
                    .build();

    /* Amazon S3: */
    // MinioClient minioClient =
    //     MinioClient.builder()
    //         .endpoint("https://s3.amazonaws.com")
    //         .credentials("YOUR-ACCESSKEY", "YOUR-SECRETACCESSKEY")
    //         .build();


    /**
     * 查询所有的Bucket
     */
    public static void  listBucket(){
        try {
            // List buckets we have atleast read access.
            List<Bucket> bucketList = minioClient.listBuckets();
            for (Bucket bucket : bucketList) {
                System.out.println(bucket.creationDate() + ", " + bucket.name());
            }
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }


    /**
     * 创建 Bucket
     * @return
     */
    public static boolean makeBucket(String bucketName,String regionName){
        try {
          /*  // Create bucket 'my-bucketname' if it doesn`t exist.
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket("my-bucketname").build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket("my-bucketname").build());
                System.out.println("my-bucketname is created successfully");
            }

            // Create bucket 'my-bucketname-in-eu' in 'eu-west-1' region if it doesn't exist.
            if (!minioClient.bucketExists(
                    BucketExistsArgs.builder().bucket("my-bucketname-in-eu").build())) {
                minioClient.makeBucket(
                        MakeBucketArgs.builder().bucket("my-bucketname-in-eu").region("eu-west-1").build());
                System.out.println("my-bucketname-in-eu is created successfully");
            }*/

            // Create bucket 'my-bucketname-in-eu-with-object-lock' in 'eu-west-1' with object lock
            // functionality enabled.

            if (minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                System.out.println(bucketName + " is already exists!");
            }else{
                System.out.println(bucketName+ " is not exists!");
            }

        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 移除指定的Bucket
     */
    public static void removeBucket(String bucketName,String regionName){
        try {
            // Remove bucket 'my-bucketname' if it exists.
            // This operation will only work if your bucket is empty.
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (found) {
                minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).region(regionName).build());
                System.out.println(bucketName + " is removed successfully");
            } else {
                System.out.println(bucketName + " does not exist");
            }
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除bucket加密配置
     */
    public void deleteEncryption(String bucketName,String regionName){
        try {
            minioClient.deleteBucketEncryption(DeleteBucketEncryptionArgs.builder().bucket(bucketName).region(regionName).build());
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除bucket的生命周期限制
     * @param bucketName
     * @param regionName
     */
    public void deleteLiftCycle(String bucketName,String regionName){
        try {
            minioClient.deleteBucketLifeCycle(DeleteBucketLifeCycleArgs.builder().bucket(bucketName).region(regionName).build());
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        }


    }


    /**
     * 删除策略
     * @param bucketName
     * @param regionName
     */
    public void deleteBucketPolicy(String bucketName,String regionName){
        try {
            minioClient.deleteBucketPolicy(DeleteBucketPolicyArgs.builder().bucket(bucketName).region(bucketName).build());
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        }

    }



}
