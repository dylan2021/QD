package com.haocang.base.base;

import java.util.List;

/**
 * Created by william on 2018/4/2.
 */

public interface IBasePageView<T> {
    void onAllPageLoaded();

    void renderList(boolean reload, List<T> list);

    void renderError(Exception e);
}
