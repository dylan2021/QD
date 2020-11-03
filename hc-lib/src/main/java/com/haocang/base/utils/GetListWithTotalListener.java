package com.haocang.base.utils;

import java.util.List;

public interface GetListWithTotalListener<T> {
    void success(List<T> list, int total);
}
