package com.sunso.lab.framework.clazz.complie;

import javax.tools.*;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * @Title:DynamicComplie
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/25下午4:58
 * @m444@126.com
 */
public class DynamicComplie {

    public static boolean complie(String className, String sourceJava, String classPath) {
        //通过 ToolProvider 取得 JavaCompiler 对象，JavaCompiler 对象是动态编译工具的主要对象
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();

        // 通过 JavaCompiler 取得标准 StandardJavaFileManager 对象，StandardJavaFileManager 对象主要负责
        // 编译文件对象的创建，编译的参数等等，我们只对它做些基本设置比如编译 CLASSPATH 等。
        StandardJavaFileManager standardFileManager = javaCompiler.getStandardFileManager(null, null, null);

        //编译目的地设置
        Iterable options = Arrays.asList("-d", classPath);

        //获取java源文件
        JavaFileObject fileObject = new InMemoryJavaFileObject(className, sourceJava);
        Iterable<? extends JavaFileObject> files = Arrays.asList(fileObject);

        //编译结果信息的记录
        StringWriter sw = new StringWriter();

        //通过 JavaCompiler 对象取得编译 Task
        JavaCompiler.CompilationTask task = javaCompiler.getTask(sw, standardFileManager, null, options, null , files);

        //调用 call 命令执行编译
        if(task.call()) {
            return true;
        }
        System.out.println("build error:" + sw.toString());
        return false;
    }
}
