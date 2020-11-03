package com.haocang.waterlink.home.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.haocang.actionanalysis.constants.ActionConfig;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.config.AppApplication;
import com.haocang.base.config.ArouterPathConstants;
import com.haocang.base.ui.CommonActivity;
import com.haocang.base.utils.ARouterUtil;
import com.haocang.messge.ui2.MessgeTabLayouFragment;
import com.haocang.waterlink.R;
import com.haocang.waterlink.constant.WaterLinkConstant;
import com.haocang.waterlink.home.adapter.MenuAdapter;
import com.haocang.waterlink.home.adapter.MyViewPagerAdapter;
import com.haocang.waterlink.home.bean.MenuEntity;
import com.haocang.waterlink.home.iview.HomeMenuView;
import com.haocang.waterlink.home.presenter.impl.HomeMenuPresenterImpl;
import com.haocang.waterlink.home.utils.PageControl;
import com.haocang.waterlink.self.ui.VoicerSetFragment;
import com.haocang.waterlink.utils.HomeJumpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题：
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/1/1614:20
 * 修 改 者：
 * 修改时间：
 */
public class HomeMenuFragment extends Fragment implements HomeMenuView {
    /**
     * 上下文参数.
     */
    private Context ctx;
    /**
     * P层，成员变量 需要被实例化.
     */
    private HomeMenuPresenterImpl presenter;

    private RecyclerView recyclerView;

    private MenuAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_home_menu, null);
        initView(view);
        return view;
    }

    /**
     * 初始化控件.
     *
     * @param view .
     */
    public void initView(final View view) {
        ctx = getActivity();
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        adapter = new MenuAdapter(R.layout.adapter_menu, getActivity());
        recyclerView.setAdapter(adapter);
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

        presenter = new HomeMenuPresenterImpl(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getHomeMenuData();
    }

    /**
     * 绑定适配器.
     *
     * @param menuList 获取到的数据
     */
    public void updateAdapter(final List<MenuEntity> menuList) {
        adapter.clear();
        VoicerSetFragment.isMenu = false;
        List<MenuEntity> list = new ArrayList<>();
        if (menuList != null && menuList.size() > 0) {
            list.addAll(menuList);
        }
//        MenuEntity entity = new MenuEntity();
//        entity.setName("更多");
//        entity.setId(0);
//        entity.setUrl(WaterLinkConstant.HOME_MY_APP);
//        entity.setReson(R.mipmap.icon_home_menu_more);
//        list.add(entity);
        adapter.addAll(list);
        adapter.notifyDataSetChanged();
    }


    /**
     * 获取到list.
     *
     * @param list 返回的数据.
     */
    @Override
    public void setData(final List<MenuEntity> list) {
        updateAdapter(list);
    }

    /**
     * @return 获取上下文参数.
     */
    @Override
    public Context getContexts() {
        return getActivity();
    }
}
