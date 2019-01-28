package com.sunso.lab.framework.rpc.dubbo.rpc.service;

/**
 * @Title:GenericService
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/11下午3:19
 * @m444@126.com
 */
public interface GenericService {
    Object $invoke(String method, String[] parameterTypes, Object[] args) throws GenericException;
}
