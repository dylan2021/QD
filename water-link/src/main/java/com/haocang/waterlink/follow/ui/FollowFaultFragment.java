package com.haocang.waterlink.follow.ui;

import android.content.Context;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.ui.CommonActivity;
import com.haocang.base.utils.ConcernUtil;
import com.haocang.base.utils.ToastUtil;
import com.haocang.fault.list.ui.FaultDetailsFragment;
import com.haocang.waterlink.R;
import com.haocang.waterlink.follow.adapter.FollowFaultAdapter;
import com.haocang.waterlink.self.bean.FolloContentEntity;
import com.haocang.waterlink.self.iview.FollowView;
import com.haocang.waterlink.self.presenter.FollowPresenter;
import com.haocang.waterlink.self.presenter.impl.FollowPresenterImpl;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.util.List;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题： 缺陷工单
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：hy
 * 创建时间：${DATA} 16:11
 * 修 改 者：
 * 修改时间：
 */
public class FollowFaultFragment extends Fragment implements FollowView, BaseRefreshListener {
    private RecyclerView recyclerView;
    private FollowFaultAdapter adapter;
    private FollowPresenter presenter;
    private PullToRefreshLayout pullToRefreshLayout;
    private int page = 1;
    private boolean isRefresh = true;//区分是刷新还是加载更多
    private FrameLayout frameLayout;
    private LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_all_follow, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        radioIv.setVisibility(View.VISIBLE);
        TextView noDataTv = view.findViewById(R.id.noData_tv);
        noDataTv.setText("暂无缺陷");
        frameLayout = view.findViewById(R.id.no_data_fl);
        recyclerView = view.findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new FollowFaultAdapter(R.layout.adapter_follow_fault, getActivity());
        recyclerView.setAdapter(adapter);
        pullToRefreshLayout = view.findViewById(R.id.pulltorefreshlayout);
        pullToRefreshLayout.setRefreshListener(this);
        presenter = new FollowPresenterImpl(this);
        adapter.setConcernInterface(new ConcernUtil.AddConcernInterface() {
            @Override
            public void concernSucess() {

            }

            @Override
            public void concernError() {

            }

            @Override
            public void abolishConcern() {
                refresh();
            }
        });
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int i, Object o) {
                FolloContentEntity entity = (FolloContentEntity) o;
                Intent intent = new Intent(getActivity(), CommonActivity.class);
                intent.putExtra("fragmentName", FaultDetailsFragment.class.getName());
//                intent.putExtra("fragmentName", "FaultDetailsFragment");
                intent.putExtra("faultId", entity.getItemId() + "");
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int i, Object o) {

            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //-1代表顶部,返回true表示没到顶,还可以滑
                //1代表底部,返回true表示没到底部,还可以滑
                if (getUserVisibleHint()) {
                    boolean b = recyclerView.canScrollVertically(-1);
                    if (b && getScollYDistance() > 100) {
                        radioIv.setVisibility(View.GONE);
                    } else if (!b) {
                        radioIv.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    int lastScollY = 0;

    @Override
    public Context getContexts() {
        return getActivity();
    }

    @Override
    public Integer getCurrentPage() {
        return page;
    }

    @Override
    public void setFollowListData(List<FolloContentEntity> list) {
        if (isRefresh) {
            adapter.clear();
            pullToRefreshLayout.finishRefresh();
        } else {
            pullToRefreshLayout.finishLoadMore();
        }
        if (list != null && list.size() > 0) {
            adapter.addAll(list);
        } else if (!isRefresh) {
            ToastUtil.makeText(getActivity(), getString(R.string.equiment_all_data));
        }
        if (adapter.getItemCount() == 0) {
            frameLayout.setVisibility(View.VISIBLE);
        } else {
            frameLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public String getType() {
        return "fault";
    }

    @Override
    public Integer getIndex() {
        return 0;
    }

    @Override
    public void refresh() {
        page = 1;
        isRefresh = true;
        presenter.getFollowList();
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        page++;
        presenter.getFollowList();
    }

    private ImageView radioIv;

    public void setImageView(ImageView radioIv) {
        this.radioIv = radioIv;
    }

    private int getScollYDistance() {
        int position = linearLayoutManager.findFirstVisibleItemPosition();
        View firstVisiableChildView = linearLayoutManager.findViewByPosition(position);
        int itemHeight = 0;
        if (firstVisiableChildView != null) {
            itemHeight = firstVisiableChildView.getHeight();
            return (position) * itemHeight - firstVisiableChildView.getTop();
        }
        return itemHeight;

    }

    @Override
    public void showDeleteItemDialog(int deleteItemCount) {
        AlertView.Builder builder = new AlertView.Builder();
        builder.setCancelText("确认");
        builder.setMessage( "当前有" + deleteItemCount + "条关注数据已被删除");
        builder.setContext(getActivity());
        builder.setTitle("提示");
        builder.setStyle(AlertView.Style.Alert );
        new AlertView(builder).show();

    }
}
