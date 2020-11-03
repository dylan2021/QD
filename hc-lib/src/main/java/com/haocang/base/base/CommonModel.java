package com.haocang.base.base;

import android.content.Context;

import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.utils.GetListListener;
import com.haocang.base.utils.GetListStringListener;
import com.haocang.base.utils.GetListWithTotalListener;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 公司地址：中国上海市徐汇区云锦路500号绿地汇中心A座20楼2001
 * 网址：http://www.haocang.com/
 * 标        题：
 * 部        门：产品研发部
 * 版        本： 1.0
 * 创  建  者：whhc
 * 创建时间：2018/5/14上午9:41
 * 修  改  者：
 * 修改时间：
 */
public interface CommonModel<T> {
    /**
     * 设置上下文参数.
     *
     * @param context 上下文参数
     * @return
     */
    CommonModel setContext(Context context);

    /**
     * 以map的形式设置参数.
     *
     * @param map 参数map
     * @return
     */
    CommonModel setParamMap(Map<String, Object> map);

    /**
     * 设置列表返回监听.
     *
     * @param listener 列表监听
     */
    CommonModel setListListener(GetListListener<T> listener);

    CommonModel setStringListener(final GetListStringListener listener);
    /**
     * 设置获取详情返回监听.
     *
     * @param listener 返回监听
     */
    CommonModel setEntityListener(GetEntityListener<T> listener);


    /**
     * 设置访问URL.
     *
     * @param url 后台地址
     */
    CommonModel setUrl(String url);


    /**
     * 获取列表.
     *
     * @return
     */
    CommonModel getList();

    CommonModel getString();

    CommonModel submit(String object);

    /**
     * 获取列表.
     *
     * @return
     */
    CommonModel getListWithTotal();

    CommonModel setListListenerWithTotal(final GetListWithTotalListener<T> listener);

    /**
     * 获取详情.
     *
     * @return 本身F
     */
    @Deprecated
    CommonModel getEntity();

    /**
     * 获取详情，直接转，不判断.
     *
     * @return 本身F
     */
    CommonModel getEntityNew();

    /**
     * 设置详情转换类类型.
     *
     * @param type 详情类类型
     * @return
     */
    CommonModel setEntityType(Class<?> type);

    /**
     * 设置列表转换类类型.
     *
     * @param classType 类类型F
     * @return
     */
    CommonModel setListType(Type classType);

    /**
     * 修改详情.
     *
     * @return
     */
    CommonModel putEntity();

    /**
     * 新增
     */
    CommonModel postEntity();

    CommonModel postEntityWithWholeUrl();

    /**
     * 设置是否需要
     *
     * @param hasDialog
     * @return
     */
    CommonModel setHasDialog(boolean hasDialog);

    CommonModel setErrorInterface(CommonModelImpl.ErrorInterface errorInterface);
}
