package com.sunso.lab.framework.concurrent.locks;

import sun.misc.Unsafe;

import java.io.Serializable;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.LockSupport;

/**
 * @Title:LabAbstractQueuedSynchronizer
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/2下午3:23
 * @m444@126.com
 */
public abstract class LabAbstractQueuedSynchronizer extends LabAbstractOwnableSynchronizer implements Serializable {
    private static final long serialVersionUID = 1L;
    private transient volatile LabNode head;
    private transient volatile LabNode tail;
    private volatile int state;

    static final long spinForTimeoutThreshold = 1000L;

    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long stateOffset;
    private static final long headOffset;
    private static final long tailOffset;
    private static final long waitStatusOffset;
    private static final long nextOffset;

    static {
        try {
            stateOffset = unsafe.objectFieldOffset
                    (AbstractQueuedSynchronizer.class.getDeclaredField("state"));
            headOffset = unsafe.objectFieldOffset
                    (AbstractQueuedSynchronizer.class.getDeclaredField("head"));
            tailOffset = unsafe.objectFieldOffset
                    (AbstractQueuedSynchronizer.class.getDeclaredField("tail"));
            waitStatusOffset = unsafe.objectFieldOffset
                    (LabNode.class.getDeclaredField("waitStatus"));
            nextOffset = unsafe.objectFieldOffset
                    (LabNode.class.getDeclaredField("next"));

        } catch (Exception ex) { throw new Error(ex); }
    }


    protected LabAbstractQueuedSynchronizer() {

    }

    protected final int getState() {
        return state;
    }

    protected final void setState(int newState) {
        state = newState;
    }

    protected final boolean compareAndSetState(int expect, int update) {
        return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
    }

    protected final boolean compareAndSetHead(LabNode update) {
        return unsafe.compareAndSwapObject(this, headOffset, null, update);
    }

    protected final boolean compareAndSetTail(LabNode expect, LabNode update) {
        return unsafe.compareAndSwapObject(this, tailOffset, expect, update);
    }

    private static final boolean compareAndSetWaitStatus(LabNode node, int expect, int update) {
        return unsafe.compareAndSwapInt(node, waitStatusOffset, expect, update);
    }

    private static final boolean compareAndSetNext(LabNode node, LabNode expect, LabNode update) {
        return unsafe.compareAndSwapObject(node, nextOffset, expect, update);
    }

    public final void acquire(int arg) {
        if(!tryAcquire(arg) && acquireQueued(addWaiter(LabNode.EXCLUSIVE), arg)) {
            selfInterrupt();
        }
    }

    protected boolean tryAcquire(int arg) {
        throw new UnsupportedOperationException();
    }

    public final boolean hasQueuedPredecessors() {
        LabNode t = tail;
        LabNode h = head;
        LabNode s ;
        return t!=h && ((s=h.next) == null || s.thread != Thread.currentThread());
    }

    final boolean acquireQueued(final LabNode node, int arg) {
        boolean failed = true;
        try{
            boolean interrupted = false;
            for(;;) {
                final LabNode p = node.predecessor();
                //当前节点的前一个节点是head的话，重新再次尝试获取锁,获取成功的话直接返回
                if(p == head && tryAcquire(arg)) {
                    setHead(node);
                    p.next = null;
                    failed = false;
                    return interrupted;
                }
                if(shouldParkAfterFailedAcquire(p, node) && parkAndCheckInterrupt()) {
                    interrupted = true;
                }
            }
        }finally {
            if(failed) {
                cancelAcquire(node);
            }
        }
    }

    /**
     * 当前线程取消获取锁
     * @param node
     */
    private void cancelAcquire(LabNode node) {
        if(node == null) {
            return;
        }
        node.thread = null;
        LabNode pred = node.prev;
        while(pred.waitStatus > 0) {
            node.prev = pred = pred.prev;
        }
        LabNode predNext = pred.next;
        node.waitStatus = LabNode.CANCELLED;

        if(node == tail && compareAndSetTail(node, pred)) {
            compareAndSetNext(pred, predNext, null);
        }
        else {
            int ws;
            //非头节点情况
            if(pred != head &&
                    ((ws=pred.waitStatus) == LabNode.SIGNAL || (ws<=0 && compareAndSetWaitStatus(pred, ws, LabNode.SIGNAL)))
                    && pred.thread !=null) {
                LabNode next = node.next;
                if(next != null && next.waitStatus <= 0) {
                    compareAndSetNext(pred, predNext, next);
                }
            }
            //头节点情况
            else {
                unparkSuccessor(node);
            }
            node.next = node;
        }
    }

    private final boolean parkAndCheckInterrupt() {
        LockSupport.park(this);
        return Thread.interrupted();
    }

    // 当获取锁失败的时候,
    private static boolean shouldParkAfterFailedAcquire(LabNode pred, LabNode node) {
        int ws = pred.waitStatus;
        if(ws == LabNode.SIGNAL) {
            return true;
        }
        if(ws > 0) {
            do {
                node.prev = pred = pred.prev;
            }while (pred.waitStatus > 0);
            pred.next = node;
        }
        else {
            compareAndSetWaitStatus(pred, ws, LabNode.SIGNAL);
        }
        return false;
    }

    static void selfInterrupt() {
        Thread.currentThread().interrupt();
    }

    private LabNode enq(final LabNode node) {
        for(;;) {
            LabNode t = tail;
            if(t == null) {
                if(compareAndSetHead(new LabNode())) {
                    tail = head;
                }
            }
            else {
                node.prev = t;
                if(compareAndSetTail(t, node)) {
                    t.next = node;
                    return t;
                }
            }
        }
    }

    private LabNode addWaiter(LabNode mode) {
        LabNode node = new LabNode(Thread.currentThread(), mode);
        LabNode pred = tail;
        if(pred != null) {
            node.prev = tail;
            if(compareAndSetTail(pred, node)) {
                pred.next = node;
                return node;
            }
        }
        enq(node);
        return node;
    }

    private void setHead(LabNode node) {
        head = node;
        node.thread = null;
        node.prev = null;
    }

    protected boolean tryRelease(int arg) {
        throw new UnsupportedOperationException();
    }

    protected boolean tryReleaseShared(int arg) {
        throw new UnsupportedOperationException();
    }

    public final boolean releaseShared(int arg) {
        if(tryReleaseShared(arg)) {
            doReleaseShared();
            return true;
        }
        return false;
    }

    public final boolean release(int arg) {
        if(tryRelease(arg)) {
            LabNode h = head;
            if(h != null && h.waitStatus !=0) {
                unparkSuccessor(h);
            }
            return true;
        }
        return false;
    }

    private void unparkSuccessor(LabNode node) {
        int ws = node.waitStatus;
        //非取消状态
        if(ws < 0) {
            compareAndSetWaitStatus(node, ws, 0);
        }

        LabNode s = node.next;
        //如果当前节点的下一个节点状态为取消状态，或者节点为空
        //从尾部开始重新找一个可用的节点
        if(s == null || s.waitStatus>0) {
            s = null;
            for(LabNode t=tail;t!=null && t!=node; t=t.prev) {
                if(t.waitStatus <= 0) {
                    s = t;
                }
            }
        }

        if(s != null) {
            LockSupport.unpark(s.thread);
        }
    }

    private void doReleaseShared() {
        for(;;) {
            LabNode h = head;
            if(h!= null && h != tail) {
                int ws = h.waitStatus;
                if(ws == LabNode.SIGNAL) {
                    if(!compareAndSetWaitStatus(h, LabNode.SIGNAL, 0)) {
                        continue;
                    }
                    unparkSuccessor(h);
                }
                else  if(ws == 0 && compareAndSetWaitStatus(h, 0, LabNode.PROPAGATE)) {
                    continue;
                }
            }

            if(h == head) {
                break;
            }
        }
    }

    private void setHeadAndPropagate(LabNode node, int propagate) {
        LabNode h = head;
        setHead(node);

        if(propagate > 0 || h == null || h.waitStatus < 0 || (h=head) == null || h.waitStatus < 0) {
            LabNode s = node.next;
            if(s == null || s.isShared()) {
                doReleaseShared();
            }
        }
    }

    public final void acquireInterruptibly(int arg) throws InterruptedException{
        if(Thread.interrupted()) {
            throw new InterruptedException();
        }
        if(!tryAcquire(arg)) {
            doAcquireInterruptibly(arg);
        }
    }

    private void doAcquireInterruptibly(int arg) throws InterruptedException  {
        final LabNode node = addWaiter(LabNode.EXCLUSIVE);
        boolean failed = true;
        try{
            for(;;) {
                final LabNode p = node.predecessor();
                if( p == head && tryAcquire(arg)) {
                    setHead(node);
                    p.next = null;
                    failed = false;
                    return ;
                }

                if(shouldParkAfterFailedAcquire(p, node) && parkAndCheckInterrupt()) {
                    throw new InterruptedException();
                }
            }
        }finally {
            if(failed) {
                cancelAcquire(node);
            }
        }
    }

    private boolean doAcquireNanos(int arg, long nanosTimeout) throws InterruptedException {
        if(nanosTimeout <= 0L) {
            return false;
        }
        final long deadline = System.nanoTime() + nanosTimeout;
        final LabNode node = addWaiter(LabNode.EXCLUSIVE);
        boolean failed = true;
        try{
            for(;;) {
                final LabNode p = node.predecessor();
                if(p == head && tryAcquire(arg)) {
                    setHead(node);
                    p.next = null;
                    failed = false;
                    return true;
                }

                nanosTimeout = deadline - System.nanoTime();
                if(nanosTimeout <= 0L) {
                    return false;
                }

                if(shouldParkAfterFailedAcquire(p, node) && nanosTimeout > spinForTimeoutThreshold) {
                    LockSupport.parkNanos(this, nanosTimeout);
                }

                if(Thread.interrupted()) {
                    throw new InterruptedException();
                }
            }
        }finally {
            if(failed) {
                cancelAcquire(node);
            }
        }
    }

    public final void acquireShared(int arg) {
        if(tryAcquireShared(arg) < 0) {
            doAcquireShared(arg);
        }
    }

    public final void acquireSharedInterruptibly(int arg) throws InterruptedException {
        if(Thread.interrupted()) {
            throw new InterruptedException();
        }
        if(tryAcquireShared(arg) < 0) {
            doAcquireSharedInterruptibly(arg);
        }
    }

    public final boolean tryAcquireSharedNanos(int arg, long nanosTimeout) throws InterruptedException {
        if(Thread.interrupted()) {
            throw new InterruptedException();
        }
        return tryAcquireShared(arg) >=0 || doAcquireSharedNanos(arg, nanosTimeout);
    }

    private void doAcquireShared(int arg) {
        final LabNode node = addWaiter(LabNode.SHARE);
        boolean failed = true;
        try{
            boolean interrupted = false;
            for(;;) {
                final LabNode p = node.predecessor();
                if(p == head) {
                    int r = tryAcquireShared(arg);
                    if(r >= 0) {
                        setHeadAndPropagate(node, r);
                        p.next = null;
                        if(interrupted) {
                            selfInterrupt();
                        }
                        failed = false;
                        return;
                    }
                }
                if(shouldParkAfterFailedAcquire(p, node) &&  parkAndCheckInterrupt()) {
                    interrupted = true;
                }
            }
        }finally {
            if(failed) {
                cancelAcquire(node);
            }
        }
    }

    private void doAcquireSharedInterruptibly(int arg) throws InterruptedException{
        final LabNode node = addWaiter(LabNode.SHARE);
        boolean failed = true;
        try{
            for(;;) {
                final LabNode p = node.predecessor();
                if(p == head) {
                    int r = tryAcquireShared(arg);
                    if(r>=0) {
                        setHeadAndPropagate(node , r);
                        p.next = null;
                        failed = false;
                        return;
                    }
                }
                if(shouldParkAfterFailedAcquire(p, node) && parkAndCheckInterrupt()) {
                    throw new InterruptedException();
                }
            }
        }finally {
            if(failed) {
                cancelAcquire(node);
            }
        }
    }

    private boolean doAcquireSharedNanos(int arg, long nanosTimeout) throws InterruptedException {
        if(nanosTimeout <= 1L) {
            return false;
        }
        final long deadline = System.nanoTime() + nanosTimeout;
        final LabNode node = addWaiter(LabNode.SHARE);
        boolean failed = true;
        try{
            for(;;) {
                final LabNode p = node.predecessor();
                if(p == head) {
                    int r = tryAcquireShared(arg);
                    if(r >= 0) {
                        setHeadAndPropagate(node, r);
                        p.next = null;
                        failed = false;
                        return true;
                    }
                }

                nanosTimeout = System.nanoTime() - deadline;
                if(nanosTimeout <= 0L) {
                    return false;
                }

                if(shouldParkAfterFailedAcquire(p, node) && nanosTimeout > spinForTimeoutThreshold) {
                    LockSupport.parkNanos(this, nanosTimeout);
                }

                if(Thread.interrupted()) {
                    throw new InterruptedException();
                }
            }
        }finally {
            if(failed) {
                cancelAcquire(node);
            }
        }
    }

    protected int tryAcquireShared(int arg) {
        throw new UnsupportedOperationException();
    }
}
