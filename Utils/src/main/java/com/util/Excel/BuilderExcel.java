package com.util.Excel;

import org.apache.poi.sl.draw.binding.ObjectFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author kangjiwei
 * @Date 2019/11/29
 * Created by Administrator on 2019/11/29.
 */
public class BuilderExcel {

    public void   operation(){
        String[] titles = {"姓名","性别","年龄"};
        List<Map<String,Object>> list = new ArrayList<>();
        list.add(getMap("刘德华","男","18"));
        list.add(getMap("张学友","男","20"));
        list.add(getMap("郭富城","",""));
        String[] colNames = {"name","sex","age"};
        createXlsx(titles,list,colNames,"C:\\Users\\rongrong\\Desktop\\rongrong.xlsx");
    }

    /**
     * @Author kangjiwei
     * @Date  2019/11/29
     * 作用: excel 表的创建
     * @param titles   标题 数组
     * @param list     数据集合  集合
     * @param colNames   对应字段column
     * @param filePath  创建文件路径
     */
     public  void   createXlsx(String[] titles,List<Map<String,Object>> list,String[]  colNames,String  filePath){
         XSSFWorkbook  workbook  = new XSSFWorkbook();
         XSSFSheet sheet1 = workbook.createSheet("sheet1");
         XSSFRow row = sheet1.createRow(0);
         Object  obj = null;
         //标题使用
         for(int i = 0; i< titles.length;i++){
             obj = titles[i];
             if(obj == null) obj = "";
              row.createCell(i).setCellValue(obj+"");
         }
         //创建数据sheet
         for(int dataI =0; dataI< list.size();dataI++){
             row = sheet1.createRow(dataI+1);
             Map<String, Object> map  = list.get(dataI);
             for(int i =0;i< colNames.length;i++){
                 row.createCell(i).setCellValue(map.get(colNames[i])+"");
             }
         }
         //6.输出excel文件
         FileOutputStream fileOut = null;
         try {
             fileOut = new FileOutputStream(filePath);
             workbook.write(fileOut);
             System.out.println("文件创建完毕！");
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }

     }
     //辅助方法，得到map集合
    public Map getMap(String name,String  sex,String  age){
         Map   map = new HashMap<String,ObjectFactory>();
         map.put("name",name);
         map.put("sex",sex);
         map.put("age",age);
        return  map;
    }

}
