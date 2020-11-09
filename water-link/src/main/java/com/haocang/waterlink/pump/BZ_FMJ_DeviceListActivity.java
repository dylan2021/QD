package com.haocang.waterlink.pump;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haocang.base.config.LibConfig;
import com.haocang.base.ui.BaseActivity;
import com.haocang.datamonitor.alarm.ui.AlarmFragment;
import com.haocang.waterlink.R;
import com.haocang.waterlink.constant.WaterLinkConstant;
import com.haocang.waterlink.home.adapter.HomeViewPageAdapter;
import com.haocang.waterlink.home.ui.HomeFragment;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

//泵站,阀门井,的设备的列表
public class BZ_FMJ_DeviceListActivity extends BaseActivity {

    private int processId;
    private Intent i;
    private boolean isTypeBZ;

    @Override
    protected void doOnCreate() {
        setContentView(R.layout.activity_bz_fmj_device_list);
        i = getIntent();
        processId = i.getIntExtra("processId", 0);
        isTypeBZ = i.getBooleanExtra("isTypeBZ", true);
        initView();
    }

    private void initView() {
        ((TextView)findViewById(R.id.title_common_tv)).setText(i.getStringExtra("title"));
    }
}
