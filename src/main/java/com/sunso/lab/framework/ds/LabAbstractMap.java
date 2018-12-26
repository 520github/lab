package com.sunso.lab.framework.ds;

import java.util.Set;

/**
 * @Title:LabAbstractMap
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/10下午3:29
 * @m444@126.com
 */
public abstract class LabAbstractMap<K,V> implements LabMap<K,V>{
    protected LabAbstractMap() {

    }

    public int size() {
        return entrySet().size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public abstract Set<Entry<K, V>> entrySet();
}
