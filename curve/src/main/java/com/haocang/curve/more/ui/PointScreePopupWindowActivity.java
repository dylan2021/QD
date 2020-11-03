package com.haocang.curve.more.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.ScreePopupWindowAdapter;
import com.haocang.base.bean.Contact;
import com.haocang.base.bean.ScreeEntity;
import com.haocang.base.ui.CommonActivity;
import com.haocang.curve.R;
import com.haocang.curve.more.adapter.PointProcessAdapter;
import com.haocang.curve.more.iview.PointScreePopupView;
import com.haocang.curve.more.presenter.PointScreePopupPresenter;
import com.haocang.curve.more.presenter.impl.PointScreePopupPresenterImpl;

import java.util.ArrayList;
import java.util.List;


public class PointScreePopupWindowActivity extends Activity implements PointScreePopupView, View.OnClickListener {

    private RecyclerView recyclerview;//数据分类
    private ScreePopupWindowAdapter adapter;
    private PointScreePopupPresenter presenter;
    public static final int REQUESTCODE = 3989;//从工艺位置选择返回

    private RecyclerView processRecyclerview;
    private PointProcessAdapter pointProcessAdapter;
    /**
     * 名为站点id，实则为工艺位置id
     */
    private String siteId = "";
    private String processList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_scree_popup_window);
        initView();
    }

    private void initView() {
        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new ScreePopupWindowAdapter(R.layout.adapter_scree_content);
        recyclerview.setAdapter(adapter);
        presenter = new PointScreePopupPresenterImpl(this);
        presenter.getDataTypeList();
        adapter.setOnItemClickListener(typeOnItemClick);
        findViewById(R.id.process_ll).setOnClickListener(this);
        processRecyclerview = findViewById(R.id.process_recyclerview);
        processRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        pointProcessAdapter = new PointProcessAdapter(R.layout.adapter_point_process);
        processRecyclerview.setAdapter(pointProcessAdapter);
        findViewById(R.id.reset_tv).setOnClickListener(this);
        findViewById(R.id.complete_tv).setOnClickListener(this);
        findViewById(R.id.scree_ll).setOnClickListener(this);
        addHistory();
    }

    BaseAdapter.OnItemClickListener typeOnItemClick = new BaseAdapter.OnItemClickListener() {
        @Override
        public void onClick(View view, int position, Object item) {
            ScreeEntity entity = (ScreeEntity) item;
            if (entity.isSelector()) {
                entity.setSelector(false);
            } else {
                entity.setSelector(true);
            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onLongClick(View view, int position, Object item) {

        }
    };

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void addHistory() {
        if (TextUtils.isEmpty(getProcessList())) {
            return;
        }
        List<Contact> list = new Gson().fromJson(getProcessList(), new TypeToken<List<Contact>>() {
        }.getType());
        pointProcessAdapter.addAll(list);
        pointProcessAdapter.notifyDataSetChanged();
    }

    @Override
    public Context getContexts() {
        return this;
    }

    @Override
    public String getSiteId() {
        return getIntent().getStringExtra("siteId");
    }

    @Override
    public String getProcessList() {
        return getIntent().getStringExtra("processList");
    }

    @Override
    public String getCategoryId() {
        return getIntent().getStringExtra("categoryId");
    }

    @Override
    public void setDataType(List<ScreeEntity> list) {
        adapter.addAll(list);
        adapter.notifyDataSetChanged();
    }

    // 实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.process_ll) {
            Intent intent = new Intent(this, CommonActivity.class);
            intent.putExtra("fragmentName", RegionSelectionFragment.class.getName());
            if (!TextUtils.isEmpty(pointProcessAdapter.getSiteIdList())) {
                intent.putExtra("siteid", pointProcessAdapter.getSiteIdList());
            }
            pointProcessAdapter.clear();
            siteId = "";
            startActivityForResult(intent, REQUESTCODE);
        } else if (v.getId() == R.id.complete_tv) {
            Intent intent = new Intent();
            intent.putExtra("categoryId", adapter.getCategoryId());
            intent.putExtra("siteId", siteId);
            intent.putExtra("processList", processList);
            setResult(10031, intent);
            finish();
        } else if (v.getId() == R.id.reset_tv) {
            pointProcessAdapter.clear();
            pointProcessAdapter.notifyDataSetChanged();
            adapter.resetData();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (REQUESTCODE == requestCode && data != null) {
            processList = data.getStringExtra("processList");
            if (TextUtils.isEmpty(processList)) {
                return;
            }
            List<Contact> list = new Gson().fromJson(processList, new TypeToken<List<Contact>>() {
            }.getType());

            for (Contact contact : list) {
//                if (getSiteIdList().contains(contact.getId() + "")) {
                siteId += contact.getId() + ",";
                pointProcessAdapter.add(contact);
//                }
            }
            if (!TextUtils.isEmpty(siteId)) {
                siteId = siteId.substring(0, siteId.length() - 1);
            }
            pointProcessAdapter.notifyDataSetChanged();
        }
    }

    private List<String> getSiteIdList() {
        List<String> list = new ArrayList<>();
//        String siteId getIntent().getStringExtra("siteId");
//        if (!TextUtils.isEmpty(siteId)) {
//            String[] sr = siteId.split(",");
//            for (String s : sr) {
//                list.add(s);
//            }
//        }
        return list;
    }

}
