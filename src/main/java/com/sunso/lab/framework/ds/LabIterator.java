package com.sunso.lab.framework.ds;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @Title:LabIterator
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/9上午9:54
 * @m444@126.com
 */
public interface LabIterator<E> {

    boolean hasNext();

    E next();

    default void remove() {
        throw new UnsupportedOperationException("remove");
    }

    default void forEachRemaining(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        while (hasNext()) {
            action.accept(next());
        }
    }


}
