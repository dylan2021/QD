package com.haocang.patrol.manage.iview;

import android.app.Activity;
import android.content.Context;

import com.haocang.patrol.manage.bean.PatrolTaskListDTO;

import java.util.List;

/**
 * Created by william on 2018/4/2.
 */

public interface PatrolListView {

    /**
     * @param e
     */
    void renderError(Exception e);

    /**
     * 渲染数据
     *
     * @param patrolList
     */
    void renderList(List<PatrolTaskListDTO> patrolList);

    /**
     * 加载离线数据
     *
     * @param patrolList
     */
    void offLineRenderList(List<PatrolTaskListDTO> patrolList);

    /**
     * 获取开始时间
     *
     * @return
     */
    String getStartTime();

    /**
     * 获取结束时间
     *
     * @return
     */
    String getEndTime();

    /**
     * 获取执行状态
     *
     * @return
     */
    String getExcuteStatus();

    /**
     * 获取查询名称
     *
     * @return
     */
    String getQueryName();

    /**
     * 是否时刷新
     *
     * @return
     */
    boolean isRefresh();

    /**
     * 获取当前加载页数
     *
     * @return
     */
    int getPage();

    /**
     * @return
     */
    Context getContext();

    /**
     * 设置开始时间
     *
     * @param startTime 开始时间
     */
    void setStartTime(String startTime);

    /**
     * 设置结束时间
     *
     * @param endTime 结束时间
     */
    void setEndTime(String endTime);

    /**
     * 设置执行状态
     *
     * @param s 执行状态
     */
    void setExcuteStatus(String s);

    /**
     * @return
     */
    Activity getActivity();

    String getCurDateStr();

    /**
     *
     */
    void updateChangedItem();

    /**
     * @param list 更新数据
     */
    void renderChangedItem(List<PatrolTaskListDTO> list);


    String getAllExecutor();
}
