package com.sunso.lab.framework.concurrent.demo;

import com.sunso.lab.framework.concurrent.AgileAction;

/**
 * @Title:DemoAction
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/26下午9:45
 * @m444@126.com
 */
public class DemoAction implements AgileAction {
    private DemoService demoService;

    public DemoAction(DemoService demoService) {
        this.demoService = demoService;
    }

    @Override
    public void action() {
        demoService.say();
    }
}
