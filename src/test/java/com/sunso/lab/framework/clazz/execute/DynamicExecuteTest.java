package com.sunso.lab.framework.clazz.execute;

import com.sunso.lab.framework.clazz.BaseClazzTest;
import com.sunso.lab.framework.clazz.LabClassLoader;
import com.sunso.lab.framework.clazz.complie.DynamicComplie;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * @Title:DynamicExecuteTest
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/25下午6:57
 * @m444@126.com
 */
public class DynamicExecuteTest extends BaseClazzTest {

    @Test
    public void executeMainMethodTest() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        String[] args = {"12345678", "990"};
        DynamicExecute.executeMainMethod(getClazz(), args);
    }

    private Class getClazz() throws ClassNotFoundException {
        String className = "Hello";
        String javaSource = "public class Hello {" +
                "   public static void main(String[] args){" +
                "        System.out.println(args[0]);" +
                "        System.out.println(\"hello world....\");" +
                "  }" +
                "}";
        String classPath = "/Users/sunso520/work/temp/db/";

        boolean result = DynamicComplie.complie(className, javaSource, classPath);
        this.print("complie result is " + result);

        LabClassLoader labClassLoader = new LabClassLoader(getClass().getClassLoader(), classPath);
        return labClassLoader.loadClass(className);
    }
}
