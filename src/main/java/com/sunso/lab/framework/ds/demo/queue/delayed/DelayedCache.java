package com.sunso.lab.framework.ds.demo.queue.delayed;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;

/**
 * @Title:DelayedCache
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/19下午10:17
 * @m444@126.com
 */
public class DelayedCache<K, V> {
    private ConcurrentHashMap<K,V> map = new ConcurrentHashMap<>();
    private DelayQueue<DelayedEntity<K>> queue = new DelayQueue<>();

    public DelayedCache() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                clearExpireCache();
            }
        });
        //t.setDaemon(true);
        t.start();
    }

    public void put(K k, V v, long expireTime) {
        map.put(k, v);
        DelayedEntity<K> entity = new DelayedEntity<>(k, expireTime);
        queue.put(entity);
    }

    private void clearExpireCache() {
        while (true) {
            //DelayedEntity<K> entity = queue.poll();
            DelayedEntity<K> entity = null;
            try {
                entity = queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(entity != null) {
                map.remove(entity.getT());
                System.out.println(System.nanoTime()+" remove "+entity.getT() +" from cache");
            }
            try{
                Thread.sleep(1);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
