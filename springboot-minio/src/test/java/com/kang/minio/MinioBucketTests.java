package com.kang.minio;

import com.kang.minio.utils.MinioBucket;
import org.junit.jupiter.api.Test;

public class MinioBucketTests {

     private MinioBucket minioBucket = new MinioBucket();


     @Test
     public void makeBucket(){
        MinioBucket.makeBucket("bu1","region1");
     }

     @Test
     public void listBucket(){
         MinioBucket.listBucket();
     }




}
