package com.sunso.lab.framework.ds;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;

/**
 * @Title:LabMap
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/9上午9:47
 * @m444@126.com
 */
public interface LabMap<K,V> {
    int size();

    boolean isEmpty();

    boolean containsKey(Object key);

    boolean containsValue(Object value);

    V get(Object key);

    V put(K key, V value);

    V remove(Object key);

    void putAll(LabMap<? extends K, ? extends  V> m);

    void clear();

    LabSet<K> keySet();

    Collection<V> values();

    interface Entry<K,V> {
        K getKey();

        V getValue();

        V setValue(V value);

        boolean equals(Object o);

        int hashCode();

        public static <K extends Comparable<? super  K>, V> Comparator<LabMap.Entry<K,V>> comparingByKey() {
            return (Comparator<LabMap.Entry<K,V>> & Serializable)
                    (c1, c2) -> c1.getKey().compareTo(c2.getKey());
        }

        public static <K, V extends Comparable<? super V>> Comparator<LabMap.Entry<K,V>> comparingByValue() {
            return (Comparator<LabMap.Entry<K,V>> & Serializable)
                    (c1, c2) -> c1.getValue().compareTo(c2.getValue());
        }

        
    }
}
