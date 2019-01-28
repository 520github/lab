package com.sunso.lab.framework.rpc.dubbo.remoting.transport;

import com.sunso.lab.framework.rpc.dubbo.common.Constants;
import com.sunso.lab.framework.rpc.dubbo.common.URL;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.ObjectInput;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.Serialization;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title:CodecSupport
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5上午10:47
 * @m444@126.com
 */
public class CodecSupport {

    private static Map<Byte, Serialization> ID_SERIALIZATION_MAP = new HashMap<Byte, Serialization>();
    private static Map<Byte, String> ID_SERIALIZATIONNAME_MAP = new HashMap<Byte, String>();

    static {
        Map<String, Serialization> serializationMap = getSerializationMap();
        if(serializationMap != null) {
            for(String key: serializationMap.keySet()) {
                Serialization serialization = serializationMap.get(key);
                byte idByte = serialization.getContentTypeId();
                ID_SERIALIZATION_MAP.put(idByte, serialization);
                ID_SERIALIZATIONNAME_MAP.put(idByte, key);
            }
        }
    }

    private static Map<String, Serialization> getSerializationMap() {
        return null;
    }

    private CodecSupport() {
    }

    public static Serialization getSerializationById(Byte id) {
        return ID_SERIALIZATION_MAP.get(id);
    }

    public static Serialization getSerialization(URL url) {
        String name = url.getParameter(Constants.SERIALIZATION_KEY, Constants.DEFAULT_REMOTING_SERIALIZATION);
        return null;
    }

    public static Serialization getSerialization(URL url, Byte id) throws IOException {
        Serialization serialization = getSerializationById(id);
        String serializationName = url.getParameter(Constants.SERIALIZATION_KEY, Constants.DEFAULT_REMOTING_SERIALIZATION);

        if (serialization == null
                || ((id == 3 || id == 7 || id == 4) && !(serializationName.equals(ID_SERIALIZATIONNAME_MAP.get(id))))) {
            throw new IOException("Unexpected serialization id:" + id + " received from network, please check if the peer send the right id.");
        }

        return serialization;
    }

    public static ObjectInput deserialize(URL url, InputStream is, byte proto) throws IOException {
        Serialization s = getSerialization(url, proto);
        return s.deserialize(url, is);
    }
}
