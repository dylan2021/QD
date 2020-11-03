package com.haocang.base.utils;

public interface GetEntityListener<T> {
    /**
     *
     */
    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";

    public void success(T entity);

    public void fail(String err);
}
