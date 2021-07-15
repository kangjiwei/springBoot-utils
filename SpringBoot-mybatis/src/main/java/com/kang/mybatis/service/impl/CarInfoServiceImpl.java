package com.kang.mybatis.service.impl;

import com.alibaba.excel.EasyExcel;
import com.kang.minio.utils.MinioUtil;
import com.kang.mybatis.entity.CarInfoModel;
import com.kang.mybatis.mapper.CarInfoMapper;
import com.kang.mybatis.service.ICarInfoService;
import com.kang.mybatis.util.excel.CarInfoListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: XiongDa
 * @Date: 2021/07/14 20:21
 */
@Service
@Slf4j
public class CarInfoServiceImpl implements ICarInfoService {

    @Autowired
    private CarInfoMapper carInfoMapper;

    @Value("${excel.bash_url}")
    private String excelFileUrl;

    @Override
    public boolean readExcel() {
        CarInfoListener carInfoListener = new CarInfoListener();
        carInfoListener.setMapper(carInfoMapper);
        EasyExcel.read(excelFileUrl, CarInfoModel.class, carInfoListener).sheet().doRead();
        return true;
    }

    @Override
    public void uploadImg() {
        MinioUtil minioUtil = new MinioUtil("http://192.168.0.77:9000/", "antnest", "antnest123", "antnest");
        File images = new File("D:\\excel\\img\\logo");
        if (images.isDirectory()) {
            File[] fileList = images.listFiles();
            for (File file : fileList) {
                try {
                    log.info("username:{},url: {}", file.getName(), file.getAbsolutePath());
                    minioUtil.uploadFile(file.getName(), file.getAbsolutePath());
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
