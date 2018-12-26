package com.sunso.lab.framework.ds.demo.queue;

/**
 * @Title:PriorityEntity
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/19下午5:13
 * @m444@126.com
 */
public class PriorityEntity implements Comparable<PriorityEntity> {
    private int index;
    private int priority;

    public PriorityEntity(int index, int priority) {
        this.index = index;
        this.priority = priority;
    }

    @Override
    public int compareTo(PriorityEntity o) {
        if(this.priority == o.priority) {
            return 0;
        }
        return this.priority > o.priority? 1: -1;
    }

    public String toString() {
        return "index=" + this.index + " priority=" + this.priority;
    }

    public int getPriority() {
        return priority;
    }
}
