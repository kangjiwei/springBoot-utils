package com.util;

import net.sf.json.processors.DefaultValueProcessor;

/**
 * @Author: XiongDa
 * @Date: 2021/05/27 13:55
 */
public class IntToStrProcessor implements DefaultValueProcessor {
    @Override
    public Object getDefaultValue(Class aClass) {
        System.out.println("================");
        return null;
    }
}
