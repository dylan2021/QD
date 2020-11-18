package com.haocang.waterlink.follow.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.waterlink.R;
import com.haocang.waterlink.constant.bean.HomeConstants;
import com.haocang.waterlink.follow.adapter.FollowTabAdapter;
import com.haocang.waterlink.follow.adapter.FollowViewPageAdapter;
import com.haocang.waterlink.home.bean.MenuEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题： 新的关注页面
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：hy
 * 创建时间：${DATA} 9:55
 * 修 改 者：
 * 修改时间：
 */
@Route(path = HomeConstants.ArouterPath.FOLLOW_MAIN)
public class FollowNewFragment extends Fragment {
    private ViewPager viewPager;
    private FollowViewPageAdapter pagerAdapter;
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private RecyclerView recyclerview;
    private FollowTabAdapter adapter;
    private ImageView radioIv;

    private DataCombinationFragment dataCombinationFragment;
   /* private DataPointFragment dataPointFragment;*/
    private FollowEquipmentFragment followEquipmentFragment;
    private FollowFaultFragment followFaultFragment;
    private FollowRepairFragment followRepairFragment;
   // private FollowPatrolFragment followPatrolFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_new_follow, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        radioIv = view.findViewById(R.id.radio_iv);
        recyclerview = view.findViewById(R.id.recyclerview);
        adapter = new FollowTabAdapter(R.layout.adapter_follow_tab, getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(adapter);
        viewPager = view.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(4);
//        tabLayout.setupWithViewPager(viewPager);
        dataCombinationFragment = new DataCombinationFragment();
        dataCombinationFragment.setImageView(radioIv);
        dataCombinationFragment.setFirstFlag(true);

  /*      dataPointFragment = new DataPointFragment();
        dataPointFragment.setImageView(radioIv);*/

        followEquipmentFragment = new FollowEquipmentFragment();
        followEquipmentFragment.setImageView(radioIv);

        followFaultFragment = new FollowFaultFragment();
        followFaultFragment.setImageView(radioIv);

        followRepairFragment = new FollowRepairFragment();
        followRepairFragment.setImageView(radioIv);

     /*   followPatrolFragment = new FollowPatrolFragment();
        followPatrolFragment.setImageView(radioIv);*/

        fragmentList.add(dataCombinationFragment);
        //fragmentList.add(dataPointFragment);
        fragmentList.add(followEquipmentFragment);
        fragmentList.add(followFaultFragment);
        fragmentList.add(followRepairFragment);
        //fragmentList.add(followPatrolFragment);
        pagerAdapter = new FollowViewPageAdapter(getActivity().getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                adapter.setSelect(position);
                radioIv.setVisibility(View.VISIBLE);
                recyclerview.scrollToPosition(position);
                if (position > 2) {
                    recyclerview.scrollToPosition(adapter.getItemCount() - 1);
                } else {
                    recyclerview.scrollToPosition(0);
                }
                getData(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int i, Object o) {
                viewPager.setCurrentItem(i);
                adapter.setSelect(i);
                if (i > 2) {
                    recyclerview.scrollToPosition(adapter.getItemCount() - 1);
                } else {
                    recyclerview.scrollToPosition(0);
                }
            }

            @Override
            public void onLongClick(View view, int i, Object o) {

            }
        });
        getTabList();
    }

    private void getData(int position) {
        if (position == 0) {
            dataCombinationFragment.getData();
        } else if (position == 1) {
            followEquipmentFragment.refresh();
        } else if (position == 2) {
            followFaultFragment.refresh();
        } else if (position == 3) {
            followRepairFragment.refresh();
        } /*else if (position == 4) {
            followPatrolFragment.refresh();
        }*/
    }

    private void getTabList() {
        List<MenuEntity> titleList = new ArrayList<>();
        String[] tabArray = getActivity().getResources().getStringArray(R.array.tab_list);
        for (int i = 0; i < tabArray.length; i++) {
            MenuEntity entity = new MenuEntity();
            entity.setName(tabArray[i]);
            if (i == 0) {
                entity.setReson(i + 1);
            }
            titleList.add(entity);
        }
        adapter.addAll(titleList);
    }
}
