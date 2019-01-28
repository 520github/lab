package com.sunso.lab.framework.rpc.dubbo.config.support;

import java.lang.annotation.*;

/**
 * @Title:Parameter
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/10上午11:06
 * @m444@126.com
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Parameter {
    String key() default "";

    boolean required() default false;

    boolean excluded() default false;

    boolean escaped() default false;

    boolean attribute() default false;

    boolean append() default false;
}
