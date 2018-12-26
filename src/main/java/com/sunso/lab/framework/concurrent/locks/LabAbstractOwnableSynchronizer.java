package com.sunso.lab.framework.concurrent.locks;

import java.io.Serializable;

/**
 * @Title:LabAbstractOwnableSynchronizer
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/2下午12:15
 * @m444@126.com
 */
public abstract class LabAbstractOwnableSynchronizer implements Serializable {
    private static final long serialVersionUID = 1L;
    private transient Thread exclusiveOwnerThread;

    protected LabAbstractOwnableSynchronizer() {

    }

    protected final void setExclusiveOwnerThread(Thread thread) {
        exclusiveOwnerThread = thread;
    }

    protected final Thread getExclusiveOwnerThread() {
        return exclusiveOwnerThread;
    }
}
