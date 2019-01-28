package com.sunso.lab.framework.rpc.dubbo.common.util;

/**
 * @Title:ArrayUtils
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/11下午8:01
 * @m444@126.com
 */
public final class ArrayUtils {

    private ArrayUtils() {
    }

    /**
     * <p>Checks if the array is null or empty. <p/>
     *
     * @param array th array to check
     * @return {@code true} if the array is null or empty.
     */
    public static boolean isEmpty(final Object[] array) {
        return array == null || array.length == 0;
    }

    /**
     * <p>Checks if the array is not null or empty. <p/>
     *
     * @param array th array to check
     * @return {@code true} if the array is not null or empty.
     */
    public static boolean isNotEmpty(final Object[] array) {
        return !isEmpty(array);
    }

}
