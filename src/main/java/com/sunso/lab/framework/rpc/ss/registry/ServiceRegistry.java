package com.sunso.lab.framework.rpc.ss.registry;

import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

/**
 * @Title:ServiceRegistry
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: panda-fintech
 * @Created on 2018/11/8下午4:05
 * @miaoxuehui@panda-fintech.com
 */
public class ServiceRegistry extends BaseRegistry {
    private String registryAddress;
    private CountDownLatch latch = new CountDownLatch(1);

    public ServiceRegistry(String registryAddress){
        this.registryAddress = registryAddress;
    }

    public void register(String data) {
        if(data == null) {
            return;
        }
        ZooKeeper zk = connectServer();
        if(zk == null) {
            return;
        }
        createNode(zk, data);
    }

    private void createNode(ZooKeeper zk, String data) {
        try {
            if(zk.exists(RegistryConstant.ZK_REGISTRY_PATH, null) == null) {
                zk.create(RegistryConstant.ZK_REGISTRY_PATH, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }

            String path = zk.create(RegistryConstant.ZK_DATA_PATH, data.getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            logger.debug("success create zookeeper node ({} => {})", path, data);
        }catch (Exception e) {
            logger.error("createNode", e);
        }
    }


}
