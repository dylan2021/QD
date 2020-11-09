package com.haocang.waterlink.pump;

import android.content.Intent;
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
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.haocang.waterlink.utils.TextUtilsMy;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.util.HashMap;

/*
   泵站列表,阀门井列表
 */
@Route(path = "/pump/pumplist")
public class BZ_FMJ_Fragment extends Fragment implements View.OnClickListener, BaseRefreshListener {
    private EditText queryEdt;
    private PullToRefreshLayout refreshLayout;
    private RecyclerView equimentRv;
    private TextView titleNameTv;
    private BZ_FMJ_Adapter adapter;
    private boolean isTypeBZ = true;
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
        isTypeBZ = "泵站工况".equals(title);
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_home_pump, null);
        initView(view);

        map.put("pageSize", 10);
        map.put("currentPage", 1);
        getData();
        return view;
    }

    private void initView(View view) {
        titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(title);
        queryEdt = view.findViewById(R.id.patrol_query_et);
        view.findViewById(R.id.search_v).setOnClickListener(this);
        refreshLayout = view.findViewById(R.id.pulltorefreshlayout);
        refreshLayout.setRefreshListener(this);
        equimentRv = view.findViewById(R.id.recyclerview);
        adapter = new BZ_FMJ_Adapter(R.layout.item_bz_fmj, isTypeBZ);
        equimentRv.setLayoutManager(new LinearLayoutManager(context));
        equimentRv.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position, Object item) {
                BZ_FMJ_ListBean.ItemsBean info = (BZ_FMJ_ListBean.ItemsBean) item;
                Intent intent = new Intent(context,BZ_FMJ_DeviceListActivity.class);
                intent.putExtra("processId",info.getProcessId());
                intent.putExtra("title",info.getProcessName());
                intent.putExtra("isTypeBZ",isTypeBZ);
                startActivity(intent);

            }

            @Override
            public void onLongClick(View view, int position, Object item) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        //搜索
    }

    @Override
    public void refresh() {
        map.put("pageSize", 10);
        getData();
    }

    private void getData() {
        CommonModel<BZ_FMJ_ListBean> progressModel = new CommonModelImpl<>();
        String url = isTypeBZ ? HomeUrlConst.URL_BZ : HomeUrlConst.URL_FMJ;
        progressModel.setContext(context)
                .setEntityType(BZ_FMJ_ListBean.class)
                .setUrl(url)
                .setParamMap(map)
                .setEntityListener(new GetEntityListener<BZ_FMJ_ListBean>() {
                    @Override
                    public void success(final BZ_FMJ_ListBean entity) {
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
    public void loadMore() {
        map.put("pageSize", Integer.valueOf(map.get("pageSize").toString()) +10);
        getData();
    }
}
