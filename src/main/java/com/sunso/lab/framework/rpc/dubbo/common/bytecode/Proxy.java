package com.sunso.lab.framework.rpc.dubbo.common.bytecode;

import com.sunso.lab.framework.rpc.dubbo.common.util.ClassHelper;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Title:Proxy
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/27下午10:39
 * @m444@126.com
 */
public abstract class Proxy {

    private static final AtomicLong PROXY_CLASS_COUNTER = new AtomicLong(0);
    private static final String PACKAGE_NAME = Proxy.class.getPackage().getName();
    /**
     * a cache of proxy classes
     */
    private static final Map<ClassLoader, Map<String, Object>> ProxyCacheMap = new WeakHashMap<ClassLoader, Map<String, Object>>();


    public static Proxy getProxy(Class<?>... ics) {
        return getProxy(ClassHelper.getClassLoader(Proxy.class), ics);
    }

    public static Proxy getProxy(ClassLoader cl, Class<?>... ics) {
        Proxy proxy = null;
        if(ics.length > 65535) {
            throw new IllegalArgumentException("interface limit exceeded");
        }
        String key = getInterfacesKey(cl, ics);

        long id = PROXY_CLASS_COUNTER.getAndIncrement();
        String pkg = null;
        if(pkg == null) {
            pkg = PACKAGE_NAME;
        }
        ClassGenerator ccp = null, ccm = null;
        try{
            ccp = ClassGenerator.newInstance(cl);
            List<Method> methods = new ArrayList<Method>();
            handleInterfaces(methods, ccp, ics);
            String pcn = pkg + ".proxy" + id;
            ccp.setClassName(pcn);
            ccp.addField("public static java.lang.reflect.Method[] methods;");
            ccp.addField("private " + InvocationHandler.class.getName() + " handler;");
            ccp.addConstructor(Modifier.PUBLIC, new Class<?>[]{InvocationHandler.class}, new Class<?>[0], "handler=$1;");
            ccp.addDefaultConstructor();
            Class<?> clazz = ccp.toClass();
            clazz.getField("methods").set(null, methods.toArray(new Method[0]));

            String fcn = Proxy.class.getName() + id;
            ccm = ClassGenerator.newInstance(cl);
            ccm.setClassName(fcn);
            ccm.addDefaultConstructor();
            ccm.setSuperClass(Proxy.class);
            ccm.addMethod("public Object newInstance(" + InvocationHandler.class.getName() + " h){ return new " + pcn + "($1); }");
            Class<?> pc = ccm.toClass();
            proxy = (Proxy) pc.newInstance();

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if(ccp != null) {
                ccp.release();
            }
            if(ccm != null) {
                ccm.release();
            }
        }
        return proxy;
    }

    private static String getInterfacesKey(ClassLoader cl, Class<?>... ics) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<ics.length; i++) {
            String itf = ics[i].getName();
            if(!ics[i].isInterface()) {
                throw new RuntimeException(itf + " is not a interface.");
            }

            Class<?> tmp = null;
            try {
                tmp = Class.forName(itf, false, cl);
            } catch (ClassNotFoundException e) {
            }

            if(tmp != ics[i]) {
                throw new IllegalArgumentException(ics[i] + " is not visible from class loader");
            }
            sb.append(itf).append(';');
        }
        return  sb.toString();
    }

    private static void handleInterfaces(List<Method> methods, ClassGenerator classGenerator, Class<?>... ics) {
        for(int i=0; i<ics.length; i++) {
            handleOneInterface(methods, classGenerator, ics[i]);
        }
    }

    private static void handleOneInterface(List<Method> methods, ClassGenerator classGenerator, Class<?> itf) {
        classGenerator.addInterface(itf);
        handleInterfaceMethods(methods, classGenerator, itf);
    }

    private static void handleInterfaceMethods(List<Method> methods, ClassGenerator classGenerator, Class<?> itf) {
        int index = 0;
        for(Method method: itf.getMethods()) {
            Class<?> rt = method.getReturnType();
            Class<?>[] pts = method.getParameterTypes();
            methods.add(method);
            String code = getOneMethodCode(method, index);
            classGenerator.addMethod(method.getName(), method.getModifiers(), rt, pts, method.getExceptionTypes(), code);
            index++;
        }
    }

    private static String getOneMethodCode(Method method, int index) {
        Class<?>[] pts = method.getParameterTypes();
        Class<?> rt = method.getReturnType();
        StringBuilder code = new StringBuilder("Object[] args = new Object[").append(pts.length).append("];");
        for(int i=0; i<pts.length; i++) {
            code.append(" args[").append(i).append("] = ($w)$").append(i + 1).append(";");
        }
        code.append(" Object ret = handler.invoke(this, methods[" + index + "], args);");
        if(!Void.TYPE.equals(rt)) {
            code.append(" return ").append(asArgument(rt, "ret")).append(";");
        }
        return code.toString();
    }

    private static String asArgument(Class<?> cl, String name) {
        if (cl.isPrimitive()) {
            if (Boolean.TYPE == cl) {
                return name + "==null?false:((Boolean)" + name + ").booleanValue()";
            }
            if (Byte.TYPE == cl) {
                return name + "==null?(byte)0:((Byte)" + name + ").byteValue()";
            }
            if (Character.TYPE == cl) {
                return name + "==null?(char)0:((Character)" + name + ").charValue()";
            }
            if (Double.TYPE == cl) {
                return name + "==null?(double)0:((Double)" + name + ").doubleValue()";
            }
            if (Float.TYPE == cl) {
                return name + "==null?(float)0:((Float)" + name + ").floatValue()";
            }
            if (Integer.TYPE == cl) {
                return name + "==null?(int)0:((Integer)" + name + ").intValue()";
            }
            if (Long.TYPE == cl) {
                return name + "==null?(long)0:((Long)" + name + ").longValue()";
            }
            if (Short.TYPE == cl) {
                return name + "==null?(short)0:((Short)" + name + ").shortValue()";
            }
            throw new RuntimeException(name + " is unknown primitive type.");
        }
        //return "(" + ReflectUtils.getName(cl) + ")" + name;
        return null;
    }

    abstract public Object newInstance(InvocationHandler handler);

}
