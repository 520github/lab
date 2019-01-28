package com.sunso.lab.framework.rpc.dubbo.common.serialize.kryo.utils;

import com.esotericsoftware.kryo.Kryo;

/**
 * @Title:ThreadLocalKryoFactory
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5下午2:47
 * @m444@126.com
 */
public class ThreadLocalKryoFactory extends AbstractKryoFactory {

    private final ThreadLocal<Kryo> holder = new ThreadLocal<Kryo>(){
        protected Kryo initialValue() {
            return create();
        }
    };

    @Override
    public void returnKryo(Kryo kryo) {
    }

    @Override
    public Kryo getKryo() {
        return holder.get();
    }


}
