package com.sunso.lab.framework.rpc.dubbo.common.serialize;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @Title:ObjectInput
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5上午10:36
 * @m444@126.com
 */
public interface ObjectInput extends DataInput {

    Object readObject() throws IOException, ClassNotFoundException;

    <T> T readObject(Class<T> cls) throws IOException, ClassNotFoundException;

    <T> T readObject(Class<T> cls, Type type) throws IOException, ClassNotFoundException;
}
