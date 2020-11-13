package com.haocang.patrol.manage.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haocang.base.config.ArouterPathConstants;
import com.haocang.base.utils.SpaceItemDecoration;
import com.haocang.base.utils.TimeUtil;
import com.haocang.base.utils.ToastUtil;
import com.haocang.patrol.R;
import com.haocang.patrol.manage.adapter.PatrolAdapter;
import com.haocang.patrol.manage.bean.PatrolTaskListDTO;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import org.feezu.liuli.timeselector.Utils.TextUtil;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 任务列表界面
 * Created by william on 2018/4/2.
 */
@Route(path = ArouterPathConstants.Patrol.PATROL_LIST)
public class PatrolListFragment extends Fragment
        implements BaseRefreshListener,
        View.OnClickListener {
    private PatrolAdapter mAdapter;
    private Context context;
    private RecyclerView mRecyclerView;
    private PullToRefreshLayout pullToRefreshLayout;
    /**
     * 开始时间
     */
    private String startTime;
    private String excuteStatus;
    private final boolean REFRESH = true;
    private boolean isRefresh = REFRESH;
    private int page = 1;
    private String curDateStr;
    private String allExecutor;
    private FrameLayout noDataFl;
    private boolean allPerson;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.patrol_fragment, null);
        context = getActivity();
        initView(view);
        getData();
        return view;
    }

    /**
     * @param view view
     */
    private void initView(final View view) {
        pullToRefreshLayout = view.findViewById(R.id.pulltorefreshlayout);
        noDataFl = view.findViewById(R.id.no_data_fl);
        TextView titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getResources().getString(R.string.patrol_manage));
        TextView commonIv = view.findViewById(R.id.common_tv);
        commonIv.setText("巡检任务");
        commonIv.setOnClickListener(this);
        mRecyclerView = view.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new PatrolAdapter(R.layout.patrol_item, context);
        mRecyclerView.setAdapter(mAdapter);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.patrol_list_space);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        pullToRefreshLayout.setRefreshListener(this);
    }

    public void getData() {
        mAdapter.clear();
        List<PatrolTaskListDTO> list = new ArrayList<>();
        list.add(new PatrolTaskListDTO("2020-11-20日度巡检", 10905437, "李军"));
        list.add(new PatrolTaskListDTO("2020-11-21日度巡检", 10905438,
                "张子涛"));
        mAdapter.addAll(list);
        pullToRefreshLayout.finishRefresh();
        pullToRefreshLayout.finishLoadMore();
    }

    @Override
    public void onClick(final View v) {
        int i = v.getId();
        if (i == R.id.common_tv) {
            //todo 进入巡检任务列表

        }
    }

    @Override
    public void refresh() {
        getData();
        ToastUtil.makeText(context,"刷新成功");
    }

    @Override
    public void loadMore() {
        getData();
        ToastUtil.makeText(context,"已经到底了哦");
    }

    /**
     * @return
     */
    @Override
    public Context getContext() {
        return context;
    }
}
