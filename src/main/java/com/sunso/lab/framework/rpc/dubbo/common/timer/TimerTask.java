package com.sunso.lab.framework.rpc.dubbo.common.timer;

/**
 * @Title:TimerTask
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/3上午11:34
 * @m444@126.com
 */
public interface TimerTask {

    void run(Timeout timeout) throws Exception;

}
