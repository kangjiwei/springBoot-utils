package com.kang.ignite.utils;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyCodeSource;
import groovy.lang.GroovyObject;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.logging.Logger;

public class GroovyUtils {


    /**
     * 加载Groovy文件，返回GroovyObject对象
     * @param file
     * @return
     */
    static GroovyObject getGroovyObject(File file) {
        GroovyObject groovyObject = null;
        try {
            GroovyClassLoader classLoader = new GroovyClassLoader(Thread.currentThread().getContextClassLoader());
            Class clazz = classLoader.parseClass(new GroovyCodeSource(file));
            groovyObject = (GroovyObject)clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groovyObject;
    }

    public static GroovyObject getGroovyObject(String filePath) {
        return getGroovyObject(new File(filePath));
    }

    /**
     * 执行GroovyObject对象的方法
     * @param name
     * @param args
     * @return
     */
    static Object invokeMethod(GroovyObject groovyObject, String name, Object args) {
        return groovyObject.invokeMethod(name, args);
    }

}
