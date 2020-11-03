package com.haocang.base.base;

/**
 * BasePresenter
 * Created by mingzhenli on 2017/12/11.
 */
public interface BasePresenter<T> {
    void takeView(T view);

    void dropView();
}
