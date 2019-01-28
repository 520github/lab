package com.sunso.lab.framework.proxy.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @Title:JavassistDemoMain
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/27下午7:25
 * @m444@126.com
 */
public class JavassistDemoMain {

    public static void main(String[] args) {
        modifyMethod();
    }

    private static void modifyMethod() {
        ClassPool pool = ClassPool.getDefault();
        try {
            CtClass ct = pool.getCtClass("com.sunso.lab.framework.proxy.javassist.Point");
            CtMethod m= ct.getDeclaredMethod("move");
            m.insertBefore("{ System.out.print(\"dx:\"+$1); System.out.println(\"dy:\"+$2);}");
            m.insertAfter("{System.out.println(this.x); System.out.println(this.y);}");

            Class pc= ct.toClass();
            Method move= pc.getMethod("move", new Class[]{int.class, int.class});
            Constructor<?> con=pc.getConstructor(new Class[]{int.class,int.class});
            move.invoke(con.newInstance(1,2), 2, 3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
