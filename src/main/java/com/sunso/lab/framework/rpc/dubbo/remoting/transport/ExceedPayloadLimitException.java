package com.sunso.lab.framework.rpc.dubbo.remoting.transport;

import java.io.IOException;

/**
 * @Title:ExceedPayloadLimitException
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5上午9:31
 * @m444@126.com
 */
public class ExceedPayloadLimitException extends IOException {

    public ExceedPayloadLimitException(String message) {
        super(message);
    }
}
