package com.kang.mybatis.util.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kang.mybatis.entity.CarInfoModel;
import com.kang.mybatis.mapper.CarInfoMapper;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: XiongDa
 * @Date: 2021/07/14 20:13
 */
@Slf4j
public class CarInfoListener extends AnalysisEventListener<CarInfoModel> {


    private final Integer BATCH_COUNT = 10;

    List<CarInfoModel> carInfoList = new ArrayList<>();

    private CarInfoMapper carInfoMapper;

    public void setMapper(CarInfoMapper carInfoMapper) {
        this.carInfoMapper = carInfoMapper;
    }

    @Override
    public void invoke(CarInfoModel data, AnalysisContext context) {
        carInfoList.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (carInfoList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            carInfoList.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("开始添加数据......");
        for (CarInfoModel carInfoModel : carInfoList) {
            carInfoModel.setId(0);
            carInfoModel.setCreate_time(LocalDateTime.now());
            carInfoModel.setUpdate_time(LocalDateTime.now());

            // 如果车型ID，车系ID，品牌ID如果 ，则同步创建时间
            QueryWrapper<CarInfoModel> wrapper = new QueryWrapper();
            wrapper.eq("brand_id", carInfoModel.getBrand_id());
            wrapper.eq("car_series_id", carInfoModel.getCar_series_id());
            wrapper.eq("car_type_id", carInfoModel.getCar_type_id());
            List<CarInfoModel> carInfoModels = carInfoMapper.selectList(wrapper);
            if (carInfoModels.size() > 0) {
                carInfoModel.setCreate_time(carInfoModels.get(0).getCreate_time()==null ? LocalDateTime.now() : carInfoModels.get(0).getCreate_time());
            }
            carInfoMapper.insert(carInfoModel);
        }
        log.info("添加数据结束。");
    }

}

