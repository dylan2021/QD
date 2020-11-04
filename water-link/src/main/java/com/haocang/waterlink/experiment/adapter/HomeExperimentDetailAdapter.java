package com.haocang.waterlink.experiment.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.BaseHolder;
import com.haocang.waterlink.R;
import com.haocang.waterlink.experiment.bean.ExperimentDetailBean;
import com.haocang.waterlink.experiment.bean.ExperimentListBean;
import com.haocang.waterlink.utils.TextUtilsMy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区龙兰路277号一号楼9楼
 * 网址：http://www.haocang.com/
 * 标 题：
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：hy
 * 创建时间：2018/5/22 10:00
 * 修 改 者：
 * 修改时间：
 */
public class HomeExperimentDetailAdapter extends BaseAdapter<ExperimentDetailBean.MpointsBean> {


    public HomeExperimentDetailAdapter(final int layoutId) {
        super(layoutId);
    }

    public void setFootView(View footView) {
        this.footView = footView;
    }

    @Override
    protected void convert(final BaseHolder holder, final ExperimentDetailBean.MpointsBean item) {
        if (item == null) {
            return;
        }
        String mpointName = item.mpointName;
        Date date = new Date();
        String falseTime = item.getFalseTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = format.parse(falseTime.replace("T", " ").replace("Z", ""));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, +8);

        holder.setText(R.id.point_name_tv, mpointName).setVisible(!TextUtilsMy.isEmpty(mpointName));
        holder.setText(R.id.time, TextUtilsMy.subTimeMDHm(format.format(calendar.getTime())))
                .setText(R.id.value, item.getValue());
        EditText et = holder.itemView.findViewById(R.id.value);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                item.setValue(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//        String val = et.getText().toString().trim();
//        Log.e("item.getValue()",item.getValue());
//        holder.setText(R.id.name,item.getFormName())
//                .setText(R.id.area,item.getSiteName())
//                .setText(R.id.professional,"业务时间:"+item.getFormLatestdate().replace("T"," ").replace("Z",""))
//                .setText(R.id.cycle,item.getCycleName())
//                .setText(R.id.cycle_time,"录入时间:"+item.getUpdateTime().replace("T"," ").replace("Z",""));
//        holder.setText(R.id.patrol_allocator_tv, item.getName());
//        holder.setText(R.id.patrol_allocate_group_tv, item.getProcessName());
    }

    @Override
    protected void convert(BaseHolder holder) {
        super.convert(holder);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    public void clear() {
        if (mList != null) {
            mList.clear();
        }
        notifyDataSetChanged();
    }
}