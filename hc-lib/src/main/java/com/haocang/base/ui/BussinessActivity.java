//package com.haocang.base.ui;
//
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.nfc.NfcAdapter;
//
//import androidx.fragment.app.Fragment;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.TextView;
//
//import com.alibaba.android.arouter.facade.annotation.Route;
//import com.haocang.base.R;
//import com.haocang.base.bean.Navigate;
//import com.haocang.base.config.AppApplication;
//import com.haocang.base.config.ArouterPathConstants;
//import com.haocang.base.utils.NavigateUtil;
//import com.haocang.base.utils.NfcUtil;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 带导航栏跳转.
// */
//@Route(path = ArouterPathConstants.Common.BUSSINESS_ACTIVY)
//public class BussinessActivity extends BaseActivity {
//    /**
//     * 按钮集合.
//     */
//    private List<Navigate> navagateList = new ArrayList<Navigate>();
//
//    /**
//     * 上下文参数..
//     */
//    private Context ctx;
//
//    private TextView spotTv;//消息点
//    protected NfcAdapter mNfcAdapter;
//    private PendingIntent mPendingIntent;
//
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        //此处adapter需要重新获取，否则无法获取message
//        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
//        //一旦截获NFC消息，就会通过PendingIntent调用窗口
//        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()), 0);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        //恢复默认状态
//        if (mNfcAdapter != null)
//            mNfcAdapter.disableForegroundDispatch(this);
//    }
//
//    /**
//     * 初始化.
//     */
//    @Override
//    protected void doOnCreate() {
//        getIntent().putExtra("FIRST_FLAG", true); /*左滑不关闭页面*/
////        boolean taskbarFlag = getIntent().getBooleanExtra("FULL_SCREEN", false);//透明的任务栏
//        setContentView(R.layout.activity_buss);
//        spotTv = findViewById(R.id.spot_tv);
//        ctx = this;
//        String fragmentName = getIntent().getStringExtra("fragmentName");
//        if (!TextUtils.isEmpty(fragmentName)) {
//            fragment = getFragmentByName(fragmentName);
//        }
//        setNavigate();
//        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
//    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        NfcUtil.readNfcTag(this, intent);
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        //设置处理优于所有其他NFC的处理
//        if (mNfcAdapter != null) {
//            mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
//        }
//        setSpot();
//    }
//
//    /**
//     * @param fragmentName Fragment名称
//     * @return 返回一个实例化的fragment
//     */
//    protected Fragment getFragmentByName(final String fragmentName) {
//        Fragment fragment = null;
//        try {
//            fragment = (Fragment) Class.forName(fragmentName).newInstance();
//        } catch (Exception e) {
//            // fragment = new HomeFragment();
//        }
//        return fragment;
//    }
//
//    public void setSpot() {
////        if (AppApplication.messgeCount > 0) {
////            spotTv.setVisibility(View.VISIBLE);
////        } else {
////            spotTv.setVisibility(View.GONE);
////        }
//        AppApplication.getInstance().setSpotTv(spotTv);
//    }
//
//    private NavigateUtil util;
//
//    /**
//     *
//     */
//    private void setNavigate() {
//        /**
//         * 从service中 跳转过来 下标从2开始
//         */
//        String curPageIndex = getIntent().getStringExtra("curPageIndex");
//        int curPage = 0;
//        if (!TextUtils.isEmpty(curPageIndex)) {
//            curPage = Integer.parseInt(curPageIndex);
//        }
//        View navigateView = findViewById(R.id.menu_navigation);
//        util = new NavigateUtil(ctx, navigateView, curPage,
//                new NavigateUtil.OnSelectMenuListener() {
//
//                    @Override
//                    public void selectMenu(final Fragment fragment) {
//                        toFragment(fragment);
//                    }
//                });
//    }
//
//    public void toHome(){
//        util. switchToContent(NavigateUtil.NAVIGATION_FIRST, false);
//    }
//
//
//    /**
//     * @param fragment 需要被实例化的fragment.
//     */
//    private void toFragment(final Fragment fragment) {
//        jumpFragment = fragment;
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.fragment_container, fragment).commit();
//    }
//
//
//    private Fragment jumpFragment;//activity中拦截了fragment的onActivityResult
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        /*然后在碎片中调用重写的onActivityResult方法*/
//        jumpFragment.onActivityResult(requestCode, resultCode, data);
//    }
//}
