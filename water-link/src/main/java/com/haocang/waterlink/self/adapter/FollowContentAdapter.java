package com.haocang.waterlink.self.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.haocang.base.utils.ConcernUtil;
import com.haocang.waterlink.R;
import com.haocang.waterlink.self.bean.FolloContentEntity;

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
 * 创建时间：2018/3/2113:19
 * 修 改 者：
 * 修改时间：
 */
public class FollowContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final static int EQUIMENT = 0;//设备台账
    public final static int PATROL = 1;//巡检任务
    public final static int FAULT = 2;//缺陷申报
    public final static int REPAIR = 3;//维修工单
    public final static int MAINTAIN = 4;//养护工单
    private Context ctx;
    private GetNotifyDataSetChanged notifyDataSetChanged;
    public OnItemClickListener onItemClickListener;


    public FollowContentAdapter(Context ctx) {
        this.ctx = ctx;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent,
                                                      final int viewType) {
        if (viewType == EQUIMENT) {
            View view = LayoutInflater.from(ctx).inflate(R.layout.adapter_follow_equiment, parent, false);
            return new EquipHolder(view);
        } else if (viewType == PATROL) {
            View view = LayoutInflater.from(ctx).inflate(R.layout.adapter_follow_route, parent, false);
            return new PatrolHolder(view);
        } else if (viewType == FAULT) {
            View view = LayoutInflater.from(ctx).inflate(R.layout.adapter_follow_defect, parent, false);
            return new FaultHolder(view);
        } else if (viewType == REPAIR) {
            View view = LayoutInflater.from(ctx).inflate(R.layout.adapter_follow_repair, parent, false);
            return new RepairtHolder(view);
        } else if (viewType == MAINTAIN) {
            View view = LayoutInflater.from(ctx).inflate(R.layout.adapter_follow_curing, parent, false);
            return new MaintainHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof EquipHolder) {
            setEquipment((EquipHolder) holder, position);
        } else if (holder instanceof PatrolHolder) {
            setPatro((PatrolHolder) holder, position);
        } else if (holder instanceof FaultHolder) {
            setFault((FaultHolder) holder, position);
        } else if (holder instanceof RepairtHolder) {
            setRepair((RepairtHolder) holder, position);
        } else if (holder instanceof MaintainHolder) {

        }
    }

    private void setRepair(final RepairtHolder holder, final int position) {
        final FolloContentEntity entity = mList.get(position);
        holder.personTv.setText(entity.getExecutorName());
        holder.statuTv.setText(entity.getStatus());
        holder.repairNameTv.setText(entity.getName());
        holder.repairCodeTv.setText(entity.getNo());
        holder.canclerFollowTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptDialog(ConcernUtil.REPAIR_TYPE, entity.getItemId());
            }
        });
        setOnItemClicke(entity, holder.itemView);
    }

    /**
     * 提示
     */
    private void promptDialog(final String type, final int id) {
        new AlertView("提示", "是否确定取消关注？", "取消",
                new String[]{"确定"}, null, ctx, AlertView.Style.Alert, new com.bigkoo.alertview.OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                if (position >= 0) {
                    canclerFollow(type, id);
                }
            }
        }).show();
    }

    private void setEquipment(final EquipHolder equipHolder, int position) {
        final FolloContentEntity entity = mList.get(position);
        equipHolder.nameTv.setText(entity.getName());
        equipHolder.equipCodeTv.setText(entity.getNo());
        equipHolder.equimentStatuTv.setText(entity.getStatus());
        equipHolder.equipPosttion.setText(entity.getProcess());
        Glide.with(ctx).load(entity.getImgUrl()).apply(options).into(equipHolder.equipImgIv);
        equipHolder.canclerFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptDialog(ConcernUtil.EQUIPMENT_TYPE, entity.getItemId());
            }
        });
        setOnItemClicke(entity, equipHolder.itemView);
    }


    private void setPatro(final PatrolHolder patrolHolder, final int position) {
        final FolloContentEntity entity = mList.get(position);
        patrolHolder.patroNameTv.setText(entity.getName());
        patrolHolder.patroStatuTv.setText(entity.getStatus());
        patrolHolder.patroTimeTv.setText(entity.getDateTime());
        patrolHolder.transactorTv.setText(entity.getExecutorName());
        patrolHolder.canclerFollowTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptDialog(ConcernUtil.PATROL_TYPE, entity.getItemId());
            }
        });
        setOnItemClicke(entity, patrolHolder.itemView);

    }

    private void setFault(final FaultHolder faultHolder, final int position) {
        final FolloContentEntity entity = mList.get(position);
        faultHolder.faultStatuTv.setText(entity.getStatus());
        faultHolder.faultLevelTv.setText(entity.getSeverity());
        faultHolder.faultNameTv.setText(entity.getName());
        faultHolder.faultCodeTv.setText(entity.getNo());
        if (!TextUtils.isEmpty(entity.getImgUrl())) {
            String[] imgSr = entity.getImgUrl().split(",");
            Glide.with(ctx).load(imgSr[0]).apply(options).into(faultHolder.faultIv);
        } else {
            Glide.with(ctx).load(R.drawable.ic_picture_default).apply(options).into(faultHolder.faultIv);
        }

        faultHolder.faultCancleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptDialog(ConcernUtil.FAULT_TYPE, entity.getItemId());
            }
        });
        setOnItemClicke(entity, faultHolder.itemView);
    }

    private void setOnItemClicke(final FolloContentEntity entity,
                                 final View itemView) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClickListener(entity);
                }
            }
        });
    }

    private RequestOptions options = new RequestOptions()
            .placeholder(com.haocang.equiment.R.drawable.ic_picture_default)// 正在加载中的图片
            .error(com.haocang.equiment.R.drawable.ic_picture_default) // 加载失败的图片
            .diskCacheStrategy(DiskCacheStrategy.ALL); // 磁盘缓存策略

    private void canclerFollow(final String type, final int id) {
        new ConcernUtil()
                .setConcernType(type)
                .setContexts(ctx)
                .setConcernId(id)
                .setConcernSuccess(addConcernInterface)
                .abolishConcern();
    }

    private ConcernUtil.AddConcernInterface addConcernInterface
            = new ConcernUtil.AddConcernInterface() {
        @Override
        public void concernSucess() {

        }

        @Override
        public void concernError() {

        }

        @Override
        public void abolishConcern() {
            notifyDataSetChanged.notifyData();
        }
    };

    @Override
    public int getItemViewType(final int position) {
        int type = (int) mList.get(position).getType();
        if (type == EQUIMENT) {
            return EQUIMENT;
        } else if (type == PATROL) {
            return PATROL;
        } else if (type == FAULT) {
            return FAULT;
        } else if (type == REPAIR) {
            return REPAIR;
        } else {
            return MAINTAIN;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    /**
     * 设备台账
     */
    public class EquipHolder extends RecyclerView.ViewHolder {

        private TextView nameTv;
        private TextView equipCodeTv;
        private TextView equimentStatuTv;
        private TextView equipPosttion;
        private TextView canclerFollow;
        private ImageView equipImgIv;

        private EquipHolder(View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.equiment_name_tv);
            equipCodeTv = itemView.findViewById(R.id.equiment_code_tv);
            equimentStatuTv = itemView.findViewById(R.id.equiment_statu_tv);
            equipPosttion = itemView.findViewById(R.id.equiment_position_tv);
            canclerFollow = itemView.findViewById(R.id.cancler_follow_tv);
            equipImgIv = itemView.findViewById(R.id.equipment_iv);
        }
    }

    /**
     * 巡检
     */
    public class PatrolHolder extends RecyclerView.ViewHolder {
        private TextView patroNameTv;
        private TextView patroStatuTv;
        private TextView patroTimeTv;
        private TextView transactorTv;
        private TextView canclerFollowTv;

        private PatrolHolder(View itemView) {
            super(itemView);
            patroNameTv = itemView.findViewById(R.id.route_task_name);
            patroStatuTv = itemView.findViewById(R.id.statu_tv);
            patroTimeTv = itemView.findViewById(R.id.route_time_tv);
            transactorTv = itemView.findViewById(R.id.transactor_tv);
            canclerFollowTv = itemView.findViewById(R.id.cancler_follow_tv);
        }

    }


    /**
     * 缺陷
     */
    public class FaultHolder extends RecyclerView.ViewHolder {

        private ImageView faultIv;
        private TextView faultNameTv;
        private TextView faultCodeTv;
        private TextView faultStatuTv;
        private TextView faultLevelTv;
        private TextView faultCancleTv;

        private FaultHolder(final View itemView) {
            super(itemView);
            faultIv = itemView.findViewById(R.id.fault_iv);
            faultNameTv = itemView.findViewById(R.id.defect_name_tv);
            faultCodeTv = itemView.findViewById(R.id.defect_code_tv);
            faultStatuTv = itemView.findViewById(R.id.defect_statu_tv);
            faultLevelTv = itemView.findViewById(R.id.fault_level_tv);
            faultCancleTv = itemView.findViewById(R.id.cancler_follow_tv);
        }

    }

    /**
     * 维修
     */
    public class RepairtHolder extends RecyclerView.ViewHolder {

        private TextView repairNameTv;

        private TextView repairCodeTv;

        private TextView processTv;

        private TextView equipmentCodeTv;

        private TextView statuTv;

        private TextView personTv;//处理人

        private TextView canclerFollowTv;//取消关注

        private RepairtHolder(final View itemView) {
            super(itemView);
            repairNameTv = itemView.findViewById(R.id.repair_tv);
            statuTv = itemView.findViewById(R.id.statu_tv);
            repairCodeTv = itemView.findViewById(R.id.repair_code_tv);
            personTv = itemView.findViewById(R.id.persont_tv);
            canclerFollowTv = itemView.findViewById(R.id.cancler_follow_tv);
        }
    }

    /**
     * 保养
     */
    public class MaintainHolder extends RecyclerView.ViewHolder {

        public MaintainHolder(final View itemView) {
            super(itemView);
        }
    }


    private List<FolloContentEntity> mList = new ArrayList<>();

    public void add(final FolloContentEntity entity) {
        mList.add(entity);
        notifyDataSetChanged();
    }

    public void addAll(final List<FolloContentEntity> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }


    public interface GetNotifyDataSetChanged {
        void notifyData();
    }


    public void GetNotifyDataSetChanged(final GetNotifyDataSetChanged notifyDataSetChanged) {
        this.notifyDataSetChanged = notifyDataSetChanged;
    }


    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(FolloContentEntity entity);
    }
}
