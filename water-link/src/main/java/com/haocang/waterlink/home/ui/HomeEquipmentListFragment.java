package com.haocang.waterlink.home.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.bean.EquimentEntity;
import com.haocang.base.config.LibConfig;
import com.haocang.base.http.OkHttpClientManager;
import com.haocang.base.ui.CommonActivity;
import com.haocang.base.utils.AnalysisQRCodeUtil;
import com.haocang.base.utils.GsonUtil;
import com.haocang.base.utils.StringUtils;
import com.haocang.base.utils.ToastUtil;
import com.haocang.equiment.details.ui.EquipmentDetailsFragment;
import com.haocang.waterlink.R;
import com.haocang.waterlink.home.adapter.HomeEquipmentAdapter;
import com.haocang.waterlink.home.iview.HomeEquipmentView;
import com.haocang.waterlink.home.presenter.HomeEquipmentPresenter;
import com.haocang.waterlink.home.presenter.impl.HomeEquipmentPresenterImpl;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

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
 * 创建时间：2018/5/21 16:54
 * 修 改 者：
 * 修改时间：
 */
@Route(path = LibConfig.AROUTE_EQUIPMENT_LIST_NEW)
public class HomeEquipmentListFragment extends Fragment
        implements View.OnClickListener, BaseRefreshListener, HomeEquipmentView, OkHttpClientManager.OnNetworkResponse {
    private EditText queryEdt;
    private PullToRefreshLayout pullToRefreshLayout;
    private RecyclerView equimentRv;
    private HomeEquipmentAdapter adapter;
    private TextView titleNameTv;
    private int page = 1;
    private HomeEquipmentPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_home_equipment, null);
        initView(view);
        return view;
    }

    /**
     * @param view 根View
     */
    private void initView(final View view) {
        presenter = new HomeEquipmentPresenterImpl(this);
        titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText("设备列表");
        queryEdt = view.findViewById(R.id.patrol_query_et);
        view.findViewById(R.id.search_v).setOnClickListener(this);
        pullToRefreshLayout = view.findViewById(R.id.pulltorefreshlayout);
        pullToRefreshLayout.setRefreshListener(this);
        equimentRv = view.findViewById(R.id.recyclerview);
        adapter = new HomeEquipmentAdapter(R.layout.adapter_home_equipment);
        equimentRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        equimentRv.setAdapter(adapter);
        addParameter();
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(final View view,
                                final int position,
                                final Object item) {
                EquimentEntity entity = (EquimentEntity) item;
                Intent intent = new Intent(getActivity(), CommonActivity.class);
                intent.putExtra("fragmentName", EquipmentDetailsFragment.class.getName());
                intent.putExtra("id", entity.getId() + "");
                startActivity(intent);
            }

            @Override
            public void onLongClick(final View view, final int position, final Object item) {

            }
        });
    }

    /**
     * @return
     */
    private String getType() {
        return getActivity().getIntent().getStringExtra("type");
    }

    private String getIds() {
        return getActivity().getIntent().getStringExtra("id");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.search_v) {
            page = 1;
            addParameter();
        }
    }

    @Override
    public void refresh() {
        adapter.clear();
        page = 1;
        addParameter();
    }

    @Override
    public void loadMore() {
        page++;
        addParameter();
    }

    private void addParameter() {
        presenter.getEquimentList();
//        Map<String, Object> map = new HashMap<>();
//        if (AnalysisQRCodeUtil.TYPE_PATROL.equals(getType())) {
//            //巡检点
//            map.put("patrolPointId", getIds());
//        } else {
//            //工艺位置id
//            map.put("processIds", getIds());
//        }
//        map.put("queryName", StringUtils.utfCode(queryEdt.getText().toString().trim()));
//        map.put("currentPage", page);
//        map.put("pageSize", "10");
//        getEquimentListData(getActivity(), map);
    }

//    public void getEquimentListData(Context ctx, Map<String, Object> map) {
//        AddParameters addParameters = new AddParameters();
//        addParameters.addParam(map);
//        new OkHttpClientManager()
//                .setOnNetWorkReponse(this)
//                .setLoadDialog(new ProgressBarDialog(ctx))
//                .setUrl(MethodConstants.Equiment.EQUIMENT_LIST + addParameters.formGet())
//                .setRequestMethod(LibConfig.HTTP_GET)
//                .builder();
//    }

    @Override
    public void onNetworkResponse(String result) {
        try {
            JSONObject object = new JSONObject(result);
            String json = object.optString("items");
            List<EquimentEntity> list = GsonUtil.stringToList(json, EquimentEntity.class);
            adapter.addAll(list);
            adapter.notifyDataSetChanged();
            pullToRefreshLayout.finishLoadMore();
            pullToRefreshLayout.finishRefresh();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onErrorResponse(Response response) {
        if (AnalysisQRCodeUtil.TYPE_PATROL.equals(getType())) {
            //巡检点
            ToastUtil.makeText(getActivity(), "当前巡检点无权限，请联系管理员");
        } else {
            //工艺位置id
            ToastUtil.makeText(getActivity(), "当前区域位置无权限，请联系管理员");
        }

        pullToRefreshLayout.finishLoadMore();
        pullToRefreshLayout.finishRefresh();
    }

    @Override
    public Map<String, Object> getParamMap() {
        Map<String, Object> map = new HashMap<>();
        if (AnalysisQRCodeUtil.TYPE_PATROL.equals(getType())) {
            //巡检点
            map.put("patrolPointId", getIds());
        } else {
            //工艺位置id
            map.put("processIds", getIds());
        }
        map.put("queryName", StringUtils.utfCode(queryEdt.getText().toString().trim()));
        map.put("currentPage", page);
        map.put("pageSize", "10");
        return map;
    }

    @Override
    public void render(List<EquimentEntity> list) {
        adapter.addAll(list);
        adapter.notifyDataSetChanged();
        pullToRefreshLayout.finishLoadMore();
        pullToRefreshLayout.finishRefresh();
    }
}
