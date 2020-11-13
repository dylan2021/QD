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
public class TaskDetailPointListActivity extends BaseActivity {

    private int id;
    private Intent i;
    private boolean isTypeBZ, isShowWarm;
    private TaskDetailPointListActivity context;
    private PullToRefreshLayout refreshLayout;
    private TaskListAdapter adapter;
    private RecyclerView rv;
    private String title;
    private String taskName;
    private String name;

    @Override
    protected void doOnCreate() {
        setContentView(R.layout.activity_task_detail);
        context = this;
        i = getIntent();
        id = i.getIntExtra("id", 0);
        taskName = i.getStringExtra("taskName");
        name = i.getStringExtra("name");
        initView();
        initTopView();
        getData();
    }

    private void initTopView() {
        String str1 = taskName
                + "\n运营"
                + "\n" + name
                + "\n1"
                + "\n未完成"
                + "\n2020-11-22 08:30:00"
                + "\n5.6小时";
        TextView tv1 = findViewById(R.id.task_detail_tv_1);
        tv1.setText(str1);
    }

    private void initView() {
        ((TextView) findViewById(R.id.title_common_tv)).setText(taskName + "详情");
        rv = findViewById(R.id.recyclerview);
        adapter = new TaskListAdapter(R.layout.item_task_list, false);
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
        list.add(new PatrolTaskListDTO("1#泵站", 2337, "河道#34处"));
        list.add(new PatrolTaskListDTO("1#粗格栅", 2338, "1#泵站_右防护栏"));
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
