package com.rt.shop.common.redis;

public interface Function<T, E> {

    public T callback(E e);

}
