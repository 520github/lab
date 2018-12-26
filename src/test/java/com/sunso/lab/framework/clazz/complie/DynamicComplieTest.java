package com.sunso.lab.framework.clazz.complie;

import com.sunso.lab.framework.clazz.BaseClazzTest;
import org.junit.jupiter.api.Test;

/**
 * @Title:DynamicComplieTest
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/25下午6:14
 * @m444@126.com
 */
public class DynamicComplieTest extends BaseClazzTest {


    @Test
    public void complieTest() {
        String className = "Hello";
        String javaSource = "public class Hello {" +
                "   public static void main(String[] args){" +
                "        System.out.println(\"hello world....\");" +
                "  }" +
                "}";
        String classPath = "/Users/sunso520/work/temp/db";

        boolean result = DynamicComplie.complie(className, javaSource, classPath);
        this.print("complie result is " + result);
    }
}
