package com.haocang.base.ui;


import android.content.Intent;

import androidx.fragment.app.Fragment;

import android.view.WindowManager;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.haocang.base.R;
import com.haocang.base.config.ArouterPathConstants;

/**
 *
 */
@Route(path = ArouterPathConstants.Common.COMMON_ACTIVY)
public class CommonActivity extends BaseActivity {

    @Autowired
    String fragmentUri;
    @Autowired
    String faultId;//缺陷id
    @Autowired
    String title;

    @Override
    protected void doOnCreate() {
        setContentView(R.layout.activity_common);
        ARouter.getInstance().inject(this);
        try {
            if (fragmentUri != null && !"".equals(fragmentUri)) {
                Postcard pc = ARouter.getInstance().build(fragmentUri);
                fragment = (Fragment) pc.withString("title", title).navigation();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        /**
         * 登录之前的页面全屏
         */
        boolean firstFlag = getIntent().getBooleanExtra("FULL_SCREEN", false);
        if (firstFlag) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        fragment.onActivityResult(requestCode, resultCode, data);
    }


}
