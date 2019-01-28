package com.sunso.lab.framework.rpc.dubbo.common.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Title:StreamUtils
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5下午9:37
 * @m444@126.com
 */
public class StreamUtils {

    public static void skipUnusedStream(InputStream is) throws IOException {
        if (is.available() > 0) {
            is.skip(is.available());
        }
    }
}
