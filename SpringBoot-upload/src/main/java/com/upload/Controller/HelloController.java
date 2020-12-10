package com.upload.Controller;

import lombok.Cleanup;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.Random;

/**
 * Created by rongrong on 2019/12/17.
 */
@Controller
@RequestMapping("/hello")
public class HelloController {


    @RequestMapping("/nihao")
    public  String    nihao(){
        return "/index";
    }

    /**
     *   TXT 文件的读取
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping("/upload")
    @ResponseBody
    public void  upload(@RequestParam("file")MultipartFile  file) throws IOException {
        String  path = "D:\\160";
        if(file == null || file.isEmpty()){
            System.out.println("文件不存在！");
        }
        String fileName = file.getOriginalFilename();
        System.out.println(" 文件名称  "+fileName);
        @Cleanup InputStream inputStream = file.getInputStream();
        @Cleanup FileOutputStream out = new FileOutputStream(path+"\\"+fileName);
        out.write(file.getBytes());
        out.flush();
       /* byte[] buf = new byte[1024];
        int length = 0;
       while((length = inputStream.read(buf))!= -1){
           System.out.println(new String(buf,0,length));
       }*/
    }

    @RequestMapping("/ExcelUpload")
    @ResponseBody
    public void  readExcel(){

    }


     @RequestMapping("/downLoadFile")
     @ResponseBody
     public void downLoadFile(HttpServletRequest request, HttpServletResponse  response) throws IOException {
         String  fileName = "/templates/File/SystemController.java";

         String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
         System.out.println(" 护你的周全  "+path);
         //path = "C:\\Users\\rongrong\\Desktop\\BuilderExcel.java";

         ApplicationHome applicationHome = new ApplicationHome(getClass());
         String path1 = applicationHome.getSource().getParentFile().toString();
         System.out.println("与他相见"+path1);
         String replace = path.replace("target", "File");
         System.out.println(replace+"/snow.txt");
         File  file = new File(path+fileName);



          if(true){
              response.setContentType("application/force-download");
              response.addHeader("Content-Disposition","attachment;filename=test.txt");

         @Cleanup InputStream fileInputStream = new FileInputStream(file);
         @Cleanup BufferedInputStream  bufferedInputStream = new BufferedInputStream(fileInputStream);
         @Cleanup OutputStream outputStream = response.getOutputStream();

          byte[] b = new  byte[1024];

          int  len = bufferedInputStream.read(b);
              while(len != -1){
                  outputStream.write(b, 0 , len);
                  len = bufferedInputStream.read(b);
              }

          }

     }

    private String resourceDir;

    // 批量上传
    @PostMapping("/dc/moreFileUpload")
    @ResponseBody
    public String bacthFileUpload(MultipartFile[] file) {
        StringBuffer buffer = new StringBuffer();
        for (MultipartFile multipartFile : file) {
             fileUpload(multipartFile);
        }
        String all = buffer.substring(0, buffer.length() - 1);
        return all;
    }

    //单个文件上传
    @RequestMapping("/dc/fileUpload")
    @ResponseBody
    public String fileUpload(MultipartFile file){
        // 获取上传文件路径
        String uploadPath = file.getOriginalFilename();
        System.out.println("上传文件路径  "+uploadPath);
        return null;
    }
}
