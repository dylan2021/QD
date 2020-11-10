package com.haocang.waterlink.pump;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.ui.BaseActivity;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.utils.ToastUtil;
import com.haocang.waterlink.R;
import com.haocang.waterlink.constant.HomeUrlConst;
import com.haocang.waterlink.utils.TextUtilsMy;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.util.HashMap;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//泵站,阀门井,的设备的列表
public class BZ_FMJ_DeviceListActivity extends BaseActivity {

    private int processId;
    private Intent i;
    private boolean isTypeBZ;
    private BZ_FMJ_DeviceListActivity context;
    private PullToRefreshLayout refreshLayout;
    private BZ_FMJ_DeviceListAdapter adapter;
    private RecyclerView rv;

    @Override
    protected void doOnCreate() {
        setContentView(R.layout.activity_bz_fmj_device_list);
        context = this;
        i = getIntent();
        processId = i.getIntExtra("processId", 0);
        isTypeBZ = i.getBooleanExtra("isTypeBZ", true);
        initView();
        getData();
    }

    private void initView() {
        ((TextView) findViewById(R.id.title_common_tv)).setText(i.getStringExtra("title")+"设备");
        rv = findViewById(R.id.recyclerview);
        adapter = new BZ_FMJ_DeviceListAdapter(R.layout.item_bz_fmj_device, isTypeBZ);
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setAdapter(adapter);
        refreshLayout = findViewById(R.id.pulltorefreshlayout);
        refreshLayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                getData();
            }

            @Override
            public void loadMore() {
                ToastUtil.makeText(context,getString(R.string.no_more_data));
                TextUtilsMy.finish(refreshLayout);
            }
        });
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position, Object item) {
                BZ_FMJ_Bean.ItemsBean info = (BZ_FMJ_Bean.ItemsBean) item;
                Intent intent = new Intent(context,BZ_FMJ_PointListActivity.class);
                intent.putExtra("id",info.getId());
                intent.putExtra("title",info.name);
                intent.putExtra("isShowWarm",!info.isStatus());//是否有预警 status:true 没有
                intent.putExtra("isTypeBZ",isTypeBZ);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position, Object item) {

            }
        });

    }

    private void getData() {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("pageSize", 1000);
        map.put("currentPage", 1);
        map.put("processIds", processId);
        CommonModel<BZ_FMJ_Bean> progressModel = new CommonModelImpl<>();
        String url = HomeUrlConst.URL_BZ_FMJ_DEVICE_LIST;
        progressModel.setContext(context)
                .setEntityType(BZ_FMJ_Bean.class)
                .setUrl(url)
                .setParamMap(map)
                .setEntityListener(new GetEntityListener<BZ_FMJ_Bean>() {
                    @Override
                    public void success(final BZ_FMJ_Bean entity) {
                        TextUtilsMy.finish(refreshLayout);
                        adapter.clear();
                        adapter.addAll(entity.getItems());
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void fail(final String err) {
                        TextUtilsMy.finish(refreshLayout);
                        ToastUtil.makeText(context, R.string.request_faild_retry);
                    }
                })
                .getEntityNew();
    }
}
