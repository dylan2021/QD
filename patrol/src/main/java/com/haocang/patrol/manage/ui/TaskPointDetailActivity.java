package com.haocang.patrol.manage.ui;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.ui.BaseActivity;
import com.haocang.base.utils.ToastUtil;
import com.haocang.patrol.R;
import com.haocang.patrol.manage.bean.PatrolTaskListDTO;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//设备下的测点列表
public class TaskPointDetailActivity extends BaseActivity {

    private int id;
    private Intent i;
    private boolean isTypeBZ, isShowWarm;
    private TaskPointDetailActivity context;
    private PullToRefreshLayout refreshLayout;
    private TaskListAdapter adapter;
    private RecyclerView rv;
    private String title,msg;

    @Override
    protected void doOnCreate() {
        setContentView(R.layout.activity_point_detail);
        context = this;
        i = getIntent();
        id = i.getIntExtra("id", 0);
        title = i.getStringExtra("title");
        msg = i.getStringExtra("msg");
        initView();
        initTopView();
        getData();
    }

    private void initTopView() {
        String str1 = title
                + "\n描述信息_" + title
                + "\n"+msg;
        TextView tv1 = findViewById(R.id.task_detail_tv_1);
        tv1.setText(str1);
    }

    private void initView() {
        ((TextView) findViewById(R.id.title_common_tv)).setText(title + "详情");
        rv = findViewById(R.id.recyclerview);
        adapter = new TaskListAdapter(R.layout.item_task_list, 2);
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setAdapter(adapter);
        refreshLayout = findViewById(R.id.pulltorefreshlayout);
        refreshLayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                ToastUtil.makeText(context, "刷新成功");
                refreshLayout.finishRefresh();
                getData();
            }

            @Override
            public void loadMore() {
                ToastUtil.makeText(context, "已经到底了哦");
                refreshLayout.finishLoadMore();
            }
        });

        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position, Object item) {

            }

            @Override
            public void onLongClick(View view, int position, Object item) {

            }
        });
    }

    private void getData() {
        adapter.clear();
        List<PatrolTaskListDTO> list = new ArrayList<>();
        list.add(new PatrolTaskListDTO("井盖缺失", 132, "河道#34处_9#井"));
        list.add(new PatrolTaskListDTO("污水漫溢", 133, "河道#34处_2#污水厂"));
        list.add(new PatrolTaskListDTO("警示标示", 134, "河道#34处"));
       // list.add(new PatrolTaskListDTO("水质情况", 137, "河道#34处"));
        adapter.addAll(list);
        adapter.notifyDataSetChanged();

     /*   HashMap<Object, Object> map = new HashMap<>();
        CommonModel<BZ_FMJ_Bean> progressModel = new CommonModelImpl<>();
        String url = HomeUrlConst.URL_BZ_FMJ_POINT_LIST + id;
        progressModel.setContext(context)
                .setEntityType(BZ_FMJ_Bean.class)
                .setUrl(url)
                .setParamMap(map)
                .setEntityListener(new GetEntityListener<BZ_FMJ_Bean>() {
                    @Override
                    public void success(final BZ_FMJ_Bean entity) {
                        TextUtilsMy.finish(refreshLayout);

                    }

                    @Override
                    public void fail(final String err) {
                        TextUtilsMy.finish(refreshLayout);
                        ToastUtil.makeText(context, R.string.request_faild_retry);
                    }
                })
                .getEntityNew();*/
    }
}
