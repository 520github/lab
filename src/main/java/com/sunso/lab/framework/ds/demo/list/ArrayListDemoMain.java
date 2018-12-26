package com.sunso.lab.framework.ds.demo.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @Title:ArrayListDemoMain
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/16上午10:12
 * @m444@126.com
 */
public class ArrayListDemoMain {

    public static void main(String[] args) {
        //arrayListDemo();
        linkedListDemo();
        //new Concurrent
    }

    private static void arrayListDemo() {
        List<String> arrayList = new ArrayList();
        for(int i=1; i<=5;i++) {
            arrayList.add(String.valueOf(i));
        }
        String value = "6";
        arrayList.add(1, value);
        arrayList.add(6, value);
        arrayList.remove(value);
        arrayList.remove(4);
    }

    private static void linkedListDemo() {
        List<String> linkedList = new LinkedList<>();
        for(int i=1; i<=5; i++) {
            linkedList.add(String.valueOf(i));
        }
        String value = "6";
        linkedList.add(1, value);
        linkedList.add(7, value);
        linkedList.remove(value);
        linkedList.remove(4);
    }
}
