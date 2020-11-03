/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.<br/>
 * 版权所有(C)2015-2020 <br/>
 * 公司名称：上海昊沧系统控制技术有限责任公司<br/>
 * 公司地址：中国上海市徐汇区云锦路500号绿地汇中心A座20楼2001<br/>
 * 网址：http://www.haocang.com/<br/>
 * <p>
 * 标 题：
 * </p>
 * <p>
 * 文 件 名：com.haocang.max.widgets.equipment.LabelView.java
 * </p>
 * <p>
 * 部 门：产品研发部
 * <p>
 * 版 本： 1.0
 * </p>
 * <p>
 * 创 建 者：he
 * </p>
 * <p>
 * 创建时间：2017-1-23上午11:02:29
 * </p>
 * <p>
 * 修 改 者：
 * </p>
 * <p>
 * 修改时间：
 * </p>
 */
package com.haocang.patrol.manage.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haocang.patrol.R;
import com.haocang.patrol.manage.adapter.filter.FilterAdapter;
import com.haocang.patrol.manage.bean.filter.FilterEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LabelView extends LinearLayout implements OnClickListener {
    private Context ctx;
    private TextView classNameTv;
    private GridView gridView;
    private FilterAdapter adapter;
    private LinearLayout openLinear;
    private boolean isOpen = false;
    private Map<String, String> map = new HashMap<String, String>(); // 选中的条件
    private ConditionInterface condition;

    public LabelView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void initView(View view) {
        openLinear = (LinearLayout) view.findViewById(R.id.open_linear);
        classNameTv = (TextView) view.findViewById(R.id.class_name_tv);
        gridView = (GridView) view.findViewById(R.id.gridView);
        adapter = new FilterAdapter(ctx);
        gridView.setAdapter(adapter);
        openLinear.setOnClickListener(this);
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                if (condition != null) {
                    condition.OnItemClick();
                }
                FilterEntity entity = (FilterEntity) adapter.getItem(position);
                if (entity.isSelect()) {
                    entity.setSelect(false);
                    // map.remove(position + "");
                } else {
                    entity.setSelect(true);
                    // map.put("" + position, entity.getAid());
                }
                setList();
                adapter.notifyDataSetChanged();
            }
        });
    }

    public List<FilterEntity> getList() {
        return adapter.getList();
    }

    public String getSelectKeys() {
        return adapter.getSelectKey();
    }

    private void setList() {
        for (int i = 0; i < adapter.getCount(); i++) {
            FilterEntity entity = (FilterEntity) adapter.getItem(i);
            if (entity.isSelect()) {
                map.put("" + i, entity.getLabelId() + "");
            } else {
                for (int k = 0; k < map.size(); k++) {
                    if (map.containsKey("" + i)) {
                        map.remove("" + i);
                    }
                }
            }
        }
        condition.condition(map);
    }

    public void setData(final List<FilterEntity> list, Context ctx, String type) {
        this.ctx = ctx;
        View view = LayoutInflater.from(ctx).inflate(R.layout.patrol_filter_label, null);
        initView(view);
        this.addView(view);
        classNameTv.setText(type);
        adapter.clear();
        adapter.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.open_linear) {
        }
//        switch (v.getId()) {
//            case R.id.open_linear:
//
//                break;
//            default:
//                break;
//        }
    }


    /**
     * 描述：重置数据
     */
    public void initData() {
        try {
            if (adapter.getCount() > 0) {
                for (int i = 0; i < adapter.getCount(); i++) {
                    FilterEntity entity = (FilterEntity) adapter.getItem(i);
                    entity.setSelect(false);
                }
                map.clear();
                condition.condition(map);
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setConditionInterface(final ConditionInterface conditionInterface) {
        this.condition = conditionInterface;

    }

    public void reset() {
        adapter.reset();
    }

    public void allSelect() {
        adapter.allSelect();
    }


    /**
     * <p>
     * 描 述：把选择的条件传递出去
     * </p>
     * <p>
     * 创 建 人：he
     * </p>
     * <p>
     * 创建时间：2017-2-7下午1:21:52
     * </p>
     */
    public interface ConditionInterface {
        void condition(Map<String, String> map);

        void OnItemClick();
    }

}
