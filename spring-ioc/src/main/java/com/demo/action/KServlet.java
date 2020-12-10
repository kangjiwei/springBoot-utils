package com.demo.action;

import com.demo.annoation.KController;
import com.demo.annoation.KService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

public class KServlet extends HttpServlet {

    private String scanUrl;

    private List<String> allCls = new ArrayList<>();

    private Map<String,Object> instanceMap = new HashMap<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }


    @Override
    public void init() throws ServletException {
        System.out.println("加载Spring容器......");

        //加载配置文件
        try {
            doLoadConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //扫描类
        doScanner();

        //初始化，将类放入IOC容器
        try {
            doInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //自动注入依赖
        doAutoWrite();

        //请求映射
        doHandlerMapping();
    }

    private void doHandlerMapping() {

    }

    private void doAutoWrite() {

    }

    private void doInstance() throws ClassNotFoundException {
        for (String filePath : allCls) {
            Class<?> aClass = Class.forName(filePath);
            Annotation[] annotations = aClass.getAnnotations();
            for (Annotation ano : annotations) {
                boolean controllerBo = ano.equals(KController.class);
                if (controllerBo) {
                    System.out.println("is Controller.");
                    instanceMap.put(aClass.getSimpleName(),filePath);
                } else if (ano.equals(KService.class)) {
                    instanceMap.put(aClass.getSimpleName(),filePath);
                    System.out.println("is Service.");
                }else{
                    continue;
                }
            }
        }
    }

    /**
     * 扫描文件
     */
    private void doScanner() {
        File files = new File(scanUrl);
        loopFile(files);
    }

    private void doLoadConfig() throws IOException {
        String application = "application.properties";
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(application);
        Properties properties = new Properties();
        properties.load(resourceAsStream);
        Object scanPackage = properties.get("scanPackage");
        String scanUrl = String.valueOf(scanPackage);
        System.out.println("扫描文件路径: " + scanUrl);
        this.scanUrl = scanUrl;
    }

    /**
     * 迭代查询文件
     */
    public void loopFile(File resource) {
        File[] fileSon = resource.listFiles();
        for (File file : fileSon) {
            if (file.isDirectory()) {
                loopFile(file);
            } else {
                System.out.println("文件名称: " + file.getName() + " 文件路径: " + file.getPath());
                allCls.add(file.getPath());
            }
        }
    }


    public static void main(String[] args) {

        File file = new File("C:\\Kang\\学习文件\\");
        File[] files = file.listFiles();
        for (File fle : files) {
            System.out.println(fle.getName());
        }

    }

}
