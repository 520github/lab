package com.sunso.lab.framework.rpc.ss.server;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Title:RpcService
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/8下午4:55
 * @m444@126.com
 */
@Target({ElementType.TYPE}) //注解用在接口上
@Retention(RetentionPolicy.RUNTIME) ////VM将在运行期也保留注释，因此可以通过反射机制读取注解的信息
@Component
public @interface RpcService {
    Class<?> value();
}
