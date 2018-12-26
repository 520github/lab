package com.sunso.lab.framework.ds.demo.queue;

import java.util.Comparator;

/**
 * @Title:PriorityEntityComparator
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/19下午8:08
 * @m444@126.com
 */
public class PriorityEntityComparator implements Comparator<PriorityEntity> {
    @Override
    public int compare(PriorityEntity o1, PriorityEntity o2) {
        if(o1.getPriority() == o2.getPriority()) {
            return 0;
        }

        return o1.getPriority() > o2.getPriority()? -1: 1;
    }
}
