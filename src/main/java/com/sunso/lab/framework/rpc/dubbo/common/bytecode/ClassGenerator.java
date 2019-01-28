package com.sunso.lab.framework.rpc.dubbo.common.bytecode;

import com.sunso.lab.framework.rpc.dubbo.common.util.ClassHelper;
import com.sunso.lab.framework.rpc.dubbo.common.util.ReflectUtils;
import javassist.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.ProtectionDomain;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Title:ClassGenerator
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/27下午10:32
 * @m444@126.com
 */
public final class ClassGenerator {
    private static final String SIMPLE_NAME_TAG = "<init>";
    private static final AtomicLong CLASS_NAME_COUNTER = new AtomicLong(0);
    private static final Map<ClassLoader, ClassPool> POOL_MAP = new ConcurrentHashMap<ClassLoader, ClassPool>();
    private List<String> mFields;
    private Set<String> mInterfaces;
    private List<String> mMethods;
    private List<String> mConstructors;
    private boolean mDefaultConstructor = false;
    private String mClassName;
    private String mSuperClass;
    private CtClass mCtc;
    private ClassPool mPool;
    private Map<String, Method> mCopyMethods; // <method desc,method instance>
    private Map<String, Constructor<?>> mCopyConstructors;

    private ClassGenerator(ClassPool pool) {
        this.mPool = pool;
    }

    public static ClassGenerator newInstance(ClassLoader classLoader) {
        return new ClassGenerator(getClassPool(classLoader));
    }

    public static boolean isDynamicClass(Class<?> cl) {
        return ClassGenerator.DC.class.isAssignableFrom(cl);
    }

    public static ClassPool getClassPool(ClassLoader classLoader) {
        if(classLoader == null) {
            return ClassPool.getDefault();
        }

        ClassPool pool = POOL_MAP.get(classLoader);
        if(pool == null) {
            pool = new ClassPool(true);
            pool.appendClassPath(new LoaderClassPath(classLoader));
            POOL_MAP.put(classLoader, pool);
        }
        return pool;
    }

    public ClassGenerator setClassName(String name) {
        mClassName = name;
        return this;
    }

    public ClassGenerator setSuperClass(String cn) {
        mSuperClass = cn;
        return this;
    }

    public ClassGenerator setSuperClass(Class<?> cl) {
        mSuperClass = cl.getName();
        return this;
    }

    public ClassGenerator addInterface(String cn) {
        if(mInterfaces == null) {
            mInterfaces = new HashSet<>();
        }
        mInterfaces.add(cn);
        return this;
    }

    public ClassGenerator addInterface(Class<?> cl) {
        return addInterface(cl.getName());
    }

    public ClassGenerator addField(String code) {
        if(mFields == null) {
            mFields = new ArrayList<>();
        }
        mFields.add(code);
        return this;
    }

    public ClassGenerator addMethod(String code) {
        if(mMethods == null) {
            mMethods = new ArrayList<>();
        }
        mMethods.add(code);
        return this;
    }

    public ClassGenerator addMethod(String name, int mod, Class<?> rt, Class<?>[] pts, Class<?>[] ets,
                                    String body) {
        StringBuilder sb = new StringBuilder();
        sb.append(modifier(mod)).append(' ').append(ReflectUtils.getName(rt)).append(' ').append(name);
        sb.append('(');
        for (int i = 0; i < pts.length; i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(ReflectUtils.getName(pts[i]));
            sb.append(" arg").append(i);
        }
        sb.append(')');
        if (ets != null && ets.length > 0) {
            sb.append(" throws ");
            for (int i = 0; i < ets.length; i++) {
                if (i > 0) {
                    sb.append(',');
                }
                sb.append(ReflectUtils.getName(ets[i]));
            }
        }
        sb.append('{').append(body).append('}');
        return addMethod(sb.toString());
    }

    public ClassGenerator addConstructor(String code) {
        if (mConstructors == null) {
            mConstructors = new LinkedList<String>();
        }
        mConstructors.add(code);
        return this;
    }

    public ClassGenerator addConstructor(int mod, Class<?>[] pts, Class<?>[] ets, String body) {
        StringBuilder sb = new StringBuilder();
        sb.append(modifier(mod)).append(' ').append(SIMPLE_NAME_TAG);
        sb.append('(');
        for (int i = 0; i < pts.length; i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(ReflectUtils.getName(pts[i]));
            sb.append(" arg").append(i);
        }
        sb.append(')');
        if (ets != null && ets.length > 0) {
            sb.append(" throws ");
            for (int i = 0; i < ets.length; i++) {
                if (i > 0) {
                    sb.append(',');
                }
                sb.append(ReflectUtils.getName(ets[i]));
            }
        }
        sb.append('{').append(body).append('}');
        return addConstructor(sb.toString());
    }

    public ClassGenerator addDefaultConstructor() {
        mDefaultConstructor = true;
        return this;
    }

    public Class<?> toClass() {
        return toClass(ClassHelper.getClassLoader(ClassGenerator.class),
                getClass().getProtectionDomain());
    }

    public Class<?> toClass(ClassLoader loader, ProtectionDomain pd) {
        if(mCtc != null) {
            mCtc.detach();
        }
        long id = CLASS_NAME_COUNTER.getAndIncrement();
        try {
            CtClass ctcs = mSuperClass == null? null: mPool.get(mSuperClass);
            if(mClassName == null) {
                mClassName = (mSuperClass == null || javassist.Modifier.isPublic(ctcs.getModifiers())? ClassGenerator.class.getName(): mSuperClass + "$sc") + id;
            }
            mCtc = mPool.makeClass(mClassName);
            if(mSuperClass != null) {
                mCtc.setSuperclass(ctcs);
            }
            mCtc.addInterface(mPool.get(DC.class.getName()));
            if(mInterfaces != null) {
                for(String cl: mInterfaces) {
                    mCtc.addInterface(mPool.get(cl));
                }
            }
            if(mFields != null) {
                for(String filedCode: mFields) {
                    mCtc.addField(CtField.make(filedCode, mCtc));
                }
            }
            if(mMethods != null) {
                for(String methodCode: mMethods) {
                    if(methodCode.charAt(0) == ':') {
                        mCtc.addMethod(CtNewMethod.copy(getCtMethod(
                                mCopyMethods.get(methodCode.substring(1))),
                                methodCode.substring(1, methodCode.indexOf("(")),
                                mCtc, null));
                    }
                    else {
                        mCtc.addMethod(CtNewMethod.make(methodCode, mCtc));
                    }
                }
            }

            if(mDefaultConstructor) {
                mCtc.addConstructor(CtNewConstructor.defaultConstructor(mCtc));
            }

            if(mConstructors != null) {
                for(String code: mConstructors) {
                    if(code.charAt(0) == ':') {
                        mCtc.addConstructor(
                                CtNewConstructor.copy(
                                        getCtConstructor(mCopyConstructors.get(code.substring(1))),
                                        mCtc, null)
                        );
                    }
                    else {
                        String[] sn = mCtc.getSimpleName().split("\\$+"); // inner class name include $.
                        mCtc.addConstructor(CtNewConstructor.make(code.replaceFirst(SIMPLE_NAME_TAG, sn[sn.length-1]), mCtc));
                    }
                }
            }

            return mCtc.toClass(loader, pd);
        }
        catch (RuntimeException e) {
            throw e;
        } catch (NotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (CannotCompileException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void release() {
        if (mCtc != null) {
            mCtc.detach();
        }
        if (mInterfaces != null) {
            mInterfaces.clear();
        }
        if (mFields != null) {
            mFields.clear();
        }
        if (mMethods != null) {
            mMethods.clear();
        }
        if (mConstructors != null) {
            mConstructors.clear();
        }
        if (mCopyMethods != null) {
            mCopyMethods.clear();
        }
        if (mCopyConstructors != null) {
            mCopyConstructors.clear();
        }
    }

    private static String modifier(int mod) {
        StringBuilder modifier = new StringBuilder();
        if (Modifier.isPublic(mod)) {
            modifier.append("public");
        }
        if (Modifier.isProtected(mod)) {
            modifier.append("protected");
        }
        if (Modifier.isPrivate(mod)) {
            modifier.append("private");
        }

        if (Modifier.isStatic(mod)) {
            modifier.append(" static");
        }
        if (Modifier.isVolatile(mod)) {
            modifier.append(" volatile");
        }

        return modifier.toString();
    }

    private CtMethod getCtMethod(Method m) throws NotFoundException {
        return getCtClass(m.getDeclaringClass())
                .getMethod(m.getName(), ReflectUtils.getDescWithoutMethodName(m));
    }

    private CtClass getCtClass(Class<?> c) throws NotFoundException {
        return mPool.get(c.getName());
    }

    private CtConstructor getCtConstructor(Constructor<?> c) throws NotFoundException {
        return getCtClass(c.getDeclaringClass()).getConstructor(ReflectUtils.getDesc(c));
    }

    public static interface DC {

    }
}
