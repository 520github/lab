package com.sunso.lab.framework.rpc.dubbo.common.serialize;

import java.io.IOException;

/**
 * @Title:ObjectOutput
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5上午10:42
 * @m444@126.com
 */
public interface ObjectOutput extends DataOutput {
    void writeObject(Object obj) throws IOException;
}
