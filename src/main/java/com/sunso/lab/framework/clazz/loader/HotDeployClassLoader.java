package com.sunso.lab.framework.clazz.loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title:HotDeployClassLoader
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/25下午7:44
 * @m444@126.com
 */
public class HotDeployClassLoader extends URLClassLoader {
    private static Map<String,Long> fileLastModifyTimeCache = new HashMap<String, Long>();
    private String projectClassPath;
    private String packagePath;
    private static HotDeployClassLoader hotDeployClassLoader = null;

    private HotDeployClassLoader(String projectClassPath, String packagePath) {
        super(getURLs(projectClassPath));
        this.projectClassPath = projectClassPath;
        this.packagePath = packagePath;
    }

    public static HotDeployClassLoader getHotDeployClassLoader(String projectClassPath, String packagePath) {
        if(hotDeployClassLoader == null) {
            hotDeployClassLoader = new HotDeployClassLoader(projectClassPath, packagePath);
        }
        return hotDeployClassLoader;
    }

    private static URL[] getURLs(String filePath) {
        URL url = null;
        try {
            url = new File(filePath).toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return new URL[]{url};
    }

    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return loadClass(name, false);
    }

    @Override
    public Class<?> loadClass(String name,boolean resolve) throws ClassNotFoundException {
        Class clazz = findLoadedClass(name);
        if(clazz != null) {
            if(resolve) {
                resolveClass(clazz);
            }
            if(isModify(name)) {
                hotDeployClassLoader = new HotDeployClassLoader(projectClassPath, packagePath);
                clazz = loadClassByCustom(name,resolve, hotDeployClassLoader);
            }
            return clazz;
        }

        if(name.startsWith("java.")) {
            return loadClassBySystemClassLoader(name, resolve);
        }

        return loadClassByCustom(name, resolve, this);
    }

    private long getClassLastModifyTime(String name) {
        String path = getClassFullPath(name);
        File file = new File(path);
        if(!file.exists()) {
            throw new RuntimeException(new FileNotFoundException(name));
        }
        return file.lastModified();
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }

    private Class loadClassBySystemClassLoader(String name, boolean resolve) throws ClassNotFoundException {
        ClassLoader sytemLoader = ClassLoader.getSystemClassLoader();
        Class clazz = sytemLoader.loadClass(name);
        if(clazz != null) {
            if(resolve) {
                resolveClass(clazz);
            }
        }
        return clazz;
    }

    private Class loadClassByCustom(String name, boolean resolve, ClassLoader cl) throws ClassNotFoundException {
        HotDeployClassLoader hotDeployClassLoader = getHotDeployClassLoader(cl);
        Class clazz = hotDeployClassLoader.findClass(name);
        if(resolve) {
            hotDeployClassLoader.resolveClass(clazz);
        }
        long lastModifyTime = getClassLastModifyTime(name);
        fileLastModifyTimeCache.put(name, lastModifyTime);
        return clazz;
    }

    private HotDeployClassLoader getHotDeployClassLoader(ClassLoader cl) {
        return (HotDeployClassLoader)cl;
    }

    private boolean isModify(String name) {
        long lastModifyTime = getClassLastModifyTime(name);
        long preModifyTime = fileLastModifyTimeCache.get(name);
        if(lastModifyTime > preModifyTime) {
            return true;
        }
        return false;
    }

    private String getClassFullPath(String name) {
        String simpleName = name.substring(name.lastIndexOf(".")+1);
        String classFullPath =  projectClassPath + packagePath + simpleName + ".class";
        System.out.println("classFullPath--> " + classFullPath);
        return classFullPath;
    }

}
