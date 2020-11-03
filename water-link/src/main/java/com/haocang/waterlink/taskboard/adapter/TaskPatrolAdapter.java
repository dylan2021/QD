package com.haocang.waterlink.taskboard.adapter;

import android.text.TextUtils;

import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.BaseHolder;
import com.haocang.base.utils.TimeTransformUtil;
import com.haocang.waterlink.R;
import com.haocang.waterlink.home.bean.TaskEntity;

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
 * 创 建 者：hy
 * 创建时间：${DATA} 14:28
 * 修 改 者：
 * 修改时间：
 */
public class TaskPatrolAdapter extends BaseAdapter<TaskEntity> {

    public TaskPatrolAdapter(int layoutId) {
        super(layoutId);
    }

    @Override
    protected void convert(BaseHolder holder, TaskEntity item, int position) {
        holder.setText(R.id.patrol_name_tv, item.getName());
        holder.setText(R.id.end_time_tv, substringTime(item.getEndTime()));
        holder.setText(R.id.start_time_tv, substringTime(item.getStartTime()));
        holder.setText(R.id.patrol_time_tv, item.getShowTime().substring(0, item.getShowTime().lastIndexOf(" ")));
    }

    private String substringTime(String time) {
        String srTime = "";
        if (!TextUtils.isEmpty(time)) {
            time = TimeTransformUtil.getShowLocalTime2(time);
            srTime = time.substring(time.lastIndexOf(" "), time.length());
        }
        return srTime;
    }

}
