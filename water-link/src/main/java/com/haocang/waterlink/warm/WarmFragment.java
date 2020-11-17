package com.haocang.waterlink.warm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.utils.ToastUtil;
import com.haocang.waterlink.R;
import com.haocang.waterlink.constant.HomeUrlConst;
import com.haocang.waterlink.pump.BZ_FMJ_DeviceListActivity;
import com.haocang.waterlink.pump.BZ_FMJ_ListAdapter;
import com.haocang.waterlink.utils.TextUtilsMy;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/*
   泵站列表,阀门井列表
 */
@Route(path = "/warm/warmStatus")
public class WarmFragment extends Fragment implements View.OnClickListener, BaseRefreshListener {
    private PullToRefreshLayout refreshLayout;
    private RecyclerView equimentRv;
    private TextView titleNameTv;
    private WarmAdapter adapter;
    @Autowired
    int id;
    @Autowired
    String title;
    private HashMap<Object, Object> map = new HashMap<>();
    private FragmentActivity context;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        context = getActivity();
        ARouter.getInstance().inject(this);
        View view = inflater.from(getActivity()).inflate(R.layout.activity_bz_fmj_device_list, null);
        initView(view);

        map.put("pageSize", 10);
        map.put("currentPage", 1);
        getData();
        return view;
    }

    private void initView(View view) {
        titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(title);
        refreshLayout = view.findViewById(R.id.pulltorefreshlayout);
        refreshLayout.setRefreshListener(this);
        equimentRv = view.findViewById(R.id.recyclerview);
        adapter = new WarmAdapter(R.layout.item_warm);
        equimentRv.setLayoutManager(new LinearLayoutManager(context));
        equimentRv.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position, Object item) {
                WarmBean.ItemsBean info = (WarmBean.ItemsBean) item;
                Intent intent = new Intent(context, BZ_FMJ_DeviceListActivity.class);
                intent.putExtra("title", info.getProcessName());
                intent.putExtra("id", id);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position, Object item) {

            }
        });
    }

    @Override
    public void refresh() {
        map.put("pageSize", 10);
        getData();
    }

    @Override
    public void loadMore() {
        map.put("pageSize", Integer.valueOf(map.get("pageSize").toString()) + 10);
        getData();
    }

    private void getData() {
        CommonModel<WarmBean> progressModel = new CommonModelImpl<>();
        String url = id == 0 ? HomeUrlConst.URL_BZ : HomeUrlConst.URL_FMJ;
        progressModel.setContext(context)
                .setEntityType(WarmBean.class)
                .setUrl(url)
                .setParamMap(map)
                .setEntityListener(new GetEntityListener<WarmBean>() {
                    @Override
                    public void success(final WarmBean entity) {
                        TextUtilsMy.finish(refreshLayout);
                        Log.d("请求数据", "请求数据:" + entity.getTotal());
                        adapter.clear();
                        adapter.addAll(entity.getItems());
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void fail(final String err) {
                        TextUtilsMy.finish(refreshLayout);
                        Log.d("请求数据", "请求失败:" + err);
                        ToastUtil.makeText(context, R.string.request_faild_retry);
                    }
                })
                .getEntityNew();
    }

    @Override
    public void onClick(View v) {

    }
}
