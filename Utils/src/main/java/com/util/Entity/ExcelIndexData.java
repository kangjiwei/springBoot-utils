package com.util.Entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: XiongDa
 * @Date: 2021/06/01 14:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelIndexData {

    @ExcelProperty("序列号")
    private String id;

    @ExcelProperty("英文名称")
    private String indexCode;

    @ExcelProperty("中文名称")
    private String indexName;

    @ExcelProperty("字段类型")
    private String clnType;

    @ExcelProperty("模块")
    private String module;

    @ExcelProperty("取数逻辑")
    private String getNum;

    @ExcelProperty("加工逻辑")
    private String parse;

    @ExcelProperty("负责人")
    private String user;
}
