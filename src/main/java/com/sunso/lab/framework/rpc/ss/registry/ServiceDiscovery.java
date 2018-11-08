package com.sunso.lab.framework.rpc.ss.registry;

import org.apache.commons.collections4.ListUtils;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Title:ServiceDiscovery
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/8下午5:40
 * @m444@126.com
 */
public class ServiceDiscovery extends BaseRegistry {

    private volatile List<String> dataList = new ArrayList<String>();

    public ServiceDiscovery(String registryAddress) {
        this.registryAddress = registryAddress;
        ZooKeeper zk = connectServer();
        if(zk != null) {
            watchNode(zk);
        }
    }

    public String discover() {
        if(dataList == null || dataList.size() < 1) {
            return null;
        }
        if(dataList.size() == 1) {
            return dataList.get(0);
        }
        int size = dataList.size();
        return dataList.get(new Random().nextInt(size));
    }

    private void watchNode(final ZooKeeper zk) {
        try {
            List<String> nodeList = zk.getChildren(RegistryConstant.ZK_REGISTRY_PATH, new Watcher() {
                public void process(WatchedEvent watchedEvent) {
                    if(watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {
                        watchNode(zk);
                    }
                }
            });

            List<String> dataList = new ArrayList<String>();
            for(String node: nodeList) {
                byte[] bytes = zk.getData(RegistryConstant.ZK_REGISTRY_PATH + "/" + node, false, null);
                dataList.add(new String(bytes));
            }

            this.dataList = dataList;
        }catch (Exception e) {
            logger.error("watchNode", e);
        }
    }
}
