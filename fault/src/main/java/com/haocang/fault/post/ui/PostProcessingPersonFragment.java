package com.haocang.fault.post.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.bean.LabelEntity;
import com.haocang.base.config.AppApplication;
import com.haocang.base.config.LibConfig;
import com.haocang.base.config.LibConstants;
import com.haocang.base.iview.AllocatorView;
import com.haocang.base.iview.OnlinePersonView;
import com.haocang.base.presenter.AllocatorPresenter;
import com.haocang.base.presenter.impl.AllocatorPresenterImpl;
import com.haocang.base.utils.ToastUtil;
import com.haocang.fault.R;
import com.haocang.fault.post.adapter.PostProcessingPersonAdapter;
import com.haocang.offline.dao.SynDataManager;
import com.haocang.offline.util.OffLineOutApiUtil;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

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
 * 标 题： 选择处理人.
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/4/269:46
 * 修 改 者：
 * 修改时间：
 */

@Route(path = LibConfig.AROUTE_FAULT_POST_PROCESSING)
public class PostProcessingPersonFragment extends Fragment
        implements AllocatorView, OnlinePersonView, BaseRefreshListener,
        View.OnClickListener, BaseAdapter.OnItemClickListener, TextView.OnEditorActionListener {

    /**
     * 列表适配器.
     */
    private PostProcessingPersonAdapter mAdapter;
    /**
     * P层.
     */
    private AllocatorPresenter mPresenter;
    /**
     * 上下文参数.
     */
    private Context ctx;
    /**
     *
     */
    private RecyclerView mRecyclerView;
    /**
     * 查询输入框.
     */
    private EditText queryEt;
    /**
     *
     */
    private final boolean REFRESH = true;
    /**
     * 是否时刷新，如果是上拉和初始化时，时刷新
     */
    private boolean isRefresh = REFRESH;
    /**
     * 当前页
     */
    private int page = 1;
    /**
     *
     */
    private PullToRefreshLayout pullToRefreshLayout;


    public static final int REQUESTCODE = 3516;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_post_processing_person, null);
        ARouter.getInstance().inject(this);
        ctx = getActivity();
        initView(view);
        return view;
    }

    /**
     * 初始化界面.
     *
     * @param view 根布局
     */
    private void initView(final View view) {
        pullToRefreshLayout = view.findViewById(R.id.pulltorefreshlayout);
        mRecyclerView = view.findViewById(R.id.patrol_allocate_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new PostProcessingPersonAdapter(R.layout.adapter_post_person);
        mRecyclerView.setAdapter(mAdapter);
        queryEt = view.findViewById(R.id.patrol_query_et);
        queryEt.setOnEditorActionListener(this);
        mPresenter = new AllocatorPresenterImpl(this, this, getAuthorized());
        TextView titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getString(R.string.fault_processing_person_title));
        view.findViewById(R.id.common_complete_tv).setOnClickListener(this);
        view.findViewById(R.id.search_v).setOnClickListener(this);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!OffLineOutApiUtil.isNetWork()) {
            refresh();
        } else {
            mAdapter.clear();
            pullToRefreshLayout.finishRefresh();
            pullToRefreshLayout.finishLoadMore();
            SynDataManager synDataManager = new SynDataManager();
            mAdapter.addAll(synDataManager.getPersonList());
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void renderList(final List<LabelEntity> list) {
        if (getUserVisibleHint() && isCurrentUserExcute()) {
            long currentUserId = AppApplication.getInstance().getUserEntity().getId();
            if (list != null && list.size() > 0) {
                for (LabelEntity entity : list) {
                    if (entity.getValue() == currentUserId) {
                        list.remove(entity);
                        break;
                    }
                }
            }
        }
        if (isRefresh) {
            mAdapter.clear();
            mAdapter.addAll(list);
            pullToRefreshLayout.finishRefresh();
        } else {
            mAdapter.addAll(list);
            pullToRefreshLayout.finishLoadMore();
        }
        mPresenter.getOnLinePersonList();
    }

    @Override
    public void renderOnlinePersonList(List<LabelEntity> list) {
        for (int i = 0; i < mAdapter.mList.size(); i++) {
            long id = mAdapter.mList.get(i).getValue();
            for (int k = 0; k < list.size(); k++) {
                long signInId = list.get(k).getUserId();
                if (id == signInId) {
                    mAdapter.mList.get(i).setOrgNames("true");
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public String getQueryName() {
        return queryEt.getText().toString();
    }


    @Override
    public void refresh() {
        if (!OffLineOutApiUtil.isNetWork()) {
            page = 1;
            isRefresh = true;
            mPresenter.getAllocatorList();
        } else {
            pullToRefreshLayout.finishRefresh();
        }
    }

    @Override
    public void loadMore() {
        if (!OffLineOutApiUtil.isNetWork()) {
            page++;
            isRefresh = false;
            mPresenter.getAllocatorList();
        } else {
            pullToRefreshLayout.finishLoadMore();
        }

    }

    @Override
    public Context getContext() {
        return ctx;
    }

    public boolean isCurrentUserExcute() {
        if (getActivity() != null) {
            return getActivity().getIntent()
                    .getBooleanExtra("isCurrentUserExcute", false);
        } else {
            return false;
        }

    }

    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.common_complete_tv) {
            LabelEntity entity = mAdapter.getSelect();
            if (entity != null) {
                Intent intent = new Intent();
                intent.putExtra("name",
                        entity.getLabel());
                intent.putExtra("orgName", entity.getOrgName());

                intent.putExtra("id",
                        entity.getValue() + "");
                /**
                 * 返回修改的项的ID
                 */
                intent.putExtra("selectItemId",
                        getActivity().getIntent().getStringExtra("selectItemId"));
                /**
                 * 缺陷分派任务的时候才会有缺陷id
                 */
                intent.putExtra("faultId",
                        getActivity().getIntent().getStringExtra("faultId"));
                getActivity().setResult(LibConstants.Fault.PICKPERSON_RESULT_CODE, intent);
                getActivity().finish();
            } else {
                ToastUtil.makeText(getActivity(), "请选择处理人");
            }

        } else if (v.getId() == R.id.search_v) {
            search();
        }
    }

    private void search() {
        if (!OffLineOutApiUtil.isNetWork()) {
            page = 1;
            isRefresh = true;
            mPresenter.getAllocatorList();
        } else {
            if (!TextUtils.isEmpty(getQueryName())) {
                mAdapter.clear();
                mAdapter.addAll(getQueryList(getQueryName()));
                mAdapter.notifyDataSetChanged();
            } else {
                onResume();
            }

        }
    }


    @Override
    public Integer getOrgId() {
        int orgId = getActivity().getIntent().getIntExtra("orgId", -1);
        return orgId > 0 ? orgId : (int) AppApplication.getInstance().getUserEntity().getOrgId();
    }

    @Override
    public void onClick(final View view,
                        final int position,
                        final Object item) {
        LabelEntity entity = (LabelEntity) item;
        if (entity.isSelect()) {
            entity.setSelect(false);
        } else {
            entity.setSelect(true);
            mAdapter.clearOther(position);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLongClick(View view, int position, Object item) {

    }

    @Override
    public boolean onEditorAction(final TextView textView,
                                  final int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEND || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            refresh();
            return true;
        }
        return false;
    }


    /**
     * 搜索后的列表
     *
     * @param queryName
     * @return
     */
    public List<LabelEntity> getQueryList(String queryName) {
        List<LabelEntity> li = new ArrayList<>();
        SynDataManager synDataManager = new SynDataManager();
        for (LabelEntity entity : synDataManager.getPersonList()) {
            if (entity.getLabel().contains(queryName)) {
                li.add(entity);
            }
        }
        return li;
    }

    public boolean getAuthorized() {
        return getActivity().getIntent().getBooleanExtra("authorized", true);
    }

}
