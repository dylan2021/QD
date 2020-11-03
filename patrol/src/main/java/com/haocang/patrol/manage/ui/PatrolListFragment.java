package com.haocang.patrol.manage.ui;

import android.content.Context;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haocang.base.config.ArouterPathConstants;
import com.haocang.base.utils.SpaceItemDecoration;
import com.haocang.base.utils.TimeUtil;
import com.haocang.base.utils.ToastUtil;
import com.haocang.offline.util.OffLineOutApiUtil;
import com.haocang.patrol.R;
import com.haocang.patrol.manage.adapter.PatrolAdapter;
import com.haocang.patrol.manage.bean.PatrolConditionEvent;
import com.haocang.patrol.manage.bean.PatrolTaskListDTO;
import com.haocang.patrol.manage.iview.PatrolListView;
import com.haocang.patrol.manage.presenter.PatrolListPresenter;
import com.haocang.patrol.manage.presenter.impl.PatrolListPresenterImpl;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 任务列表界面
 * Created by william on 2018/4/2.
 */
@Route(path = ArouterPathConstants.Patrol.PATROL_LIST)
public class PatrolListFragment extends Fragment
        implements BaseRefreshListener, PatrolListView,
        View.OnClickListener, TextView.OnEditorActionListener {
    /**
     *
     */
    private PatrolAdapter mAdapter;
    /**
     *
     */
    private PatrolListPresenter mPresenter;

    /**
     *
     */
    private Context ctx;
    /**
     *
     */
    private RecyclerView mRecyclerView;
    /**
     *
     */
    private PullToRefreshLayout pullToRefreshLayout;
    /**
     * 查询输入框
     */
    private EditText queryEt;

    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime = TimeUtil.getDateTimeStr(getDate(new Date()));
    /**
     * 执行状态
     */
    private String excuteStatus;
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

    private String curDateStr;

    private String allExecutor;
    /**
     * 没有数据的时候显示暂无数据图片.
     */
    private FrameLayout noDataFl;

    /**
     * 执行人，是否选中所有人，true为选中，false为不选中所有人
     */
    private boolean allPerson;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.patrol_fragment, null);
        ctx = getActivity();
        initView(view);
        return view;
    }

    /**
     * @param view view
     */
    private void initView(final View view) {
        queryEt = view.findViewById(R.id.query_et);
        queryEt.setHint("任务名称");
        pullToRefreshLayout = view.findViewById(R.id.pulltorefreshlayout);
        noDataFl = view.findViewById(R.id.no_data_fl);
        TextView titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getResources().getString(R.string.patrol_manage));
        ImageView commonIv = view.findViewById(R.id.common_iv);
        if (OffLineOutApiUtil.isOffLine) {
            commonIv.setVisibility(View.INVISIBLE);
        } else {
            commonIv.setVisibility(View.VISIBLE);
        }
        commonIv.setBackgroundResource(R.mipmap.list_filter);
        commonIv.setOnClickListener(this);
        mPresenter = new PatrolListPresenterImpl(this);
        mRecyclerView = view.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new PatrolAdapter(R.layout.patrol_item, ctx);
        mRecyclerView.setAdapter(mAdapter);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.patrol_list_space);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        pullToRefreshLayout.setRefreshListener(this);
        queryEt.setOnEditorActionListener(this);
        view.findViewById(R.id.delete_ibtn).setOnClickListener(this);
        mPresenter.setDefalutCondition();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (OffLineOutApiUtil.isOffLine()) {
            isRefresh = true;
            page = 1;
            mPresenter.getOffLinePatrolList();
        } else if (page == 1) {
            getPatrolList(REFRESH);
        }
    }

    /**
     *
     */
    @Override
    public void updateChangedItem() {
        int changedItemPosition = mAdapter.getChangedItemPosition();
        if (changedItemPosition != -1) {
            mPresenter.getItem(changedItemPosition + 1);
//            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void renderChangedItem(final List<PatrolTaskListDTO> list) {
        if (list != null && list.size() > 0) {
            mAdapter.setChangedItem(list.get(0));
        }
    }

    @Override
    public String getAllExecutor() {
        return allExecutor;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }

    /**
     * 获取列表.
     *
     * @param refresh 是否是刷新
     */
    public void getPatrolList(final boolean refresh) {
        isRefresh = refresh;
        mPresenter.getPatrolList();
        if (isRefresh == REFRESH) {
            page = 1;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSelectConditionEvent(final PatrolConditionEvent event) {
        this.startTime = event.getStartTime();
        this.endTime = event.getEndTime();
        this.excuteStatus = event.getExcuteStatus();
        this.curDateStr = event.getCurDateStr();
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(final int requestCode,
                                 final int resultCode,
                                 final Intent data) {
        if ((requestCode == PatrolFilterActivity.REQUEST
                || resultCode == PatrolFilterActivity.REQUEST) && data != null) {
            this.startTime = data.getStringExtra("startTime");
            this.endTime = data.getStringExtra("endTime");
            this.excuteStatus = data.getStringExtra("excuteStates");
            this.curDateStr = data.getStringExtra("curDateStr");
            this.allExecutor = data.getStringExtra("allExecutor");
            isRefresh = true;
            mPresenter.getPatrolList();
        }
    }

    @Override
    public void renderList(final List<PatrolTaskListDTO> patrolList) {
        if (isRefresh) {
            mAdapter.clear();
            pullToRefreshLayout.finishRefresh();
        } else {
            pullToRefreshLayout.finishLoadMore();
        }

        if (patrolList != null && patrolList.size() > 0) {
            mAdapter.addAll(patrolList);
            mAdapter.notifyDataSetChanged();
        } else if (!isRefresh) {
            ToastUtil.makeText(getActivity(), getString(R.string.equiment_all_data));
            backToLastPage();
        }
        if (mAdapter.getItemCount() == 0) {
            noDataFl.setVisibility(View.VISIBLE);
        } else {
            noDataFl.setVisibility(View.GONE);
        }

        if (isRefresh) {
            mAdapter.replaceAll(patrolList);
            pullToRefreshLayout.finishRefresh();
        } else {
            mAdapter.addAll(patrolList);
            pullToRefreshLayout.finishLoadMore();
        }
        mAdapter.notifyListDataSetChanged();
    }

    @Override
    public void offLineRenderList(List<PatrolTaskListDTO> patrolList) {
        pullToRefreshLayout.finishRefresh();
        pullToRefreshLayout.finishLoadMore();
        mAdapter.clear();
        if (patrolList != null && patrolList.size() > 0) {
            mAdapter.addAll(patrolList);
            mAdapter.notifyDataSetChanged();
            noDataFl.setVisibility(View.GONE);
        } else {
            noDataFl.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 如果下一页无数据，则页数变回原来的页数.
     */
    private void backToLastPage() {
        if (page > 1) {
            page--;
        }
    }

    @Override
    public void onClick(final View v) {
        int i = v.getId();
        if (i == R.id.common_iv) {
            mPresenter.showFilterView();
        }else if(i==R.id.delete_ibtn){
            queryEt.setText("");
        }
    }

    @Override
    public void refresh() {
        if (OffLineOutApiUtil.isOffLine()) {
            onResume();
        } else {
            page = 1;
            getPatrolList(REFRESH);
        }

    }

    @Override
    public void loadMore() {
        if (OffLineOutApiUtil.isOffLine()) {
            isRefresh = true;
            onResume();
        } else {
            page++;
            getPatrolList(!REFRESH);
        }

    }

    @Override
    public void renderError(final Exception e) {

    }


    @Override
    public String getStartTime() {
        return startTime;
    }

    @Override
    public String getEndTime() {
        return endTime;
    }

    @Override
    public String getExcuteStatus() {
        return excuteStatus;
    }

    @Override
    public String getQueryName() {
        return queryEt.getText().toString();
    }

    @Override
    public boolean isRefresh() {
        return isRefresh;
    }

    @Override
    public int getPage() {
        return page;
    }

    /**
     * @return
     */
    @Override
    public Context getContext() {
        return ctx;
    }

    /**
     * @param startTime 开始时间
     */
    @Override
    public void setStartTime(final String startTime) {
        this.startTime = startTime;
    }

    /**
     * @param endTime 结束时间
     */
    @Override
    public void setEndTime(final String endTime) {
        this.endTime = endTime;
    }

    /**
     * @param s 执行状态
     */
    @Override
    public void setExcuteStatus(final String s) {
        this.excuteStatus = s;
    }

    @Override
    public String getCurDateStr() {
        return curDateStr;
    }

    /**
     * @param textView
     * @param actionId
     * @param event
     * @return
     */
    @Override
    public boolean onEditorAction(final TextView textView, final int actionId, final KeyEvent event) {
//        if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
//            getPatrolList(REFRESH);
//            return true;
//        }
        if (textView.getId() == R.id.query_et) {
            if (OffLineOutApiUtil.isOffLine()) {
                onResume();
            } else {
                getPatrolList(REFRESH);
            }
        }
        return false;
    }

    private Date getDate(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return ca.getTime();
    }
}
