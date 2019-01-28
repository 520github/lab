package com.sunso.lab.framework.rpc.dubbo.common.serialize.fst;

import com.sunso.lab.framework.rpc.dubbo.common.serialize.support.SerializableClassRegistry;
import org.nustaq.serialization.FSTConfiguration;
import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Title:FstFactory
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5上午11:07
 * @m444@126.com
 */
public class FstFactory {

    private static final FstFactory factory = new FstFactory();

    private final FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();

    public static FstFactory getDefaultFactory() {
        return factory;
    }

    public FstFactory() {
        SerializableClassRegistry.getRegisteredClasses().keySet().forEach(conf::registerClass);
    }

    public FSTObjectOutput getObjectOutput(OutputStream outputStream) {
        return conf.getObjectOutput(outputStream);
    }

    public FSTObjectInput getObjectInput(InputStream inputStream) {
        return conf.getObjectInput(inputStream);
    }

}
