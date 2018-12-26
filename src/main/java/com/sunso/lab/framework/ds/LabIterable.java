package com.sunso.lab.framework.ds;

import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

/**
 * @Title:LabIterable
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/9上午9:53
 * @m444@126.com
 */
public interface LabIterable<T> {

    LabIterator<T> iterator();

//    default void forEach(Consumer<? super T> action) {
//        Objects.requireNonNull(action);
//        for(T t: this) {
//            action.accept(t);
//        }
//    }
//
//    default Spliterator<T> spliterator() {
//        return Spliterators.spliteratorUnknownSize(iterator(), 0);
//    }
}
