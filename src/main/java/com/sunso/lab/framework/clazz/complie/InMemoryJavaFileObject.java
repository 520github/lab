package com.sunso.lab.framework.clazz.complie;

import javax.tools.SimpleJavaFileObject;
import java.io.IOException;
import java.net.URI;

/**
 * @Title:InMemoryJavaFileObject
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/25下午5:09
 * @m444@126.com
 */
public class InMemoryJavaFileObject extends SimpleJavaFileObject {

    private String javaSource;

    public InMemoryJavaFileObject(String className, String javaSource) {
        super(URI.create("string:///" + className.replace(".", "/") + Kind.SOURCE.extension), Kind.SOURCE);
        this.javaSource = javaSource;
    }

    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return javaSource;
    }

}
