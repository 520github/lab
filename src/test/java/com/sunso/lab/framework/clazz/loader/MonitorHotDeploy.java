package com.sunso.lab.framework.clazz.loader;

import java.lang.reflect.Method;

/**
 * @Title:MonitorHotDeploy
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/25下午9:52
 * @m444@126.com
 */
public class MonitorHotDeploy implements Runnable {
    private String projectClassPath = "/Users/sunso520/code/IdeaProjects/base/lab/target/test-classes/";
    private String packagePath = "com/sunso/lab/framework/clazz/loader/";
    private String className = "com.sunso.lab.framework.clazz.loader.HotDeploy";
    private Class hotDeployClass;
    private HotDeployClassLoader hotDeployClassLoader;

    @Override
    public void run() {
        try{
            while (true) {
                System.out.println("monitor....");
                initLoad();
                Object hotDeployObject = hotDeployClass.newInstance();
                Method m = hotDeployClass.getMethod("hotMethod");
                m.invoke(hotDeployObject, null);
                Thread.sleep(10000);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initLoad() throws ClassNotFoundException {
        hotDeployClassLoader = HotDeployClassLoader.getHotDeployClassLoader(projectClassPath, packagePath);
        hotDeployClass = hotDeployClassLoader.loadClass(className);
    }
}
