package com.sunso.lab.framework.ds.demo.map;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Title:HashMapDemoMain
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/10下午5:31
 * @m444@126.com
 */
public class HashMapDemoMain {

    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>(64);
//        System.out.println(1&63);
//        System.out.println(65&63);
//        System.out.println(129&63);
//        System.out.println(193&63);
//        System.out.println(257&63);
//        System.out.println(321&63);
//        System.out.println(385&63);
//        System.out.println(449&63);
//        System.out.println(513&63);

//        map.put(1, "1");
//        map.put(65, "65");
//        map.put(129, "129");
//        map.put(193, "193");
//        map.put(257, "257");
//        map.put(321, "321");
//        map.put(385, "385");
//        map.put(449, "449");
//        map.put(513, "513");
//        Set<Integer> set = map.keySet();
//        Iterator<Integer> it = set.iterator();
//        while (it.hasNext()) {
//            Integer key = it.next();
//            System.out.println("key-->" + key);
//        }


//        Map<Integer, String> linkedMap = new LinkedHashMap<>();
//        linkedMap.put(1, "1");
//        linkedMap.put(2, "2");
//        linkedMap.put(1, "2");


        Map<String, String> treeMap = new TreeMap<>();
//        treeMap.put(new TreeKey(), "111");
//        treeMap.put(new TreeKey(), "222");
//        treeMap.put("1", "11");
//        treeMap.put(Integer.valueOf(10), "10");
//        treeMap.put("2", "22");
//        treeMap.put("3", "33");
//        treeMap.put("5", "55");
//        treeMap.put("4", "44");
//        Iterator<String> it = treeMap.keySet().iterator();
//        while (it.hasNext()) {
//            String key = it.next();
//            System.out.println("key-->" + key);
//        }
//
//        Collections.synchronizedMap(treeMap);

        Map<String,String> hashTable = new Hashtable<>();
//        hashTable.put("1", "1");
//        hashTable.put("2", "1");
//        hashTable.put("5", "1");
//        hashTable.put("3", "1");
//        hashTable.put("4", "1");

        Map<String, String> concurrentMap = new ConcurrentHashMap<>(2);
        concurrentMap.put("1", "1");
        concurrentMap.put("2", "2");
        concurrentMap.put("5", "5");
        concurrentMap.put("3", "3");
        concurrentMap.put("4", "4");


//        map.put("456", "456");
//        map.put("458", "458");
//        map.put("123", "123");
//        map.put("125", "125");
//        System.out.println(map.size());
//        map.remove("123");
//        map.put("789", "789");
//        System.out.println(map.get("456"));
    }

    private static class TreeKey implements Comparable {
        private String key = "";

        TreeKey() {

        }

        @Override
        public int compareTo(Object o) {
            return 0;
        }
    }
}
