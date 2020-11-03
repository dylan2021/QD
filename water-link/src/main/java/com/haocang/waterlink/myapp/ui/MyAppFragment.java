package com.haocang.waterlink.myapp.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.config.AppApplication;
import com.haocang.base.ui.CommonActivity;
import com.haocang.base.utils.ARouterUtil;
import com.haocang.base.utils.ToastUtil;
import com.haocang.waterlink.R;
import com.haocang.waterlink.constant.WaterLinkConstant;
import com.haocang.waterlink.home.bean.MenuEntity;
import com.haocang.waterlink.myapp.adapter.MyAppAdapter;
import com.haocang.waterlink.myapp.adapter.MyAppHomeAdapter;
import com.haocang.waterlink.myapp.iview.MyAppView;
import com.haocang.waterlink.myapp.presenter.MyAppPresenter;
import com.haocang.waterlink.myapp.presenter.impl.MyAppPresenterImpl;
import com.haocang.waterlink.utils.HomeJumpUtil;

import java.util.ArrayList;
import java.util.List;

@Route(path = WaterLinkConstant.HOME_MY_APP)
public class MyAppFragment extends Fragment implements MyAppView, View.OnClickListener {

    private TabLayout tablayout;

    private MyAppPresenter presenter;
    private MyAppAdapter adapter;//所有菜单
    private RecyclerView recyclerView;//所有的菜单

    private RecyclerView myRy;//首页保存的菜单
    private MyAppHomeAdapter myAppHomeAdapter;//首页菜单适配器

    private GridLayoutManager gridLayoutManager;

    private List<String> tabList = new ArrayList<>();//tab菜单
    private List<Integer> indexList = new ArrayList<>();//tab和ry下标对应关系

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_my_app, null);
        initView(view);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initView(View view) {
        myRy = view.findViewById(R.id.myapp_ry);
        myAppHomeAdapter = new MyAppHomeAdapter(R.layout.adapter_my_app_home, getActivity());
        myRy.setLayoutManager(new GridLayoutManager(getActivity(), 6));
        myRy.setAdapter(myAppHomeAdapter);
        myRy.setOnClickListener(this);
        tablayout = view.findViewById(R.id.tablayout);
        recyclerView = view.findViewById(R.id.recyclerview);
        gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int type = adapter.getItemViewType(position);
                if (type == 0) {
                    return 4;
                } else {
                    return 1;
                }
            }
        });
        view.findViewById(R.id.manager_tv).setOnClickListener(this);
        view.findViewById(R.id.myhome_ll).setOnClickListener(this);
        view.findViewById(R.id.search_tv).setOnClickListener(this);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new MyAppAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                for (int i = 0; i < indexList.size(); i++) {
                    int first = gridLayoutManager.findFirstVisibleItemPosition();
                    if (first < indexList.get(i)) {
                        tablayout.setScrollPosition(i, 0, true);
                        return;
                    }
                }
            }
        });
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = 0;
                for (int i = 0; i < indexList.size(); i++) {
                    int tabIndex = tab.getPosition();
                    if (i == tabIndex) {
                        if (tabIndex == 0) {
                            gridLayoutManager.scrollToPositionWithOffset(0, 0);
                        } else {
                            index = indexList.get(i - 1);
                            gridLayoutManager.scrollToPositionWithOffset(index, 0);
                        }

                        return;
                    }
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        adapter.setOnItemOnclick(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int i, Object o) {
                MenuEntity entity = (MenuEntity) o;
                new HomeJumpUtil().jump(entity, getActivity());
            }


            @Override
            public void onLongClick(View view, int i, Object o) {

            }
        });
        presenter = new MyAppPresenterImpl(this);
        presenter.getMyAllAppList();//获取全部菜单
        presenter.getHomePageList();//获取首页菜单

    }


    @Override
    public void setMyAllAppList(List<MenuEntity> list) {
        adapter.addAll(list);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void setTabList(List<MenuEntity> list) {
        for (int i = 0; i < list.size(); i++) {
            tablayout.addTab(tablayout.newTab());
            tablayout.getTabAt(i).setText(list.get(i).getName());
            tabList.add(list.get(i).getName());
        }
    }

    @Override
    public void setIndexList(List<Integer> list) {
        indexList.addAll(list);
    }

    @Override
    public void setHomePage(final List<MenuEntity> list) {
        myAppHomeAdapter.clear();
        //获取首页保存的list
        if (list.size() <= 5) {
            myAppHomeAdapter.addAll(list);
            myAppHomeAdapter.notifyDataSetChanged();
        } else {
            List<MenuEntity> mentList = new ArrayList<>();
            mentList.addAll(list.subList(0, 5));
//            MenuEntity entity = new MenuEntity();
//            entity.setId(0);
//            mentList.add(entity);
            myAppHomeAdapter.addAll(mentList);
            myAppHomeAdapter.notifyDataSetChanged();
        }

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.manager_tv || v.getId() == R.id.myhome_ll || v.getId() == R.id.myapp_ry) {
            Intent intent = new Intent(getActivity(), CommonActivity.class);
            intent.putExtra("fragmentName", MyAppEditFragment.class.getName());
            startActivityForResult(intent, 3001);
        } else if (v.getId() == R.id.search_tv) {
            List<MenuEntity> menuEntityList = adapter.mList;
            if (menuEntityList == null || menuEntityList.size() == 0) {
                ToastUtil.makeText(getActivity(), "数据加载中");
                return;
            }
            Intent intent = new Intent(getActivity(), CommonActivity.class);
            intent.putExtra("fragmentName", MyAppSearchFragment.class.getName());
            intent.putExtra("list", new Gson().toJson(menuEntityList));
            startActivity(intent);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == 3001) {
            //提交成功刷新
            myAppHomeAdapter.clear();
            myAppHomeAdapter.notifyDataSetChanged();
            presenter.getHomePageList();//获取首页菜单
        }
    }
}
