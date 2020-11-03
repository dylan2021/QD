package com.haocang.repair.progress.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haocang.base.adapter.PictureAdapter;
import com.haocang.base.bean.PictureEntity;
import com.haocang.base.config.ArouterPathConstants;
import com.haocang.base.utils.ARouterUtil;
import com.haocang.base.utils.StringUtils;
import com.haocang.base.utils.TimeUtil;
import com.haocang.base.widgets.MyGridLayoutManager;
import com.haocang.repair.R;
import com.haocang.repair.progress.bean.AppRepairRecordDetailVo;
import com.haocang.repair.progress.iview.RepairProgressDetailView;
import com.haocang.repair.progress.presenter.RepairProgressDetailPresenter;
import com.haocang.repair.progress.presenter.impl.RepairProgressDetailPresenterImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * 创建时间：2018/5/9上午10:19
 * 修  改  者：
 * 修改时间：
 */

/**
 *
 */
@Route(path = ArouterPathConstants.Repair.REPAIR_PROGRESS_DETAIL)
public class RepairProgressDetailFragment extends Fragment
        implements View.OnClickListener, RepairProgressDetailView {

    /**
     * 故障设备.
     */
    private TextView repairEquipmentTv;
    /**
     * 设备编号.
     */
    private TextView repairEquipmentCodeTv;
    /**
     * 维修结果.
     */
    private TextView repairResultTv;
    /**
     * 维修措施.
     */
    private TextView repairMethodTv;
//    /**
//     * 开始时间.
//     */
//    private TextView repairStartTimeTv;
    /**
     * 结束时间.
     */
    private TextView repairEndTimeTv;
    /**
     * 报障人所在组织.
     */
    private TextView repairReportPersonOrgTv;
    /**
     * 报障人.
     */
    private TextView repairReportPersonTv;

    /**
     * 暂无现场记录图片.
     */
    private TextView faultNodateTv;
    /**
     *
     */
    private PictureAdapter pictureAdapter;
    /**
     * 列表视图.
     */
    private RecyclerView recyclerview;

    /**
     * 界面交互类
     */
    private RepairProgressDetailPresenter mPresenter;

    /**
     *
     */
    private String remark;

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.repair_progress_detail_fragment, null);
        initView(view);
        return view;
    }

    /**
     * @param view 根view.
     */
    private void initView(final View view) {
        TextView titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getString(R.string.repair_processing_results));
        repairEquipmentTv = view.findViewById(R.id.repair_equipment_tv);
        repairEquipmentCodeTv = view
                .findViewById(R.id.repair_equipment_code_tv);
        repairResultTv = view.findViewById(R.id.repair_result_tv);
        repairMethodTv = view.findViewById(R.id.repair_method_tv);
        repairEndTimeTv = view.findViewById(R.id.repair_end_time_tv);
        repairReportPersonOrgTv = view
                .findViewById(R.id.repair_report_person_org_tv);
        repairReportPersonTv = view.findViewById(R.id.repair_report_person_tv);
        recyclerview = view.findViewById(R.id.recyclerview);
        faultNodateTv = view.findViewById(R.id.no_pic_tv);
        pictureAdapter = new PictureAdapter(getActivity());
        recyclerview.setLayoutManager(
                new MyGridLayoutManager(getActivity(), 3));
        recyclerview.setAdapter(pictureAdapter);
        pictureAdapter.setDisplay();
        view.findViewById(R.id.repair_look_repair_analysis_ll)
                .setOnClickListener(this);
        mPresenter = new RepairProgressDetailPresenterImpl();
        mPresenter.setView(this);
        mPresenter.getProgressDetail();
    }

    /**
     * @param view
     */
    @Override
    public void onClick(final View view) {
        int id = view.getId();
        if (id == R.id.repair_look_repair_analysis_ll) {
            Map<String, Object> map = new HashMap<>();
            map.put("fragmentUri", ArouterPathConstants.Repair.REPAIR_LOOK_REMARK);
            map.put("title", getString(R.string.repair_analysis));
            map.put("label", getString(R.string.repair_look_repair_analysis));
            if (!TextUtils.isEmpty(remark)) {
                map.put("remark", remark);
            }
            ARouterUtil.toFragment(map);
        }
    }

    /**
     * @return
     */
    @Override
    public String getRepairRecordId() {
        return getActivity().getIntent().getStringExtra("repairRecordId");
    }

    @Override
    public void setDetailData(final AppRepairRecordDetailVo entity) {
//        RepairRecordVo recordVo = entity.getRepairRecordVo();
//        RepairVo repairVo = entity.getRepairVo();
        if (entity != null) {
            repairEquipmentTv.setText(entity.getEquName());
            repairEquipmentCodeTv.setText(entity.getEquCode());
            repairReportPersonOrgTv.setText(entity.getCreateUserName());
            repairReportPersonTv.setText(entity.getProcessingPersonName());
            repairResultTv.setText(entity.getRepairResultName());
            repairMethodTv.setText(entity.getRepairAdoptname());
            String finishDate = TimeUtil
                    .getDayStr(TimeUtil.getDateTimeWithoutSpace(entity.getFinishDate()));
            repairEndTimeTv.setText(finishDate);
            remark = entity.getFaultAnalysis();
            setUrlList(entity.getPictureVideos());
        }

    }

    @Override
    public String getRepairContrailId() {
        return null;
    }

    private void setUrlList(List<PictureEntity> list) {
        pictureAdapter.addAll(list);
        pictureAdapter.notifyDataSetChanged();
//        if (imgUrlArr != null && imgUrlArr.length > 0) {
//            new AsyncTask<Void, Void, String>() {
//                private RequestBuilder<Drawable> builder = null;
//
//                @Override
//                protected String doInBackground(final Void... voids) {
//                    for (String imgUrl : imgUrlArr) {
//                        addItem(imgUrl);
//                    }
//                    return null;
//                }
//
//                protected void onPostExecute(final String s) {
//                    pictureAdapter.notifyDataSetChanged();
//                }
//            }.execute();
//            for (String imgUrl : imgUrlArr) {
//                addItem(imgUrl);
//            }
//            pictureAdapter.notifyDataSetChanged();
    }


    private void addItem(final String path) {
        PictureEntity entity = new PictureEntity();
        if (StringUtils.isPicture(path)) {
            entity.setLocalImgPath(path);
            entity.setType(0);
            pictureAdapter.addItemWithoutNotifyList(entity);
        } else if (path.contains(".mp4")) {
            entity.setNetWordVideoPath(path);
            entity.setType(1);
            pictureAdapter.addItemWithoutNotifyList(entity);
        }

    }
}
