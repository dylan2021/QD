package com.haocang.base.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.haocang.base.R;

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
 * 创建时间：2018/3/3017:49
 * 修 改 者：
 * 修改时间：
 */
public class ProgressBarDialog extends Dialog {

    public ProgressBarDialog(Context context) {
        super(context, R.style.customDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_progressbar);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress);
        ThreeBounce doubleBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
    }

    //RotatingPlane,
    // DoubleBounce,W
    // ave,WanderingCubes
    // ,Pulse,ChasingDots,
    // ThreeBounce，
    // Circle，
    // CubeGrid，
    // FadingCircle，
    // FoldingCube，
    // RotatingCircle
}
