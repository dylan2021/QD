package com.haocang.waterlink.myapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.waterlink.R;
import com.haocang.waterlink.home.bean.MenuEntity;
import com.haocang.waterlink.myapp.adapter.MyAppSearchAdapter;
import com.haocang.waterlink.utils.HomeJumpUtil;

import java.lang.reflect.Type;
import java.util.List;

public class MyAppSearchFragment extends Fragment {
    private EditText searchEt;
    private ImageView contentIv;
    private TextView contentTv;
    private MyAppSearchAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayout noDataLl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_my_app_search, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        noDataLl = view.findViewById(R.id.no_data_ll);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration divider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.home_divider_shape));
        recyclerView.addItemDecoration(divider);
        adapter = new MyAppSearchAdapter(R.layout.adapter_my_app_search, getActivity());
        recyclerView.setAdapter(adapter);
        contentIv = view.findViewById(R.id.content_iv);
        contentTv = view.findViewById(R.id.content_tv);
        searchEt = view.findViewById(R.id.search_et);
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = searchEt.getText().toString().trim();
                addData(content);
            }
        });
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int i, Object o) {
                MenuEntity entity = (MenuEntity) o;
                new HomeJumpUtil().jump(entity, getActivity());
            }

            @Override
            public void onLongClick(View view, int i, Object o) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        inputMethodShow();
    }

    @Override
    public void onPause() {
        super.onPause();
        inputMethodHide();
    }

    private void inputMethodHide() {
        InputMethodManager imm = (InputMethodManager) searchEt.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.showSoftInput(searchEt, InputMethodManager.SHOW_FORCED);//SHOW_FORCED表示强制显示
        imm.hideSoftInputFromWindow(searchEt.getWindowToken(), 0); //强制隐藏键盘
    }

    private void inputMethodShow() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) searchEt.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(searchEt, InputMethodManager.SHOW_FORCED);//SHOW_FORCED表示强制显示
            }
        }, 200);
    }

    private void addData(final String content) {
        adapter.clear();
        if (TextUtils.isEmpty(content)) {
            isShow();
            contentIv.setBackgroundResource(R.mipmap.icon_home_app_search);
            contentTv.setText("请搜索你感兴趣的应用");
            return;
        }
        List<MenuEntity> list = getAllApp();
        for (MenuEntity entity : list) {
            if (entity.getName().contains(content) && entity.getParentId() > 0) {
                adapter.add(entity);
            }
        }
        isShow();
        if (adapter.getItemCount() == 0) {
            contentIv.setBackgroundResource(R.mipmap.icon_home_search_nodata);
            contentTv.setText("找不到匹配应用");
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 是否展示列表或者暂无数据
     */
    private void isShow() {
        if (adapter.getItemCount() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            noDataLl.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            noDataLl.setVisibility(View.VISIBLE);
        }
    }

    private List<MenuEntity> getAllApp() {
        Type type = new TypeToken<List<MenuEntity>>() {
        }.getType();
        List<MenuEntity> list = new Gson().fromJson(getAppSr(), type);
        return list;
    }

    private String getAppSr() {
        return getActivity().getIntent().getStringExtra("list");
    }
}


