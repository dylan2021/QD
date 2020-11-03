package com.haocang.fault.list.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.bean.LabelEntity;
import com.haocang.base.utils.ToastUtil;
import com.haocang.fault.R;
import com.haocang.fault.post.adapter.PostProcessingPersonAdapter;

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
 * 创建时间：2018/5/1013:26
 * 修 改 者：
 * 修改时间：
 */
public class ResultsReasonDialog extends Dialog implements BaseAdapter.OnItemClickListener, View.OnClickListener {

    private TextView stateTv;//
    private TextView hangUpTv;

    private String remarks;
    private RecyclerView recyclerView;
    private PostProcessingPersonAdapter mAdapter;
    private Context ctx;

    private TextView selectStatuTv;//选中时

    private int type;

    private int id = -1;

    public ResultsReasonDialog(@NonNull Context context, String remarks, int type) {
        super(context, R.style.customDialog);
        ctx = context;
        this.remarks = remarks;
        this.type = type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_results_reason);
        stateTv = findViewById(R.id.state_tv);
        hangUpTv = findViewById(R.id.hang_up_tv);
        findViewById(R.id.select_ll).setOnClickListener(this);
        stateTv.setText(remarks);
        hangUpTv.setText(remarks + "原因");
        recyclerView = findViewById(R.id.recyclerview);
        selectStatuTv = findViewById(R.id.select_statu_tv);
        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        mAdapter = new PostProcessingPersonAdapter(R.layout.adapter_post_processing2);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        List<LabelEntity> list = new ArrayList<>();
        if (type == 3) {
            list.addAll(closeData());
        } else if (type == 2) {
            list.addAll(hookData());
        }
        mAdapter.addAll(list);//关闭
        mAdapter.notifyDataSetChanged();
        findViewById(R.id.submit_tv).setOnClickListener(this);
        findViewById(R.id.cancler_tv).setOnClickListener(this);
        findViewById(R.id.item_ll).setOnClickListener(this);
    }

    @Override
    public void onClick(View view, int position, Object item) {
        recyclerView.setVisibility(View.GONE);
        LabelEntity entity = (LabelEntity) item;
        id = (int) entity.getId();
        selectStatuTv.setText(entity.getLabel());
    }

    @Override
    public void onLongClick(View view, int position, Object item) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submit_tv) {
            if (id > 0) {
                dataInterface.setSelectType(type, id);
                dismiss();
            } else {
                ToastUtil.makeText(ctx, "请选择" + remarks + "原因");
            }

        } else if (v.getId() == R.id.cancler_tv) {
            dismiss();
        } else if (v.getId() == R.id.item_ll) {
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    public static void setDialogWindowAttr(Dialog dlg, Context ctx) {
        Window window = dlg.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;//宽高可设置具体大小
        dlg.getWindow().setAttributes(lp);
    }


    public interface OnDataInterface {

        /**
         * @param type 区分 是挂起 还是关闭
         * @param id   返回 id
         */
        void setSelectType(int type, int id);

    }

    public OnDataInterface dataInterface;

    public void setOnDataInterface(OnDataInterface dataInterface) {
        this.dataInterface = dataInterface;

    }

    private List<LabelEntity> hookData() {
        int[] labelId = ctx.getResources().getIntArray(R.array.processing_result_hook_id);
        String[] labelName = ctx.getResources().getStringArray(R.array.processing_result_hook);
        List<LabelEntity> list = new ArrayList<>();
        for (int i = 0; i < labelName.length && i < labelId.length; i++) {
            LabelEntity labelEntity = new LabelEntity();
            labelEntity.setId(labelId[i]);
            labelEntity.setLabel(labelName[i]);
            list.add(labelEntity);
        }
        return list;
    }

    private List<LabelEntity> closeData() {
        int[] labelId = ctx.getResources().getIntArray(R.array.processing_result_close_id);
        String[] labelName = ctx.getResources().getStringArray(R.array.processing_result_close);
        List<LabelEntity> list = new ArrayList<>();
        for (int i = 0; i < labelName.length && i < labelId.length; i++) {
            LabelEntity labelEntity = new LabelEntity();
            labelEntity.setId(labelId[i]);
            labelEntity.setLabel(labelName[i]);
            list.add(labelEntity);
        }
        return list;
    }
}
