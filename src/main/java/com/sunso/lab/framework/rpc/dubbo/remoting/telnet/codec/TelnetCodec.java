package com.sunso.lab.framework.rpc.dubbo.remoting.telnet.codec;

import com.sunso.lab.framework.rpc.dubbo.common.Constants;
import com.sunso.lab.framework.rpc.dubbo.common.URL;
import com.sunso.lab.framework.rpc.dubbo.common.util.StringUtils;
import com.sunso.lab.framework.rpc.dubbo.remoting.Channel;
import com.sunso.lab.framework.rpc.dubbo.remoting.RemotingException;
import com.sunso.lab.framework.rpc.dubbo.remoting.buffer.ChannelBuffer;
import com.sunso.lab.framework.rpc.dubbo.remoting.transport.codec.TransportCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @Title:TelnetCodec
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5下午7:17
 * @m444@126.com
 */
public class TelnetCodec extends TransportCodec {

    private static final Logger logger = LoggerFactory.getLogger(TelnetCodec.class);

    private static final String HISTORY_LIST_KEY = "telnet.history.list";
    private static final String HISTORY_INDEX_KEY = "telnet.history.index";

    private static final byte[] UP = new byte[]{27, 91, 65};

    private static final byte[] DOWN = new byte[]{27, 91, 66};

    private static final List<?> ENTER = Arrays.asList(new Object[]{
            new byte[]{'\r', '\n'} /* Windows Enter */,
            new byte[]{'\n'} /* Linux Enter */});

    private static final List<?> EXIT = Arrays.asList(new Object[]{
            new byte[]{3} /* Windows Ctrl+C */,
            new byte[]{-1, -12, -1, -3, 6} /* Linux Ctrl+C */,
            new byte[]{-1, -19, -1, -3, 6} /* Linux Pause */});

    public void encode(Channel channel, ChannelBuffer buffer, Object message) throws IOException {
        if (message instanceof String) {
            if(isClientSide(channel)) {
                message = message + "\r\n";
            }
            byte[] msgData = ((String) message).getBytes(getCharset(channel).name());
            buffer.writeBytes(msgData);
        }
        else {
            super.encode(channel, buffer, message);
        }
    }

    public Object decode(Channel channel, ChannelBuffer buffer) throws IOException {
        int readable = buffer.readableBytes();
        byte[] message = new byte[readable];
        buffer.readBytes(message);
        return decode(channel, buffer, readable, message);
    }

    protected Object decode(Channel channel, ChannelBuffer buffer, int readable, byte[] message)
            throws IOException {
        if(isClientSide(channel)) {
            return toString(message, getCharset(channel));
        }
        checkPayload(channel, readable);
        if (message == null || message.length == 0) {
            return DecodeResult.NEED_MORE_INPUT;
        }
        if (message[message.length - 1] == '\b') {
            try {
                boolean doublechar = message.length >= 3 && message[message.length - 3] < 0; // double byte char
                channel.send(new String(doublechar ? new byte[]{32, 32, 8, 8} : new byte[]{32, 8}, getCharset(channel).name()));
            } catch (RemotingException e) {
                throw new IOException(StringUtils.toString(e));
            }
            return DecodeResult.NEED_MORE_INPUT;
        }

        for (Object command : EXIT) {
            if (isEquals(message, (byte[]) command)) {
                if (logger.isInfoEnabled()) {
                    //logger.info(new Exception("Close channel " + channel + " on exit command: " + Arrays.toString((byte[]) command)));
                }
                channel.close();
                return null;
            }
        }

        boolean up = endsWith(message, UP);
        boolean down = endsWith(message, DOWN);
        if (up || down) {
            LinkedList<String> history = (LinkedList<String>) channel.getAttribute(HISTORY_LIST_KEY);
            if (history == null || history.isEmpty()) {
                return DecodeResult.NEED_MORE_INPUT;
            }

            Integer index = (Integer) channel.getAttribute(HISTORY_INDEX_KEY);
            Integer old = index;
            if (index == null) {
                index = history.size() - 1;
            }
            else {
                if (up) {
                    index = index - 1;
                    if (index < 0) {
                        index = history.size() - 1;
                    }
                }
                else {
                    index = index + 1;
                    if (index > history.size() - 1) {
                        index = 0;
                    }
                }
            }

            if (old == null || !old.equals(index)) {
                channel.setAttribute(HISTORY_INDEX_KEY, index);
                String value = history.get(index);
                if (old != null && old >= 0 && old < history.size()) {
                    String ov = history.get(old);
                    StringBuilder buf = new StringBuilder();
                    for (int i = 0; i < ov.length(); i++) {
                        buf.append("\b");
                    }
                    for (int i = 0; i < ov.length(); i++) {
                        buf.append(" ");
                    }
                    for (int i = 0; i < ov.length(); i++) {
                        buf.append("\b");
                    }
                    value = buf.toString() + value;
                }
                try {
                    channel.send(value);
                } catch (RemotingException e) {
                    throw new IOException(StringUtils.toString(e));
                }
            }
            return DecodeResult.NEED_MORE_INPUT;
        }

        for (Object command : EXIT) {
            if (isEquals(message, (byte[]) command)) {
                if (logger.isInfoEnabled()) {
                    //logger.info(new Exception("Close channel " + channel + " on exit command " + command));
                }
                channel.close();
                return null;
            }
        }

        byte[] enter = null;
        for (Object command : ENTER) {
            if (endsWith(message, (byte[]) command)) {
                enter = (byte[]) command;
                break;
            }
        }

        if (enter == null) {
            return DecodeResult.NEED_MORE_INPUT;
        }

        LinkedList<String> history = (LinkedList<String>) channel.getAttribute(HISTORY_LIST_KEY);
        Integer index = (Integer) channel.getAttribute(HISTORY_INDEX_KEY);
        channel.removeAttribute(HISTORY_INDEX_KEY);
        if (history != null && !history.isEmpty() && index != null && index >= 0 && index < history.size()) {
            String value = history.get(index);
            if (value != null) {
                byte[] b1 = value.getBytes();
                byte[] b2 = new byte[b1.length + message.length];
                System.arraycopy(b1, 0, b2, 0, b1.length);
                System.arraycopy(message, 0, b2, b1.length, message.length);
                message = b2;
            }
        }

        String result = toString(message, getCharset(channel));
        if (result.trim().length() > 0) {
            if (history == null) {
                history = new LinkedList<String>();
                channel.setAttribute(HISTORY_LIST_KEY, history);
            }
            if (history.isEmpty()) {
                history.addLast(result);
            } else if (!result.equals(history.getLast())) {
                history.remove(result);
                history.addLast(result);
                if (history.size() > 10) {
                    history.removeFirst();
                }
            }
        }

        return result;
    }

    private static String toString(byte[] message, Charset charset)
            throws UnsupportedEncodingException {
        byte[] copy = new byte[message.length];
        int index = 0;
        for (int i = 0; i < message.length; i++) {
            byte b = message[i];
            if (b == '\b') {
                if (index > 0) {
                    index--;
                }
                if (i > 2 && message[i - 2] < 0) { // double byte char
                    if (index > 0) {
                        index--;
                    }
                }
            }
            else if (b == 27) {
                if (i < message.length - 4 && message[i + 4] == 126) {
                    i = i + 4;
                }
                else if (i < message.length - 3 && message[i + 3] == 126) {
                    i = i + 3;
                }
                else if (i < message.length - 2) {
                    i = i + 2;
                }
            }
            else if (b == -1 && i < message.length - 2
                    && (message[i + 1] == -3 || message[i + 1] == -5)) {
                i = i + 2;
            }
            else {
                copy[index++] = message[i];
            }
        }
        if(index == 0) {
            return "";
        }
        return new String(copy, 0, index, charset.name()).trim();
    }

    private static Charset getCharset(Channel channel) {
        if(channel != null) {
            Object attribute = channel.getAttribute(Constants.CHARSET_KEY);
            if (attribute instanceof String) {
                try {
                    return Charset.forName((String) attribute);
                } catch (Throwable t) {
                    //logger.warn(t.getMessage(), t);
                }
            }
            else if (attribute instanceof Charset) {
                return (Charset) attribute;
            }
            URL url = channel.getUrl();
            if(url != null) {
                String parameter = url.getParameter(Constants.CHARSET_KEY);
                if (parameter != null && parameter.length() > 0) {
                    try {
                        return Charset.forName(parameter);
                    } catch (Throwable t) {
                        //logger.warn(t.getMessage(), t);
                    }
                }
            }
        }
        try {
            return Charset.forName(Constants.DEFAULT_CHARSET);
        } catch (Throwable t) {
            //logger.warn(t.getMessage(), t);
        }
        return Charset.defaultCharset();
    }

    private static boolean isEquals(byte[] message, byte[] command) throws IOException {
        return message.length == command.length && endsWith(message, command);
    }

    private static boolean endsWith(byte[] message, byte[] command) throws IOException {
        if (message.length < command.length) {
            return false;
        }
        int offset = message.length - command.length;
        for (int i = command.length - 1; i >= 0; i--) {
            if (message[offset + i] != command[i]) {
                return false;
            }
        }
        return true;
    }
}
