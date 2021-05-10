package com.kang.ignite.groovy;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyCodeSource;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import org.codehaus.groovy.control.CompilationFailedException;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class GroovyUtil {
    /**
     * 加载Groovy文件，返回GroovyObject对象
     *
     * @param file
     * @return
     */
    public static GroovyObject getGroovyObject(File file) {
        GroovyObject groovyObject = null;
        try {
            GroovyClassLoader classLoader = new GroovyClassLoader(Thread.currentThread().getContextClassLoader());
            Class clazz = classLoader.parseClass(new GroovyCodeSource(file));
            groovyObject = (GroovyObject) clazz.newInstance();
        } catch (Exception e) {
        }
        return groovyObject;
    }

    public static Optional<GroovyObject> getGroovyObject(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("路劲不对!");
            return Optional.empty();
        }
        return Optional.of(getGroovyObject(file));
    }

    /**
     * 执行GroovyObject对象的方法
     *
     * @param name
     * @param args
     * @return
     */
    public static Object invokeMethod(GroovyObject groovyObject, String name, Object args) {
        return groovyObject.invokeMethod(name, args);
    }

    /**
     * Shell执行Groovy。
     * @throws CompilationFailedException
     * @throws IOException
     */
    public void testGroovy1() throws CompilationFailedException, IOException {
        GroovyShell groovyShell = new GroovyShell();
        groovyShell.evaluate("println 'My First Groovy shell.'");
    }

}
