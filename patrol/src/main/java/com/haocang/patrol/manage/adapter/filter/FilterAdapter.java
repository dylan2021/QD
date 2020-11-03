/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020 <br/>
 * 公司名称：上海昊沧系统控制技术有限责任公司<br/>
 * 公司地址：中国上海市徐汇区云锦路500号绿地汇中心A座20楼2001<br/>
 * 网址：http://www.haocang.com/<br/>
 * <p>
 * 标 题：
 * </p>
 * <p>
 * 文 件 名：com.haocang.max.adapter.equip.LabelAdapter.java
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
 * 创建时间：2017-1-23下午1:25:22
 * </p>
 * <p>
 * 修 改 者：
 * </p>
 * <p>
 * 修改时间：
 * </p>
 */
package com.haocang.patrol.manage.adapter.filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.haocang.base.adapter.MyBaseAdapter;
import com.haocang.patrol.R;
import com.haocang.patrol.manage.bean.filter.FilterEntity;

import java.util.List;

/**
 * 过滤的adapter.
 */
public class FilterAdapter extends MyBaseAdapter<FilterEntity> {
    /**
     * 上下文参数.
     */
    private Context ctx;

    /**
     * 构造函数.
     *
     * @param context 上下文参数
     */
    public FilterAdapter(final Context context) {
        super();
        this.ctx = context;
    }

    /**
     * 渲染View.
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(final int position,
                        View convertView, final ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(ctx)
                    .inflate(R.layout.patrol_filter_label_item, null);
            holder.labelName = convertView.findViewById(R.id.name_tv);
            holder.delete = convertView.findViewById(R.id.delete);
            holder.bgFrameLayout = convertView
                    .findViewById(R.id.bg_frameLayout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        FilterEntity entity = list.get(position);
        holder.labelName.setText(entity.getLabelName());
        if (entity.isSelect()) {
            holder.bgFrameLayout.setBackgroundResource(R.drawable.patrol_fileter2_shape);
            holder.labelName.setTextColor(ctx.getResources().getColor(R.color.patrol_font_pink));
            holder.delete.setVisibility(View.VISIBLE);
        } else {
            holder.bgFrameLayout.setBackgroundResource(R.drawable.patrol_filter_shape);
            holder.labelName.setTextColor(ctx.getResources().getColor(R.color.patrol_font_gray));
            holder.delete.setVisibility(View.GONE);
        }
        return convertView;
    }

    /**
     * 清空重置信息.
     */
    public void reset() {
        if (list != null && list.size() > 0) {
            for (FilterEntity entity : list) {
                /**
                 * 全部设置为不选中.
                 */
                entity.setSelect(false);
            }
        }
        this.notifyDataSetChanged();
    }

    public void allSelect() {
        if (list != null && list.size() > 0) {
            for (FilterEntity entity : list) {
                /**
                 * 全部设置为不选中.
                 */
                entity.setSelect(true);
            }
        }
        this.notifyDataSetChanged();
    }

    /**
     * 列表复用的Holder.
     */
    class ViewHolder {
        /**
         * 标签名称.
         */
        private TextView labelName;
        /**
         * 删除.
         */
        private View delete;
        /**
         * 背景容器.
         */
        private FrameLayout bgFrameLayout;
    }

    /**
     * @return 返回列表内容
     */
    public List<FilterEntity> getList() {
        return list;
    }

    /**
     * 获取选中项的id集合拼接字符串.
     *
     * @return 选中ID拼接字符串
     */
    public String getSelectKey() {
        String keys = "";
        if (list != null && list.size() > 0) {
            for (FilterEntity entity : list) {
                if (entity.isSelect()) {
                    keys += entity.getLabelKey() + ",";
                }
            }
        }
        if (keys.length() > 1) {
            keys = keys.substring(0, keys.length() - 1);
        }
        return keys;
    }
}
