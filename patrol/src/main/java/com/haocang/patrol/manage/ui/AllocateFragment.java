package com.haocang.patrol.manage.ui;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.haocang.base.config.LibConfig;
import com.haocang.patrol.R;
import com.haocang.patrol.manage.adapter.AllocateAdapter;
import com.haocang.patrol.manage.bean.UserDTO;
import com.haocang.patrol.manage.iview.AllocateView;
import com.haocang.patrol.manage.presenter.PatrolAllocatePresenter;
import com.haocang.patrol.manage.presenter.impl.PatrolAllocatePresenterImpl;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.util.List;

/**
 * 任务分派界面
 * Created by william on 2018/4/2.
 */
@Route(path = LibConfig.AROUTE_PATROL)
public class AllocateFragment extends Fragment
        implements AllocateView, BaseRefreshListener, View.OnClickListener, TextView.OnEditorActionListener {

    /**
     * 列表适配器.
     */
    private AllocateAdapter mAdapter;
    /**
     * P层.
     */
    private PatrolAllocatePresenter mPresenter;
    /**
     *
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
     * 刷新常量.
     */
    private final boolean REFRESH = true;
    /**
     * 是否时刷新，如果是上拉和初始化时，时刷新.
     */
    private boolean isRefresh = REFRESH;
    /**
     * 当前页.
     */
    private int page = 1;
    /**
     *
     */
    private PullToRefreshLayout pullToRefreshLayout;

//    private PatrolTaskListDTO mPatroltask;


    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.from(getActivity())
                .inflate(R.layout.patrol_allocate_fragment, null);
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
        mAdapter = new AllocateAdapter(R.layout.patrol_allocate_item);
        mRecyclerView.setAdapter(mAdapter);
        queryEt = view.findViewById(R.id.patrol_query_et);
        mPresenter = new PatrolAllocatePresenterImpl();
        mPresenter.setAllocateView(this);
        mPresenter.getAllocatorList();
        pullToRefreshLayout.setRefreshListener(this);
        TextView titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getText(R.string.patrol_allote_title));
        queryEt.setOnEditorActionListener(this);
        view.findViewById(R.id.common_complete_tv).setOnClickListener(this);
    }

    /**
     * 渲染布局.
     *
     * @param list
     */
    @Override
    public void renderList(final List<UserDTO> list) {
        mAdapter.replaceAll(list);
        if (isRefresh) {
            pullToRefreshLayout.finishRefresh();
        } else {
            pullToRefreshLayout.finishLoadMore();
        }
        mAdapter.notifyListDataSetChanged();
    }

    /**
     * 获取模糊查询的值.
     *
     * @return
     */
    @Override
    public String getQueryName() {
        return queryEt.getText().toString();
    }

    /**
     * 是否刷新.
     *
     * @return
     */
    @Override
    public boolean isRefresh() {
        return isRefresh;
    }

    /**
     * 获取当前页.
     *
     * @return
     */
    @Override
    public Integer getPage() {
        return page;
    }

    /**
     * 获取执行人ID.
     *
     * @return
     */
    @Override
    public Integer getExecutorId() {
        return mAdapter.getSelectId();
    }

    /**
     * 获取任务ID.
     *
     * @return
     */
    @Override
    public int getTaskId() {
        return getActivity().getIntent().getIntExtra("taskId", -1);
    }

    /**
     * 刷新.
     */
    @Override
    public void refresh() {
        page = 1;
        isRefresh = true;
        mPresenter.getAllocatorList();
    }

    /**
     * 分页加载，加载更多.
     */
    @Override
    public void loadMore() {
        page = 1;
        isRefresh = false;
        mPresenter.getAllocatorList();
    }

    /**
     * 获取当前上下文参数.
     *
     * @return
     */
    @Override
    public Context getContext() {
        return ctx;
    }

    /**
     * 分配成功后返回.
     *
     * @param result
     */
    @Override
    public void success(final String result) {
//        if (GetEntityListener.SUCCESS.equals(result)) {
//            ToastUtil.makeText(ctx,
//                    ctx.getString(R.string.patrol_allocate_success));
//            getActivity().finish();
//        } else {
//            ToastUtil.makeText(ctx,
//                    ctx.getString(R.string.patrol_allocate_fail));
//        }
        getActivity().finish();
    }

    /**
     * 单击事件监听.
     *
     * @param v
     */
    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.common_complete_tv) {
            mPresenter.allocatePatrolTask();
        }
    }

    /**
     * 获取组织ID.
     *
     * @return
     */
    @Override
    public Integer getOrgId() {
        int orgId = getActivity().getIntent().getIntExtra("orgId", -1);
        return orgId;
    }

    /**
     *
     * @param textView
     * @param actionId
     * @param keyEvent
     * @return
     */
    @Override
    public boolean onEditorAction(final TextView textView,
                                  final int actionId,
                                  final KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEND
                || (keyEvent != null
                && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            refresh();
            return true;
        }
        return false;
    }
}
