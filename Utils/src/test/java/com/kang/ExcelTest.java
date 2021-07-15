package com.kang;

import com.util.Excel.v1.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: XiongDa
 * @Date: 2021/05/29 14:07
 */
@Slf4j
public class ExcelTest {

    public static void main(String[] args) {

        //实现写操作
        //1. 设置要写入的文件路径
/*        String filename = "D:\\excel\\write.xlsx";
        File file = new File(filename);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        //2. 准备数据
        List<ExcelData> data = new ArrayList<>();*/
        /*data.add(new ExcelData(201430317, "诺亚"));
        data.add(new ExcelData(201430329, "雷杰多"));
        data.add(new ExcelData(201430365, "撒加"));*/
        //3. 调用easyexcel的方法实现写操作。**写完后，文件流会自动关闭**
        //   参数1：文件名称  参数2：实体类
        //   sheet：excel文件底部栏的sheet
//        EasyExcel.write(filename, ExcelData.class).sheet("指标多数据源测试结果").doWrite(data);

        for (int i = 0; i <= 55; i++) {
            String code = " @ExcelProperty(\"征信2代样本数据_" + i + "\")\n" +
                    "private String xml" + i + ";\n";
            System.out.println(code);
        }

        for (int i = 1; i <= 55; i++) {
            System.out.println(" ExecutePythonUtil.execute(fileName,allDbSource.get(\"x" + i + "\")),");

        }
    }


    /**
     * 得到第三批指标indexCode
     */
    public List<String> readExcelFile() {
        String path = "D:\\工作文件\\征信\\thirdTime\\智能决策_人行征信指标_20210520.xlsx";
        Optional<List<Object>> clns = ExcelUtil.readExcelFile(path, 0);
        List<String> allClnValList = null;
        if (clns.isPresent()) {
            allClnValList = clns.get().stream().map(obj -> ((Map) obj).get(3) + "").collect(Collectors.toList());
            //log.info("第{}个Sheet 一共{}条数据", 1, clns.get());
        } else {
            allClnValList = new ArrayList<>();
        }

        Optional<List<Object>> clns1 = ExcelUtil.readExcelFile(path, 1);
        if (clns1.isPresent()) {
            allClnValList.addAll(clns1.get().stream().map(obj -> ((Map) obj).get(1) + "").collect(Collectors.toList()));
            //  log.info("第{}个Sheet 一共{}条数据", 2, clns1.get());
        } else {
            allClnValList = new ArrayList<>();
        }

        Optional<List<Object>> clns3 = ExcelUtil.readExcelFile(path, 2);
        if (clns3.isPresent()) {
            allClnValList.addAll(clns3.get().stream().map(obj -> ((Map) obj).get(4) + "").collect(Collectors.toList()));
            // log.info("第{}个Sheet 一共{}条数据", 3, clns3.get());
        } else {
            allClnValList = new ArrayList<>();
        }

        Optional<List<Object>> clns4 = ExcelUtil.readExcelFile(path, 3);
        if (clns4.isPresent()) {
            allClnValList.addAll(clns4.get().stream().map(obj -> ((Map) obj).get(1) + "").collect(Collectors.toList()));
            //log.info("第{}个Sheet 一共{}条数据", 4, clns4.get());
        }
        log.info("一共是{}条数据", allClnValList.size());
        return allClnValList;
    }


    @Test
    public void secondIndex() {
        String path = "D:\\工作文件\\征信\\secondTime\\衍生字段IT需求文档20210517_1.2.xlsx";
        Optional<List<Object>> clns = ExcelUtil.readExcelFile(path, 0);
        List<String> allClnValList = null;
        if (clns.isPresent()) {
            allClnValList = clns.get().stream().map(obj -> ((Map) obj).get(6) + "").collect(Collectors.toList());
            //log.info("第{}个Sheet 一共{}条数据", 1, clns.get());
        } else {
            allClnValList = new ArrayList<>();
        }

        Optional<List<Object>> clns1 = ExcelUtil.readExcelFile(path, 1);
        if (clns1.isPresent()) {
            allClnValList = clns1.get().stream().map(obj -> ((Map) obj).get(6) + "").collect(Collectors.toList());
            //log.info("第{}个Sheet 一共{}条数据", 1, clns.get());
        } else {
            allClnValList = new ArrayList<>();
        }
        for (String indexCode : allClnValList) {
            log.info(indexCode);
        }
        log.info("长度:{}", allClnValList.size());
    }


    /**
     * 759
     */
    @Test
    public void firstIndex() {
        String path = "D:\\工作文件\\征信\\firstTime\\智能决策服务部_人行征信指标需求_20210510_V1.2.xlsx";
        Optional<List<Object>> clns = ExcelUtil.readExcelFile(path, 0);
        List<String> allClnValList = null;
        if (clns.isPresent()) {
            allClnValList = clns.get().stream().map(obj -> ((Map) obj).get(4) + "").collect(Collectors.toList());
        }
        for (String indexCode : allClnValList) {
            log.info(indexCode);
        }
        log.info("长度:{}", allClnValList.size());
    }

    public List<String> getAllIndex(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        List<String> allIndexList = new ArrayList<>();
        for (File indexFile : files) {
            parseFile(indexFile, allIndexList);
        }
        return allIndexList.stream().map(filePath -> {
            int begin = filePath.lastIndexOf("\\");
            if (begin > -1) {
                return filePath.substring(begin + 1).replace(".py", "");
            }
            return filePath.replace(".py", "");
        }).collect(Collectors.toList());
    }

    public List<String> parseFile(File file, List<String> allUpdPath) {
        if (file.isFile()) {
            allUpdPath.add(file.getAbsolutePath());
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File pyFile : files) {
                this.parseFile(pyFile, allUpdPath);
            }
        }
        return allUpdPath;
    }


    @Test
    public void isExists() {
        List<String> excelFiles = this.readExcelFile();
        String path = "D:\\InitSoft\\Project\\pythonwarehouse\\SCI";
        List<String> allIndexCode = this.getAllIndex(path);
        List<String> existsIndex = excelFiles.stream().filter(excelFile -> allIndexCode.contains(excelFile)).collect(Collectors.toList());
        for (String indexCode : existsIndex) {
            System.out.println(indexCode);
        }
        log.info("{}个指标已经存在", existsIndex.size());
    }


    /**
     * 得到所有的模板信息
     */
    @Test
    public void getAllTemplate() {
        String path = "D:\\InitSoft\\Project\\pythonwarehouse\\SCI_temp";
        List<String> allIndexCode = this.getAllIndex(path);
        log.info("所有indexCode的个数:{}", allIndexCode.size());
    }

}
