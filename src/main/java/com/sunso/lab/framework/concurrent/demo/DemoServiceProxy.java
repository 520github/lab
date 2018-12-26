package com.sunso.lab.framework.concurrent.demo;

import com.sunso.lab.framework.concurrent.AgileAction;
import com.sunso.lab.framework.concurrent.AgileRun;

/**
 * @Title:DemoServiceProxy
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/26下午9:47
 * @m444@126.com
 */
public class DemoServiceProxy implements DemoService {

    private DemoService demoService;
    private AgileRun agileRun;

    public DemoServiceProxy() {
        this.demoService = new DemoServiceImpl();
        this.agileRun = new AgileRun();
    }

    @Override
    public void say() {
        AgileAction agileAction = new DemoAction(demoService);
        agileRun.putQueue(agileAction);
    }
}
