package com.haocang.patrol.patrolinhouse.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.BaseHolder;
import com.haocang.patrol.R;
import com.haocang.patrol.patrolinhouse.bean.PatrolTaskPointStep;

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
 * 创 建 者：hy
 * 创建时间：${DATA} 14:38
 * 修 改 者：
 * 修改时间：
 */
public class PatrolExemptionAdapter extends BaseAdapter<PatrolTaskPointStep> {

    public PatrolExemptionAdapter(int layoutId, Context mContext) {
        super(layoutId);
    }

    @Override
    protected void convert(BaseHolder holder, final PatrolTaskPointStep item, int position) {
        holder.setText(R.id.name_tv, item.getStepName());
        if (item.isSelect()) {
            holder.setImageResource(R.id.select_iv, R.drawable.icon_patrol_exemption_t);
        } else {
            holder.setImageResource(R.id.select_iv, R.drawable.icon_patrol_exemption_f);
        }

    }

    public void allSelect(boolean isSelect) {
        for (PatrolTaskPointStep item : mList) {
            if ("abnormal".equals(item.getStepResult())) {
                continue;
            }
            if (isSelect) {
                item.setStepResult("/");
            } else {
                item.setStepResult(item.getLastStepResult());
            }
            item.setSelect(isSelect);
        }
        notifyDataSetChanged();
    }

    /**
     * 获取有巡检结果的步骤数
     *
     * @return
     */
    public Integer getHasResultCount() {
        int hasResultCount = 0;
        for (PatrolTaskPointStep step : mList) {
            if (!TextUtils.isEmpty(step.getStepResult())) {
                hasResultCount++;
            }
        }
        return hasResultCount;
    }


    public Integer getFaultCount() {
        int faultCount = 0;
        String abnormal = "abnormal";
        for (PatrolTaskPointStep step : mList) {
            if (PatrolTaskPointStep.TYPE_STATE == step.getStepResultType()
                    && abnormal.equals(step.getStepResult())) {
                faultCount++;
            }
        }
        return faultCount;
    }

    /**
     * 获取所有巡检步骤
     *
     * @return
     */
    public List<PatrolTaskPointStep> getPointSteps() {
        return mList;
    }

}
