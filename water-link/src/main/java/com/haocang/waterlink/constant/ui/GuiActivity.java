package com.haocang.waterlink.constant.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.cj.videoeditor.Constants;
import com.haocang.base.config.AppApplication;
import com.haocang.base.config.LibConfig;
import com.haocang.base.ui.CommonActivity;
import com.haocang.equipmentar.constants.EquipmentARConstants;
import com.haocang.maonlib.base.config.HCLicConstant;
import com.haocang.offline.bean.user.OffLineUserEntity;
import com.haocang.offline.util.OffLineOutApiUtil;
import com.haocang.waterlink.R;
import com.haocang.waterlink.home.ui.NavigationActivity;
import com.haocang.waterlink.login.ui.LoginFragment;
import com.haocang.waterlink.utils.AutomaticLogonUtils;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.List;


/**
 * 启动程序运行的欢迎页.
 */
@Route(path = "/gui/activity")
public class GuiActivity extends Activity {

    /**
     * 延时操作跳转.
     */
    private static final int POSTDELAYEDTIME = 3000;
    /**
     * 上下文参数.
     */
    private Context ctx;

    /**
     * 把用户名和密码存储下来.
     */
    private SharedPreferences sp;

    /**
     * 初始化.
     *
     * @param savedInstanceState ..
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(AppApplication.getInstance());
        ARouter.printStackTrace();
        Constants.init(AppApplication.getContext());
//        WebSocketUtil.getInstance().createStompClient();//连接websocket
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
//        QQShareUtil.mAppid = "1106964590";
        CrashReport.initCrashReport(getApplicationContext(), "010cddf759", false);//开启闪退收集系统
        EquipmentARConstants.setKey(HCLicConstant.AR_KEY);
        HCLicConstant.setAddressIp();
        setContentView(R.layout.activity_gui);
        ctx = GuiActivity.this;
        LibConfig.CONTINUITY_INTERVAL = 180;
        init();

    }


    /**
     * 执行handler延时3秒 进行跳转.
     */
    private void init() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (AppApplication.isNetWord) {//todo 没网的情况下 进入登录页面
                    login();
                } else {
                    toFragment();
                }

            }
        }, POSTDELAYEDTIME);
    }

    private void login() {
        List<OffLineUserEntity> list = OffLineOutApiUtil.getUserList();
        if (list != null && list.size() > 0) {
            OffLineUserEntity entity = list.get(list.size() - 1);
            automaticLogoin(entity);
        } else {
            toFragment();
        }
    }

    /**
     * todo 如果本地数据库里 存在登录记录 直接尝试登录，非登录成功 需要进入到登录页面
     *
     * @param entity
     */
    private void automaticLogoin(final OffLineUserEntity entity) {
        new AutomaticLogonUtils(this).setUserEntity(entity)
                .setAutomaticLogon(new AutomaticLogonUtils.AutomaticLogonInterface() {
                    @Override
                    public void loginSuccess() {
                        setConfiguration(entity.getTel(), entity.getPassword());
                        Intent intent = new Intent(GuiActivity.this, NavigationActivity.class);
//                        intent.putExtra("fragmentName", HomeFragment.class.getName());
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void passWordError() {
                        toFragment();
                    }

                    @Override
                    public void userNameError() {
                        toFragment();
                    }
                }).login();
    }

    private void toFragment() {
        Intent intent = new Intent(ctx, CommonActivity.class);
        intent.putExtra("fragmentName", LoginFragment.class.getName());
        intent.putExtra("FIRST_FLAG", true);
        intent.putExtra("FULL_SCREEN", true);
        startActivity(intent);
        finish();
    }

    /**
     * todo 每次登陆成功后 刷新用户名和密码.
     */
    private void setConfiguration(String userName, String passWord) {
        try {
            sp = getSharedPreferences(LibConfig.CHECK, Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString(LibConfig.USERNAME, userName);
            edit.putString(LibConfig.PASSWORD, passWord);
            edit.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
