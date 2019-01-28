package com.sunso.lab.framework.rpc.dubbo.common.util;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

/**
 * @Title:StringUtils
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/2下午4:53
 * @m444@126.com
 */
public class StringUtils {
    public static final String EMPTY = "";
    private static final Pattern INT_PATTERN = Pattern.compile("^\\d+$");

    public static boolean isInteger(String str) {
        return isNotEmpty(str) && INT_PATTERN.matcher(str).matches();
    }

    public static int parseInteger(String str) {
        return isInteger(str) ? Integer.parseInt(str) : 0;
    }

    public static String join(Collection<String> coll, String split) {
        if (CollectionUtils.isEmpty(coll)) {
            return EMPTY;
        }

        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (String s : coll) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append(split);
            }
            sb.append(s);
        }
        return sb.toString();
    }

    public static String toQueryString(Map<String, String> ps) {
        StringBuilder buf = new StringBuilder();
        if (ps != null && ps.size() > 0) {
            for (Map.Entry<String, String> entry : new TreeMap<String, String>(ps).entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (isNoneEmpty(key, value)) {
                    if (buf.length() > 0) {
                        buf.append("&");
                    }
                    buf.append(key);
                    buf.append("=");
                    buf.append(value);
                }
            }
        }
        return buf.toString();
    }

    public static boolean isNoneEmpty(final String... ss) {
        if (ArrayUtils.isEmpty(ss)) {
            return false;
        }
        for (final String s : ss){
            if (isEmpty(s)) {
                return false;
            }
        }
        return true;
    }

    public static String toString(Throwable e) {
        //UnsafeStringWriter w = new UnsafeStringWriter();
//        PrintWriter p = new PrintWriter();
//        p.print(e.getClass().getName());
//        if (e.getMessage() != null) {
//            p.print(": " + e.getMessage());
//        }
//        p.println();
//        try {
//            e.printStackTrace(p);
//            //return w.toString();
//        } finally {
//            p.close();
//        }
        return "";
    }

    /**
     * @param msg
     * @param e
     * @return string
     */
    public static String toString(String msg, Throwable e) {
//        UnsafeStringWriter w = new UnsafeStringWriter();
//        w.write(msg + "\n");
//        PrintWriter p = new PrintWriter(w);
//        try {
//            e.printStackTrace(p);
//            return w.toString();
//        } finally {
//            p.close();
//        }
        return "";
    }

    public static String camelToSplitName(String camelName, String split) {
        if (isEmpty(camelName)) {
            return camelName;
        }
        StringBuilder buf = null;
        for (int i = 0; i < camelName.length(); i++) {
            char ch = camelName.charAt(i);
            if (ch >= 'A' && ch <= 'Z') {
                if (buf == null) {
                    buf = new StringBuilder();
                    if (i > 0) {
                        buf.append(camelName.substring(0, i));
                    }
                }
                if (i > 0) {
                    buf.append(split);
                }
                buf.append(Character.toLowerCase(ch));
            } else if (buf != null) {
                buf.append(ch);
            }
        }
        return buf == null ? camelName : buf.toString();
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isBlank(String str) {
        return isEmpty(str);
    }
}
