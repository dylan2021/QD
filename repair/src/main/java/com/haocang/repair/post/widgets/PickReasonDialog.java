package com.haocang.repair.post.widgets;

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
import com.haocang.repair.R;
import com.haocang.repair.post.adapter.PickReasonAdapter;

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
public class PickReasonDialog extends Dialog implements BaseAdapter.OnItemClickListener, View.OnClickListener {

    private TextView stateTv;//
    private TextView hangUpTv;

    private String remarks;
    private RecyclerView recyclerView;
    private PickReasonAdapter mAdapter;
    private Context ctx;

    private TextView selectStatuTv;//选中时

    private int type;

    private int id = -1;

    private String text = "";

    public PickReasonDialog(final @NonNull Context context,
                            final String remarks,
                            final int type) {
        super(context, R.style.customDialog);
        ctx = context;
        this.remarks = remarks;
        this.type = type;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repair_dialog_reason);
        stateTv = findViewById(R.id.state_tv);
        hangUpTv = findViewById(R.id.hang_up_tv);
        findViewById(R.id.select_ll).setOnClickListener(this);
        stateTv.setText(remarks);
        hangUpTv.setText(remarks + "原因");
        recyclerView = findViewById(R.id.recyclerview);
        selectStatuTv = findViewById(R.id.select_statu_tv);
        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        mAdapter = new PickReasonAdapter(R.layout.repair_pickreason_item);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        List<LabelEntity> list = new ArrayList<>();
        if (type == 3) {
            list.addAll(closeData());
        } else if (type == 2) {
            list.addAll(hangupData());
        }

        mAdapter.addAll(list);//关闭
        mAdapter.notifyDataSetChanged();
        findViewById(R.id.submit_tv).setOnClickListener(this);
        findViewById(R.id.cancler_tv).setOnClickListener(this);
    }

    /**
     * @param view
     * @param position
     * @param item
     */
    @Override
    public void onClick(final View view, final int position, final Object item) {
        recyclerView.setVisibility(View.GONE);
        LabelEntity entity = (LabelEntity) item;
        id = (int) entity.getId();
        text = entity.getLabel();
        selectStatuTv.setText(entity.getLabel());
    }

    @Override
    public void onLongClick(final View view, final int position, final Object item) {

    }

    /**
     * @param v
     */
    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.submit_tv) {
            if (id > 0) {
                dataInterface.setSelectType(type, id, text);
                dismiss();
            } else {
                ToastUtil.makeText(ctx, "请选择" + remarks + "原因");
            }

        } else if (v.getId() == R.id.cancler_tv) {
            dismiss();
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
         * @param name 返回 id
         */
        void setSelectType(int type, int id, String name);
    }

    public OnDataInterface dataInterface;

    public void setOnDataInterface(OnDataInterface dataInterface) {
        this.dataInterface = dataInterface;

    }

    private List<LabelEntity> hangupData() {
        int[] labelId = ctx.getResources().getIntArray(R.array.repair_hangup_reason_id);
        String[] labelName = ctx.getResources().getStringArray(R.array.repair_hangup_reason);
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
        int[] labelId = ctx.getResources().getIntArray(R.array.repair_close_reason_id);
        String[] labelName = ctx.getResources().getStringArray(R.array.repair_close_reason);
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
