package com.haocang.waterlink.experiment;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.utils.ARouterUtil;
import com.haocang.maonlib.base.config.HCLicConstant;
import com.haocang.waterlink.R;
import com.haocang.waterlink.experiment.adapter.HomeExperimentAdapter;
import com.haocang.waterlink.experiment.bean.ExperimentListBean;
import com.haocang.waterlink.experiment.presenter.ExperimentPresenter;
import com.haocang.waterlink.experiment.presenter.ExperimentPresenterImpl;
import com.haocang.waterlink.home.bean.MenuEntity;
import com.haocang.waterlink.pump.adapter.HomePumpAdapter;
import com.haocang.waterlink.utils.HomeJumpUtil;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.util.HashMap;
import java.util.Map;


@Route(path = "/experiment/experiment")
public class ExperimentFragment extends Fragment implements View.OnClickListener, BaseRefreshListener,TextView.OnEditorActionListener{

    private EditText queryEdt;
    private PullToRefreshLayout pullToRefreshLayout;
    private RecyclerView equimentRv;
    private TextView titleNameTv;
    private HomeExperimentAdapter adapter;
    private ExperimentPresenter presenter;
    private Map<String,Object> mMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.from(getActivity()).inflate(R.layout.fragment_experiment, null);
        initView(view);
        return view;

    }

    private void initView(View view) {

        titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText("人工实验抄录");
        queryEdt = view.findViewById(R.id.query_et);
        view.findViewById(R.id.search_v).setOnClickListener(this);
        pullToRefreshLayout = view.findViewById(R.id.pulltorefreshlayout);
        pullToRefreshLayout.setRefreshListener(this);
        equimentRv = view.findViewById(R.id.recyclerview);
        adapter = new HomeExperimentAdapter(R.layout.adapter_home_experiment);
        presenter = new ExperimentPresenterImpl();
        presenter.setView(this);
        equimentRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        equimentRv.setAdapter(adapter);
        queryEdt.setOnEditorActionListener(this);
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position, Object item) {
                ExperimentListBean.ItemsBean bean = (ExperimentListBean.ItemsBean)item;
//                startActivity(new Intent(Experiment));
//                MenuEntity entity = (MenuEntity) o;
                Map<String, Object> map = new HashMap<>();
                map.put("fragmentUri", "/experiment/experiment/detail");
                map.put("formId", bean.getId());
                map.put("cycleId", bean.getCycleId());
//                map.put("formId", bean.getId());
//                HCLicConstant.JUMP_STATIC = "1";
                ARouterUtil.toFragment(map);
            }

            @Override
            public void onLongClick(View view, int position, Object item) {

            }
        });

        mMap = new HashMap<>();
//        type=hour&siteId=&queryName=&beginDate=&endDate=&pageSize=10&currentPage=1&siteName=&cycleId=
        mMap.put("type","hour");
        mMap.put("pageSize","10");
        mMap.put("currentPage",1);
        presenter.getDataList(mMap);
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
            mMap.put("queryName",queryEdt.getText().toString().trim());
//            adapter.clear();
            refresh();
            return true;
        }
        return false;
    }

    public void setData(ExperimentListBean entity){
        adapter.addAll(entity.getItems());
        adapter.notifyDataSetChanged();
        pullToRefreshLayout.finishLoadMore();
        pullToRefreshLayout.finishRefresh();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void refresh() {
        adapter.clear();
        mMap.put("currentPage",1);
        presenter.getDataList(mMap);
    }

    @Override
    public void loadMore() {
        mMap.put("currentPage",Integer.valueOf(mMap.get("currentPage").toString())+1);
        presenter.getDataList(mMap);
    }
}