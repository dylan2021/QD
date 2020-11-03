package com.haocang.patrol.patrolinhouse.adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.haocang.base.adapter.PictureAdapter;
import com.haocang.base.bean.PictureEntity;
import com.haocang.base.utils.ToastUtil;
import com.haocang.patrol.R;
import com.haocang.patrol.manage.bean.PatrolConstans;
import com.haocang.patrol.patrolinhouse.bean.PatrolPictureEntity;
import com.haocang.patrol.patrolinhouse.bean.PatrolTaskPointStep;
import com.haocang.patrol.patrolinhouse.iview.PatrolPointDetailListView;

import java.util.ArrayList;
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
 * 创建时间：2018/4/8下午7:06
 * 修  改  者：
 * 修改时间：
 */
public class PatrolPointDetailAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private String[] statekey = null;
    private String[] stateLabel = null;

    /**
     * 数据为空时显示
     */
    private final int TYPE_EMPTY = 0;
    /**
     * 数据为状态时显示
     */
    private final int TYPE_STATE = 1;
    /**
     * 数据为数据类型时显示
     */
    private final int TYPE_DATA = 2;

    /**
     * 免检
     */
    private final int TYPE_EXEMPTION = 3;
    private Context mContext;
    private List<PatrolTaskPointStep> mList = new ArrayList<>();

    public void clean() {
        if (mList != null && mList.size() > 0) {
            mList.clear();
        }
    }

    /**
     *
     */
    private PatrolPointDetailListView patrolPointDetailListView;

    /**
     * 选择状态为未选中
     */
    private final int UN_SELECT = -1;
    /**
     * 标记选择状态的位置，如果位置为
     */
    private int selectStatePosition = UN_SELECT;

    public void setPatrolPointDetailListView(final PatrolPointDetailListView view) {
        patrolPointDetailListView = view;
    }


    public PatrolPointDetailAdapter(final Context context) {
        mContext = context;
        statekey = mContext.getResources().getStringArray(R.array.patrol_status_step_keys);
        stateLabel = mContext.getResources().getStringArray(R.array.patrol_status_step_labels);
    }

    public void setList(final List<PatrolTaskPointStep> list) {
        mList.addAll(list);
    }

    @Override
    public int getItemViewType(final int position) {
        int type = TYPE_EMPTY;
        if (mList == null || mList.size() == 0) {
            type = TYPE_EMPTY;
        } else if (PatrolTaskPointStep.TYPE_STATE == mList.get(position).getStepResultType()) {
            type = TYPE_STATE;
        } else if (PatrolTaskPointStep.TYPE_DATA == mList.get(position).getStepResultType()) {
            type = TYPE_DATA;
        } else if (PatrolTaskPointStep.TYPE_EXEMPTION == mList.get(position).getStepResultType()) {
            type = TYPE_EXEMPTION;
        } else {
            type = super.getItemViewType(position);
        }
        return type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = null;
        if (viewType == TYPE_STATE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patrol_pointdetail_state_item, parent, false);
            return new StateViewHolder(view);
        } else if (viewType == TYPE_DATA) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patrol_pointdetail_data_item, parent, false);
            return new DataViewHolder(view);
        } else if (viewType == TYPE_EXEMPTION) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patrol_adapter_exemption_detail, parent, false);
            return new ExemptionViewHolder(view);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Log.i("holder", position + "");
        PatrolTaskPointStep item = mList.get(position);
        if (holder instanceof StateViewHolder) {
            final StateViewHolder stateViewHolder = (StateViewHolder) holder;
            stateViewHolder.stepNameTv = holder.itemView.findViewById(R.id.patrol_pointstep_name_tv);
            stateViewHolder.stepNormalTv = holder.itemView.findViewById(R.id.normal_tv);
            stateViewHolder.stepAbnormalTv = holder.itemView.findViewById(R.id.abnormal_tv);
            stateViewHolder.stepRemarkEt = holder.itemView.findViewById(R.id.patrol_pointstep_remark_et);
            stateViewHolder.stepLl = holder.itemView.findViewById(R.id.step_ll);
            stateViewHolder.cameraIv = holder.itemView.findViewById(R.id.camera_iv);
            stateViewHolder.stateItemLl = holder.itemView.findViewById(R.id.state_item_ll);
            stateViewHolder.picRecyclerview = holder.itemView.findViewById(R.id.pic_recyclerview);
            stateViewHolder.picRecyclerview.setLayoutManager(new GridLayoutManager(mContext, 3));
            PictureAdapter pictureAdapter = new PictureAdapter(mContext);
            stateViewHolder.picRecyclerview.setAdapter(pictureAdapter);
            stateViewHolder.stateItemLl.setOnClickListener(this);
            stateViewHolder.stateItemLl.setTag(position);
            stateViewHolder.cameraIv.setOnClickListener(this);
            stateViewHolder.cameraIv.setTag(position);
            stateViewHolder.stepNameTv.setText(item.getNewStepName());
            if (statekey[0].equals(item.getStepResult())) {
                stateViewHolder.stepNormalTv.setText(stateLabel[0]);
                stateViewHolder.stepNormalTv
                        .setTextColor(mContext.getResources().getColor(R.color.white));
                stateViewHolder.stepNormalTv.setBackgroundResource(R.drawable.patrol_normal_shape);

                stateViewHolder.stepAbnormalTv.
                        setTextColor(Color.parseColor("#ff3c3c3c"));
                stateViewHolder.stepAbnormalTv.setBackgroundResource(0);

            } else if (statekey[1].equals(item.getStepResult())) {
                stateViewHolder.stepAbnormalTv
                        .setTextColor(mContext.getResources().getColor(R.color.white));
                stateViewHolder.stepAbnormalTv.setText(stateLabel[1]);
                stateViewHolder.stepAbnormalTv.setBackgroundResource(R.drawable.patrol_abnormal_shape);

                stateViewHolder.stepNormalTv.
                        setTextColor(Color.parseColor("#ff3c3c3c"));
                stateViewHolder.stepNormalTv.setBackgroundResource(0);
            } else {
                stateViewHolder.stepNormalTv.setBackgroundResource(0);
                stateViewHolder.stepNormalTv.setTextColor(Color.parseColor("#ff3c3c3c"));
                stateViewHolder.stepAbnormalTv.setBackgroundResource(0);
                stateViewHolder.stepAbnormalTv.setTextColor(Color.parseColor("#ff3c3c3c"));
            }
            if (mList.get(position).isShowRemark()) {
                stateViewHolder.stepLl.setVisibility(View.VISIBLE);
            } else {
                stateViewHolder.stepLl.setVisibility(View.GONE);
            }
            if (mList.get(position).getFileList() != null && mList.get(position).getFileList().size() > 0) {
                pictureAdapter.addAll(mList.get(position).getFileList());
                pictureAdapter.notifyDataSetChanged();
            }
            if (mList.get(position).getStepImgList() != null && mList.get(position).getStepImgList().size() > 0) {
                List<PatrolPictureEntity> picList = mList.get(position).getStepImgList();
                for (PatrolPictureEntity entity : picList) {
                    if (entity.getImgUrl() != null && (entity.getImgUrl().contains("png") || entity.getImgUrl().contains("jpeg") || entity.getImgUrl().contains("jpg"))) {
                        PictureEntity pictureEntity = new PictureEntity();
                        pictureEntity.setType(0);
                        pictureEntity.setImgUrl(entity.getImgUrl());
                        pictureAdapter.addItem(pictureEntity);
                    } else {
                        PictureEntity pictureEntity = new PictureEntity();
                        pictureEntity.setType(1);
                        pictureEntity.setNetWordVideoPath(entity.getImgUrl());
                        pictureAdapter.addItem(pictureEntity);
                    }
                }
                pictureAdapter.notifyDataSetChanged();
            }
            stateViewHolder.stepNormalTv.setOnClickListener(this);
            stateViewHolder.stepNormalTv.setTag(position);
            stateViewHolder.stepAbnormalTv.setOnClickListener(this);
            stateViewHolder.stepAbnormalTv.setTag(position);
            stateViewHolder.stepRemarkEt.setText(item.getStepComment());
            stateViewHolder.stepRemarkEt.setTag(position);
            TextWatcher remarkWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

                }

                @Override
                public void onTextChanged(final CharSequence s, final int i, final int i1, final int i2) {
                    if (((int) stateViewHolder.stepRemarkEt.getTag()) == position && stateViewHolder.stepRemarkEt.hasFocus()) {
                        mList.get(position).setStepComment(s.toString());
                    }

                }

                @Override
                public void afterTextChanged(final Editable editable) {

                }
            };
            stateViewHolder.stepRemarkEt.addTextChangedListener(remarkWatcher);
        } else if (holder instanceof DataViewHolder) {
            final DataViewHolder dataViewHolder = (DataViewHolder) holder;
            dataViewHolder.stepNametv = holder.itemView.findViewById(R.id.patrol_pointstep_name_tv);
            dataViewHolder.stepValueEt = holder.itemView.findViewById(R.id.patrol_pointstep_value_et);
            dataViewHolder.stepRemarkEt = holder.itemView.findViewById(R.id.patrol_pointstep_remark_et);
            dataViewHolder.stepLl = holder.itemView.findViewById(R.id.step_ll);
            dataViewHolder.dataItemLl = holder.itemView.findViewById(R.id.detail_data_item_ll);
            dataViewHolder.picRecyclerview = holder.itemView.findViewById(R.id.pic_recyclerview);
            dataViewHolder.picRecyclerview.setLayoutManager(new GridLayoutManager(mContext, 3));
            PictureAdapter pictureAdapter = new PictureAdapter(mContext);
            dataViewHolder.cameraIv = holder.itemView.findViewById(R.id.camera_iv);
            dataViewHolder.cameraIv.setOnClickListener(this);
            dataViewHolder.cameraIv.setTag(position);
            dataViewHolder.picRecyclerview.setAdapter(pictureAdapter);
            dataViewHolder.dataItemLl.setOnClickListener(this);
            dataViewHolder.dataItemLl.setTag(position);
            dataViewHolder.stepNametv.setText(item.getNewStepName());
            dataViewHolder.stepValueEt.setText(item.getStepResult());
            dataViewHolder.stepRemarkEt.setText(item.getStepComment());
            dataViewHolder.stepValueEt.setTag(position);
            dataViewHolder.stepRemarkEt.setTag(position);
            if (mList.get(position).getFileList() != null && mList.get(position).getFileList().size() > 0) {
                pictureAdapter.addAll(mList.get(position).getFileList());
                pictureAdapter.notifyDataSetChanged();
            }
            if (mList.get(position).getStepImgList() != null && mList.get(position).getStepImgList().size() > 0) {
                List<PatrolPictureEntity> picList = mList.get(position).getStepImgList();
                for (PatrolPictureEntity entity : picList) {
                    if (entity.getImgUrl() != null && (entity.getImgUrl().contains("png") || entity.getImgUrl().contains("jpeg") || entity.getImgUrl().contains("jpg"))) {
                        PictureEntity pictureEntity = new PictureEntity();
                        pictureEntity.setType(0);
                        pictureEntity.setImgUrl(entity.getImgUrl());
                        pictureAdapter.addItem(pictureEntity);
                    } else {
                        PictureEntity pictureEntity = new PictureEntity();
                        pictureEntity.setType(1);
                        pictureEntity.setNetWordVideoPath(entity.getImgUrl());
                        pictureAdapter.addItem(pictureEntity);
                    }
                }
                pictureAdapter.notifyDataSetChanged();
            }
            if (mList.get(position).isShowRemark()) {
                dataViewHolder.stepLl.setVisibility(View.VISIBLE);
//                dataViewHolder.stepRemarkEt.setVisibility(View.VISIBLE);
            } else {
//                dataViewHolder.stepRemarkEt.setVisibility(View.GONE);
                dataViewHolder.stepLl.setVisibility(View.GONE);
            }
            dataViewHolder.stepValueEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

                }

                @Override
                public void onTextChanged(final CharSequence s, final int i, final int i1, final int i2) {
                    if (((int) dataViewHolder.stepValueEt.getTag()) == position && dataViewHolder.stepValueEt.hasFocus()) {
                        mList.get(position).setStepResult(s.toString());
                    }
                }

                @Override
                public void afterTextChanged(final Editable editable) {

                }
            });
            dataViewHolder.stepRemarkEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

                }

                @Override
                public void onTextChanged(final CharSequence s, final int i, final int i1, final int i2) {
                    if (((int) dataViewHolder.stepRemarkEt.getTag()) == position && dataViewHolder.stepRemarkEt.hasFocus()) {
                        mList.get(position).setStepComment(s.toString());
                    }
                }

                @Override
                public void afterTextChanged(final Editable editable) {

                }
            });
        } else if (holder instanceof ExemptionViewHolder) {
            final ExemptionViewHolder exemptionViewHolder = (ExemptionViewHolder) holder;
            exemptionViewHolder.stepNametv = holder.itemView.findViewById(R.id.name_tv);
            exemptionViewHolder.stepNametv.setText(item.getStepName());
        }
    }

    /**
     * 获取异常数量
     *
     * @return
     */
    public Integer getFaultCount() {
        int faultCount = 0;
        String abnormal = statekey[1];
        if (mList != null && mList.size() > 0) {
            for (PatrolTaskPointStep step : mList) {
                if (PatrolTaskPointStep.TYPE_STATE == step.getStepResultType()
                        && abnormal.equals(step.getStepResult())) {
                    faultCount++;
                }
            }
        }
        return faultCount;
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

    /**
     * @return
     */
    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    /**
     * @return
     */
    public boolean isAllComplete() {
        boolean isAllComplete = true;
        for (PatrolTaskPointStep step : mList) {
            if (TextUtils.isEmpty(step.getStepResult())) {
                isAllComplete = false;
                break;
            }
        }
        return isAllComplete;
    }

    /**
     * @param v
     */
    @Override
    public void onClick(final View v) {
        int id = v.getId();
        if (R.id.normal_tv == id) {
            normal(v);
        } else if (R.id.abnormal_tv == id) {
            selectStatePosition = (int) v.getTag();
            if (!"abnormal".equals(mList.get(selectStatePosition).getStepResult())) {
                selectStateTv = v;
                patrolPointDetailListView.postFault(0);
            } else {
                ToastUtil.makeText(mContext, "已提交缺陷报告，不可修改结果状态");
            }
        } else if (R.id.state_item_ll == id || R.id.detail_data_item_ll == id) {
            int position = (int) v.getTag();
            if (mList.get(position).isShowRemark()) {
                mList.get(position).setShowRemark(false);
            } else {
                mList.get(position).setShowRemark(true);
            }
            notifyDataSetChanged();
        } else if (R.id.camera_iv == id && onOpenCamera != null) {
            int position = (int) v.getTag();
            String stepName = mList.get(position).getStepName();
            int count = 0;
            if (mList.get(position).getFileList() != null) {
                count = mList.get(position).getFileList().size();
            }
            if (mList.get(position).getStepImgList() != null) {
                count += mList.get(position).getStepImgList().size();
            }
            if (count < 6) {
                onOpenCamera.onOpnCamera(position, stepName);
            } else {
                ToastUtil.makeText(mContext, "最多添加6张照片或视频");
            }

        }
    }

    private void normal(View v) {
        selectStatePosition = (int) v.getTag();
        if (mList != null && mList.size() > 0 && selectStatePosition < mList.size()) {
            if (!"abnormal".equals(mList.get(selectStatePosition).getStepResult())) {
                mList.get(selectStatePosition).setStepResult(statekey[0]);
                TextView stateTv = (TextView) v;
                stateTv.setText(stateLabel[0]);
                notifyDataSetChanged();
                selectStatePosition = UN_SELECT;
            } else {
                ToastUtil.makeText(mContext, "已提交缺陷报告，不可修改结果状态");
            }
        }
    }

    /**
     * @param view
     */
    private void showDialog(final View view) {
        new AlertView(null, null, mContext.getString(R.string.patrol_cancel), null,
                stateLabel, mContext, AlertView.Style.ActionSheet, new OnItemClickListener() {
            @Override
            public void onItemClick(final Object o, final int position) {
                //如果是-1说明点击的取消,点击取消时，不做任何事
                if (position == -1) {
//                mList.get(selectStatePosition).setStepResult("");
                    selectStatePosition = UN_SELECT;
                } else if (PatrolConstans.ABNORMAL.equals(statekey[position])) {
                    selectStateTv = view;
                    patrolPointDetailListView.postFault(position);
                } else {
                    mList.get(selectStatePosition).setStepResult(statekey[position]);
                    TextView stateTv = (TextView) view;
                    stateTv.setText(stateLabel[position]);
                    notifyDataSetChanged();
                    selectStatePosition = UN_SELECT;
                }
            }
        }).show();
    }


    private View selectStateTv;


    /**
     * 任务ID.
     */
    private Integer taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 获取任务ID.
     *
     * @return
     */
    private Integer getTaskId() {
        return taskId;
    }

    /**
     * 获取所有巡检步骤
     *
     * @return
     */
    public List<PatrolTaskPointStep> getPointSteps() {
        return mList;
    }

    public void setTaskId(final Integer taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(final String taskName) {
        this.taskName = taskName;
    }

    public void setAbnormalResult() {
        if (selectStatePosition >= 0 && selectStatePosition < mList.size()) {
            mList.get(selectStatePosition).setStepResult(PatrolConstans.ABNORMAL);
            TextView stateTv = (TextView) selectStateTv;
            stateTv.setText(mContext.getString(R.string.patrol_abnormal));
            selectStateTv = null;
            notifyDataSetChanged();
        }

    }

    public void setNullResult() {
        selectStateTv = null;
    }

    static class StateViewHolder extends RecyclerView.ViewHolder {
        /**
         * 步骤名称
         */
        TextView stepNameTv;
        /**
         * 正常状态
         */
        TextView stepNormalTv;

        /**
         * 异常状态
         */
        TextView stepAbnormalTv;
        /**
         * 步骤备注
         */
        EditText stepRemarkEt;

        LinearLayout stepLl;
        /**
         * 点击展开备注
         */
        LinearLayout stateItemLl;

        ImageView cameraIv;

        RecyclerView picRecyclerview;

        public StateViewHolder(final View itemView) {
            super(itemView);
        }
    }

    static class DataViewHolder extends RecyclerView.ViewHolder {

        /**
         * 步骤名称
         */
        TextView stepNametv;
        /**
         * 步骤填的值
         */
        EditText stepValueEt;
        /**
         * 步骤备注
         */
        TextView stepRemarkEt;

        LinearLayout dataItemLl;

        LinearLayout stepLl;

        ImageView cameraIv;

        RecyclerView picRecyclerview;

        public DataViewHolder(final View itemView) {
            super(itemView);
        }
    }

    static class ExemptionViewHolder extends RecyclerView.ViewHolder {

        /**
         * 步骤名称
         */
        TextView stepNametv;

        public ExemptionViewHolder(final View itemView) {
            super(itemView);
        }
    }


    public interface OnOpenCamera {
        void onOpnCamera(int position, String stepName);
    }

    private OnOpenCamera onOpenCamera;

    public void setOnOpenCamera(OnOpenCamera onOpenCamera) {
        this.onOpenCamera = onOpenCamera;
    }

}
