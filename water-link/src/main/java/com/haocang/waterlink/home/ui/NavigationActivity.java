package com.haocang.waterlink.home.ui;

import android.content.Intent;
import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.haocang.base.config.LibConfig;
import com.haocang.base.ui.BaseActivity;
import com.haocang.datamonitor.alarm.ui.AlarmFragment;
import com.haocang.waterlink.R;
import com.haocang.waterlink.constant.WaterLinkConstant;
import com.haocang.waterlink.home.adapter.HomeViewPageAdapter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

//底部导航
@Route(path = WaterLinkConstant.HOME)
public class NavigationActivity extends BaseActivity {
    private BottomNavigationViewEx bnve;
    private List<Fragment> fragments;// used for ViewPager adapter
    private HomeViewPageAdapter viewPageAdapter;
    private ViewPager viewPager;

    @Override
    protected void doOnCreate() {
        setContentView(R.layout.activity_navigation);
        bnve = findViewById(R.id.bnve);
        bnve.enableAnimation(false);
        bnve.enableShiftingMode(false);
        bnve.enableItemShiftingMode(false);
//        bnve.setIconsMarginTop(-10);
        viewPager = findViewById(R.id.vp);
        viewPager.setOffscreenPageLimit(5);
        initData();
    }

    private void initData() {
        getIntent().putExtra("isHomeActivity", true);
        fragments = new ArrayList<>();
        String[] fragmentName = getResources().getStringArray(R.array.home_menu_name);
        for (int i = 0; i < fragmentName.length; i++) {
            Fragment fragment = getFragmentByName(fragmentName[i]);
            Bundle bundle = new Bundle();
            bundle.putString("title", "ss");
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        viewPageAdapter = new HomeViewPageAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(viewPageAdapter);
        bnve.setupWithViewPager(viewPager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == LibConfig.SCAN_CODE) {
            ((HomeFragment) fragments.get(0)).onActivityResult(requestCode, resultCode, data);
        }
        if (resultCode==1023){
            ((AlarmFragment) fragments.get(1)).onActivityResult(requestCode, resultCode, data);
        }
//        data.
//        for (int i = 0; i <fragments.size() ; i++) {
////            fragments.get(i)
//        }
//        super.onActivityResult(requestCode, resultCode, data);
    }
}
