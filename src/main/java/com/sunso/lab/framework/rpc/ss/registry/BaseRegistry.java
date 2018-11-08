package com.sunso.lab.framework.rpc.ss.registry;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * @Title:BaseRegistry
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/8下午4:33
 * @m444@126.com
 */
public abstract class BaseRegistry {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected CountDownLatch latch = new CountDownLatch(1);
    protected String registryAddress;

    protected ZooKeeper connectServer() {
        ZooKeeper zk = null;
        try{
            zk = new ZooKeeper(registryAddress, RegistryConstant.ZK_SESSION_TIMEOUT, new Watcher() {
                public void process(WatchedEvent watchedEvent) {
                    if(watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                        latch.countDown();
                    }
                }
            });
            latch.await();
        }catch (Exception e) {

        }
        return zk;
    }
}
