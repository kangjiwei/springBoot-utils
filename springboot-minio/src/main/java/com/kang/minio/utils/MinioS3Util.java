package com.kang.minio.utils;

import com.fasterxml.jackson.annotation.JsonRawValue;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Author kangjiwie
 * @Date 2020/7/31
 */
public class MinioS3Util {

    @Value("${s3_url}")
    private String s3_url;

    @Value("${s3_access_key}")
    private String s3_accessKey;

    @Value("${s3_secret_key}")
    private String s3_secretKey;


    private MinioClient minioClient =
            MinioClient.builder()
                    .endpoint(s3_url)
                    .credentials(s3_accessKey, s3_secretKey)
                    .build();

}
