package com.kang.mybatis.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: XiongDa
 * @Date: 2021/07/14 16:24
 */
@Data
@TableName("car_info")
public class CarInfoModel {

    @TableId
    private Integer id;
    // 首字母
    @ExcelProperty("首字母")
    private String first_word;
    // 状态
    @ExcelProperty("状态")
    private String car_status;
    // 品牌ID
    @ExcelProperty("品牌ID")
    private String brand_id;
    // 品牌名称
    @ExcelProperty("品牌名称")
    private String brand_name;
    // 厂商ID
    @ExcelProperty("厂商ID")
    private String manu_id;
    // 厂商名称
    @ExcelProperty("厂商名称")
    private String manu_name;
    // 车系ID
    @ExcelProperty("车系ID")
    private String car_series_id;
    // 车系名称
    @ExcelProperty("车系名称")
    private String car_series_name;
    // 车型ID
    @ExcelProperty("车型ID")
    private String car_type_id;
    // 车型名称
    @ExcelProperty("车型名称")
    private String car_type_name;
    // 年款
    @ExcelProperty("年份")
    private String year_style;
    // 品牌logo
    @ExcelProperty("品牌logo")
    private String logo_url;
    // 厂商指导价(元)
    @ExcelProperty("厂商指导价(元)")
    private String init_price;
    // 厂商
    @ExcelProperty("厂商")
    private String manu;
    // 级别
    @ExcelProperty("级别")
    private String car_class;
    // 能源类型
    @ExcelProperty("能源类型")
    private String energy_type;
    // 环保标准
    @ExcelProperty("环保标准")
    private String env_standards;
    // 变速箱
    @ExcelProperty("变速箱")
    private String trans_case;
    // 排量(L)
    @ExcelProperty("排量(L)")
    private String car_displacement;
    // 燃料形式
    @ExcelProperty("燃料形式")
    private String fuel_form;
    // 变速箱类型
    @ExcelProperty("变速箱类型")
    private String trans_case_type;
    // 简称
    @ExcelProperty("简称")
    private String small_name;
    // 驱动方式
    @ExcelProperty("驱动方式")
    private String driving_form;
    // 更新时间
    @ExcelProperty("更新时间")
    private LocalDateTime update_time;
    // 创建时间
    @ExcelProperty("创建时间")
    private LocalDateTime create_time;
    //厂商最低指导价格
    @ExcelProperty("厂商最低指导价(万元)")
    private String manu_min_price;

}
