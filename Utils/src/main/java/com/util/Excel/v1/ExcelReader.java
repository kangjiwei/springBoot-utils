package com.util.Excel.v1;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * @Author kangjiwei
 * @Date 2019/11/28
 * 知识点：
 *    apache poi 与  java Excel（jxl）的区别：
 *    现在的主流的两个Excel操作框架，apache poi 与 Java Execel(jxl)，两个的区别在于
 *    apache poi 提供api给java程式对Microsoft office（Excel，Word,PowerPoint,Visio等）格式档案读和写的功能。
 *    Java Excel 是一开源项目，通过他开发人员可以读取新的Excel文件内容，更新Excel文件内容，还有创建新的Excel内容。
 *    相比较而言，apache POI 的功能更为强大.
 */
public class ExcelReader {

    @Test
    public  void TestExcel() throws IOException {
        String excelPath = "C:\\Users\\Administrator\\Desktop\\excelTest.xlsx";
        iterator(excelPath);
    }

     /**
      * @Author kangjiwei
      * @Date 2019/11/28
      * 遍历Excel表的所有的数据
      */
     public void   iterator(String  excelPath) throws IOException {
         File excel = new File(excelPath);
         Workbook  wb = null;
         //创建样式
         CellStyle style = wb.createCellStyle();
         //自动换行
         style.setWrapText(true);
         //水平对齐方式（居中）
         style.setAlignment(HorizontalAlignment.CENTER);
         if(excel.isFile() && excel.exists()){

             String suffix = excel.getName().split("\\.")[1]; //特殊字符需要转换

             if(suffix.equals("xls"))
               wb = new HSSFWorkbook(new FileInputStream(excel));
              else if(suffix.equals("xlsx"))
               wb = new XSSFWorkbook(new FileInputStream(excel));

             //获取sheet的个数
             int numberOfSheets = wb.getNumberOfSheets();
             System.out.println("获取的所有的sheet的个数  "+ numberOfSheets);
             // No runnable methods 忘记加 @Test 注解
             for(int i =0 ;i < numberOfSheets;i++) {
                 Sheet sheetAt = wb.getSheetAt(i);
                 if (sheetAt.getFirstRowNum() != sheetAt.getLastRowNum()) //防止sheet为空抛出异常
                     ShowSheetCell(sheetAt);
             }
         }else{
             System.out.println(" 所述文件不存在！ ");
         }

     }
    /**
     * @Author kangjiwei
     * @Date 2019/11/28
     * 辅助方法： 遍历sheet
     * @param sheetAt
     */
     public void  ShowSheetCell(Sheet sheetAt){

         int firstRowNum = sheetAt.getFirstRowNum(); //开始行
         int lastRowNum = sheetAt.getLastRowNum();//结束行
         System.out.println(firstRowNum +"  "+lastRowNum);
         for(int rIndex = firstRowNum ; rIndex <= lastRowNum; rIndex++){
           ShowCellValues(sheetAt.getRow(rIndex));
         }

     }
    /**
     * @Author kangjiwei
     * @Date   2019/11/28
     * 辅助方法：遍历cell
     * @param row1
     */
    public  void ShowCellValues(Row row1){
            //遍历所有的cell
            Iterator<Cell> iterator = row1.iterator();
            String stringCellValue = null;
            double integerCellValue = 0;
            Object obj = null;
            while (iterator.hasNext()) {
                Cell next = iterator.next();
                CellType cellTypeEnum = next.getCellTypeEnum();
                switch (cellTypeEnum) {
                    case STRING:
                        obj = next.getStringCellValue();
                        break;
                    case NUMERIC:
                        obj = next.getNumericCellValue();
                        break;
                    case BOOLEAN:
                        obj = next.getBooleanCellValue();
                        break;
                    default:
                        break;
                }
                System.out.println(" 伤痛 " + obj);
            }
    }


}
