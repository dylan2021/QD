package com.haocang.patrol.patrolinhouse.adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.haocang.base.adapter.BaseRecyclerAdapter;
import com.haocang.base.adapter.PictureAdapter;
import com.haocang.base.adapter.SmartViewHolder;
import com.haocang.base.bean.PictureInfo;
import com.haocang.patrol.R;
import com.haocang.patrol.patrolinhouse.bean.PatrolPictureEntity;
import com.haocang.patrol.patrolinhouse.bean.PatrolTaskPointStep;

import java.util.Collection;
import java.util.List;

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
 * 创建时间：2018/4/9下午6:32
 * 修  改  者：
 * 修改时间：
 */
public class PatrolPointDetailScanAdapter extends BaseRecyclerAdapter<PatrolTaskPointStep> {
    private Context mContext;

    public PatrolPointDetailScanAdapter(final Collection<PatrolTaskPointStep> collection, final int layoutId) {
        super(collection, layoutId);
    }

    public PatrolPointDetailScanAdapter(final @LayoutRes int layoutId, final Context ctx) {
        super(layoutId);
        mContext = ctx;
    }

    @Override
    protected void onBindViewHolder(final SmartViewHolder holder, final PatrolTaskPointStep item, final int position) {
        holder.text(R.id.patrol_pointstep_name_tv, mList.get(position).getStepName());
        TextView remarkTv = holder.itemView.findViewById(R.id.patrol_pointstep_remark_tv);
        TextView valueTv = holder.itemView.findViewById(R.id.patrol_pointstep_value_tv);
        RecyclerView recyclerView=holder.itemView.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,3));
        PictureAdapter pictureAdapter =new PictureAdapter(mContext);
        pictureAdapter.setDisplay();
        recyclerView.setAdapter(pictureAdapter);
        if (mList.get(position).getStepImgList() != null && mList.get(position).getStepImgList().size() > 0) {
            List<PatrolPictureEntity> picList = mList.get(position).getStepImgList();
            for (PatrolPictureEntity entity : picList) {
                if (entity.getImgUrl() != null && (entity.getImgUrl().contains("png") || entity.getImgUrl().contains("jpeg") || entity.getImgUrl().contains("jpg"))) {
                    PictureInfo pictureInfo = new PictureInfo();
                    pictureInfo.setType(0);
                    pictureInfo.setImgUrl(entity.getImgUrl());
                    pictureAdapter.addItem(pictureInfo);
                } else {
                    PictureInfo pictureInfo = new PictureInfo();
                    pictureInfo.setType(1);
                    pictureInfo.setNetWordVideoPath(entity.getImgUrl());
                    pictureAdapter.addItem(pictureInfo);
                }
            }
            pictureAdapter.notifyDataSetChanged();
        }
        valueTv.setText(getShowResult(item));
        valueTv.setTextColor(Color.parseColor(getResultTextColor(item)));
        String stepComment = item.getStepComment();
        if (TextUtils.isEmpty(stepComment)) {
            remarkTv.setVisibility(View.GONE);
        } else {
            remarkTv.setVisibility(View.VISIBLE);
        }
        holder.text(R.id.patrol_pointstep_remark_tv, stepComment);
    }

    private String getShowResult(final PatrolTaskPointStep item) {
        String showResult = item.getStepResult();
        if (TextUtils.isEmpty(showResult)) {
            return "未检查";
        }
        if ("/".equals(showResult)) {
            return "免检";
        }
        if (item.getStepResultType() == PatrolTaskPointStep.TYPE_STATE) {
            if (TextUtils.isEmpty(item.getStepResult())) {
                showResult = "";
            } else if (PatrolTaskPointStep.NORMAL.equals(item.getStepResult())) {
                showResult = mContext.getString(R.string.patrol_normal);
            } else {
                showResult = mContext.getString(R.string.patrol_abnormal);
            }
        }
        return showResult;
    }

    private String getResultTextColor(final PatrolTaskPointStep item) {
        String showResult = item.getStepResult();
        if (TextUtils.isEmpty(showResult)) {
            return "#ff9b9b9b";
        }
        if ("/".equals(showResult)) {
            return "#ff89d11c";//免检
        }
        if (item.getStepResultType() == PatrolTaskPointStep.TYPE_STATE) {
            if (PatrolTaskPointStep.NORMAL.equals(item.getStepResult())) {
                return "#ff0cabdf";//正常
            } else {
                return "#fff94837";//异常
            }
        }
        return "#ff0cabdf";
    }

}
