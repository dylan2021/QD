package com.haocang.waterlink.pump;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.haocang.base.config.LibConfig;
import com.haocang.base.utils.ARouterUtil;
import com.haocang.waterlink.R;
import com.haocang.waterlink.home.adapter.HomeEquipmentAdapter;
import com.haocang.waterlink.pump.adapter.HomePumpAdapter;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

/*
   泵站列表,阀门井列表
 */
@Route(path = "/pump/pumplist")
public class PumpFragment extends Fragment implements View.OnClickListener, BaseRefreshListener {
    private EditText queryEdt;
    private PullToRefreshLayout pullToRefreshLayout;
    private RecyclerView equimentRv;
    private TextView titleNameTv;
    private HomePumpAdapter adapter;
    private boolean isTypeBZ = true;
    @Autowired
    String title;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        isTypeBZ = "泵站工况".equals(title);
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_home_pump, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(title);
        queryEdt = view.findViewById(R.id.patrol_query_et);
        view.findViewById(R.id.search_v).setOnClickListener(this);
        pullToRefreshLayout = view.findViewById(R.id.pulltorefreshlayout);
        pullToRefreshLayout.setRefreshListener(this);
        equimentRv = view.findViewById(R.id.recyclerview);
        adapter = new HomePumpAdapter(R.layout.adapter_home_pump);
        equimentRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        equimentRv.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        //搜索
        pullToRefreshLayout.finishLoadMore();
        pullToRefreshLayout.finishRefresh();

    }

    @Override
    public void refresh() {

    }

    @Override
    public void loadMore() {

    }
}
