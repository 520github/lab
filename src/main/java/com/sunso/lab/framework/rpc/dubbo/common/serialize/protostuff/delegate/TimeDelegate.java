package com.sunso.lab.framework.rpc.dubbo.common.serialize.protostuff.delegate;

import io.protostuff.Input;
import io.protostuff.Output;
import io.protostuff.Pipe;
import io.protostuff.WireFormat;
import io.protostuff.runtime.Delegate;

import java.io.IOException;
import java.sql.Time;

/**
 * @Title:TimeDelegate
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5下午3:34
 * @m444@126.com
 */
public class TimeDelegate implements Delegate<Time> {
    @Override
    public WireFormat.FieldType getFieldType() {
        return WireFormat.FieldType.FIXED64;
    }

    @Override
    public Time readFrom(Input input) throws IOException {
        return new Time(input.readFixed64());
    }

    @Override
    public void writeTo(Output output, int i, Time time, boolean b) throws IOException {
        output.writeFixed64(i, time.getTime(), b);
    }

    @Override
    public void transfer(Pipe pipe, Input input, Output output, int i, boolean b) throws IOException {
        output.writeFixed64(i, input.readFixed64(), b);
    }

    @Override
    public Class<?> typeClass() {
        return Time.class;
    }
}
