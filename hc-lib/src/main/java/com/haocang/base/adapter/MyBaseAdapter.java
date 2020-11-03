package com.haocang.base.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题：
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/1/1710:11
 * 修 改 者：
 * 修改时间：
 */
public class MyBaseAdapter<T> extends BaseAdapter {
    protected List<T> list = new ArrayList<T>();

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    public void addItem(T item) {
        checkList();
        list.add(item);
    }

    protected void checkList() {
        if (list == null) {
            list = new ArrayList<T>();
        }
    }

    public void removeItem(T item) {
        checkList();
        list.remove(item);
    }

    public void removeItem(int position) {
        checkList();
        list.remove(position);
    }

    public void addAll(List<T> tempList) {
        checkList();
        list.addAll(tempList);
    }

    public void clear() {
        checkList();
        list.clear();
//        notifyDataSetChanged();
    }
}
