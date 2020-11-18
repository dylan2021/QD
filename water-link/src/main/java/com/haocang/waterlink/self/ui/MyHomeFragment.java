package com.haocang.waterlink.self.ui;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.alertview.AlertView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.bean.OrgEntity;
import com.haocang.base.bean.UserEntity;
import com.haocang.base.config.AppApplication;
import com.haocang.base.config.LibConfig;
import com.haocang.base.iview.UserInfoView;
import com.haocang.base.presenter.UserInfoPresenter;
import com.haocang.base.presenter.impl.UserInfoPresenterImpl;
import com.haocang.base.ui.CommonActivity;
import com.haocang.base.utils.ARouterUtil;
import com.haocang.base.utils.GetListListener;
import com.haocang.base.utils.PermissionsProcessingUtil;
import com.haocang.base.utils.PictureUtils;
import com.haocang.base.utils.ToastUtil;
import com.haocang.offline.util.OffLineOutApiUtil;
import com.haocang.waterlink.R;
import com.haocang.waterlink.constant.HomeUrlConst;
import com.haocang.waterlink.constant.bean.HomeConstants;
import com.haocang.waterlink.home.bean.MenuEntity;
import com.haocang.waterlink.login.config.LoginMethodConstants;
import com.haocang.waterlink.login.ui.LoginFragment;
import com.haocang.waterlink.self.iview.MyHomeView;
import com.haocang.waterlink.self.presenter.MyHomePresenter;
import com.haocang.waterlink.self.presenter.impl.MyHomePresenterImpl;
import com.haocang.waterlink.widgets.ImageViewPlus;

import java.lang.reflect.Type;
import java.util.List;


/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题： 我的首页
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/1/2315:21
 * 修 改 者：
 * 修改时间：
 */
@Route(path = HomeConstants.ArouterPath.MY_INFO)
public class MyHomeFragment extends Fragment implements View.OnClickListener, MyHomeView, UserInfoView, PermissionsProcessingUtil.OnPermissionsCallback {
    /**
     * p层逻辑处理.
     */
    private MyHomePresenter presenter;

    /**
     * 头像.
     */
    private ImageViewPlus headIv;

    /**
     * 图片最大张数.
     */
    private static final int PICTURE_MAX = 1;

    private TextView userNameTv;
    private TextView orgTv;
    private TextView groupTv;//班组
    private Intent intent;
    private UserInfoPresenter userInfoPresenter;
    private LinearLayout voicersetLl;

    /**
     * 初始化.
     *
     * @param inflater           .
     * @param container          .
     * @param savedInstanceState .
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_my_home, null);
        initView(view);
        return view;
    }


    /**
     * 初始化子控件.
     *
     * @param view .
     */
    private void initView(final View view) {
        userInfoPresenter = new UserInfoPresenterImpl(this);
        intent = new Intent(getActivity(), CommonActivity.class);
        headIv = view.findViewById(R.id.my_head_iv);
        presenter = new MyHomePresenterImpl(this);
        userInfoPresenter.getUserInfo();
        userNameTv = view.findViewById(R.id.user_name_tv);
        orgTv = view.findViewById(R.id.org_tv);
        groupTv = view.findViewById(R.id.group_tv);
        view.findViewById(R.id.my_head_iv).setOnClickListener(this);
        view.findViewById(R.id.modify_pwd_ll).setOnClickListener(this);
        view.findViewById(R.id.feedback_ll).setOnClickListener(this);
        view.findViewById(R.id.version_ll).setOnClickListener(this);
        view.findViewById(R.id.home_setup_ll).setOnClickListener(this);
        view.findViewById(R.id.exit_ll).setOnClickListener(this);
        view.findViewById(R.id.self_voicerset_ll).setOnClickListener(this);
        view.findViewById(R.id.siwich_ll).setOnClickListener(this);
        voicersetLl = view.findViewById(R.id.self_voicerset_ll);
        setDefaultHead();
        isShow();
        getOffceMenu();
    }

    /**
     * 获取离线菜单
     */
    private void getOffceMenu() {
        CommonModel<MenuEntity> progressModel = new CommonModelImpl<>();
        Type type = new TypeToken<List<MenuEntity>>() {
        }.getType();
        progressModel
                .setListType(type)
                .setHasDialog(false)
                .setUrl(HomeUrlConst.HOME_ALL_MENU)
                .setListListener(new GetListListener<MenuEntity>() {
                    @Override
                    public void success(List<MenuEntity> list) {
                        if (list != null && list.size() > 0) {
                            String result = new Gson().toJson(list);
                            insertMenu(result);//离线模式下菜单
                        }

                    }
                })
                .getList();
    }

    /**
     * /**
     * 语音菜单是否显示
     */
    private void isShow() {
        if (isVoiceMenu()) {
            voicersetLl.setVisibility(View.VISIBLE);
            voicersetLl.setVisibility(View.VISIBLE);
        } else {
            voicersetLl.setVisibility(View.GONE);
        }
    }


    /**
     * 是否显示语音菜单
     *
     * @return
     */
    private boolean isVoiceMenu() {
        boolean flag = false;
        String[] sr = AppApplication.getInstance().getUserEntity().getAppOpers();
        if (sr != null && sr.length > 0) {
            for (String oper : sr) {
                if (oper.equals("VOI_VOICE_ALL")) {
                    flag = true;
                }
            }
        }
        return flag;
    }


    /**
     * 离线模式菜单
     *
     * @param result
     */
    private void insertMenu(String result) {
        OffLineOutApiUtil.saveMenu(result);
    }

    /**
     * 点击事件.
     *
     * @param view .
     */
    @Override
    public void onClick(final View view) {
        if (view.getId() == R.id.modify_pwd_ll) {
            toFragment(SelfModifyPassWordFragment.class.getName());
        } else if (view.getId() == R.id.home_setup_ll) {
            toFragment(HomeSetUpFragment.class.getName());
        } else if (view.getId() == R.id.my_head_iv) {
            PermissionsProcessingUtil.setPermissions(this, LibConfig.STORAGE, this);
        } else if (view.getId() == R.id.feedback_ll) {
            toFragment(FeedbackFragment.class.getName());
        } else if (view.getId() == R.id.version_ll) {
            toFragment(VersionFragment.class.getName());
        } else if (view.getId() == R.id.exit_ll) {
            new AlertView("提示", "是否退出登录？", "取消",
                    new String[]{"确定"}, null, getActivity(), AlertView.Style.Alert, new com.bigkoo.alertview.OnItemClickListener() {
                @Override
                public void onItemClick(Object o, int position) {
                    if (position >= 0) {
                        AppApplication.getInstance().finishActivity(getActivity());
                        AppApplication.getInstance().finisBussActivity(getActivity());
                        AppApplication.getInstance().setOnReceiveVoiceListener(null);
                        LibConfig.setCookie("");
                        toFragment(LoginFragment.class.getName());
                    }
                }
            }).show();
        } else if (view.getId() == R.id.self_voicerset_ll) {
            toFragment(VoicerSetFragment.class.getName());
        } else if (view.getId() == R.id.siwich_ll) {
            ARouterUtil.toFragment(LoginMethodConstants.ACCOUNT_SWITCHING);
        }
    }


    /**
     * 执行跳转.
     *
     * @param fragmentName 跳转的类.
     */
    private void toFragment(final String fragmentName) {
        intent.putExtra("fragmentName", fragmentName);
        startActivity(intent);
    }

    /**
     * 获取上下文参数.
     *
     * @return
     */
    @Override
    public Context getContexts() {
        return getActivity();
    }

    private RequestOptions options = new RequestOptions()
            .placeholder(R.drawable.ic_self_head)// 正在加载中的图片
            .error(R.drawable.ic_self_head) // 加载失败的图片
            .diskCacheStrategy(DiskCacheStrategy.ALL); // 磁盘缓存策略

    /**
     * 设置数据.
     *
     * @param entity .
     */
    @Override
    public void setAccountData(final UserEntity entity) {
        if (entity != null) {
            userNameTv.setText(entity.getName() != null ? entity.getName() : "");
            if (entity.getOrgEntity() != null && entity.getOrgEntity().size() > 0) {
                List<OrgEntity> list = entity.getOrgEntity();
                groupTv.setText(list.get(list.size() - 1).getName());
                orgTv.setText(list.get(0).getName());
            }
            orgTv.setText(entity.getOrgName());
//            groupTv.setText();
            intent.putExtra("userId", entity.getId() + "");
            if (!TextUtils.isEmpty(entity.getImageUrl()) && getActivity() != null) {
                Glide.with(getActivity()).load(entity.getImageUrl()).apply(options).into(headIv);
            } else {
                setDefaultHead();
            }
        }
    }

    /**
     * 设置默认头像.
     */
    private void setDefaultHead() {
        Resources r = getActivity().getResources();
        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + r.getResourcePackageName(R.drawable.ic_self_head) + "/"
                + r.getResourceTypeName(R.drawable.ic_self_head) + "/"
                + r.getResourceEntryName(R.drawable.ic_self_head));
        headIv.setImageURI(uri);
    }

    @Override
    public Fragment getFragment() {
        return this;
    }


    @Override
    public void setUri(final Uri uri) {
        headIv.setImageURI(uri);
    }

    @Override
    public void setSuccess() {
        userInfoPresenter.getUserInfo();
//        presenter.accountData();
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode,
                                 final Intent data) {
        presenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void callBack(final boolean flag,
                         final String permissions) {
        if (flag && LibConfig.STORAGE.equals(permissions)) {
            PermissionsProcessingUtil.setPermissions(this, LibConfig.CAMERA, this);
        } else if (flag && LibConfig.CAMERA.equals(permissions)) {
            PictureUtils.openPicture(this, PICTURE_MAX);
        } else {
            ToastUtil.makeText(getActivity(), getResources().getString(R.string.permissions_camera));
        }
    }


    @Override
    public void setUserInfo(UserEntity entity) {
        if (entity != null) {
            userNameTv.setText(entity.getName() != null ? entity.getName() : "");
            if (entity.getOrgEntity() != null && entity.getOrgEntity().size() > 0) {
                List<OrgEntity> list = entity.getOrgEntity();
                groupTv.setText(list.get(list.size() - 1).getName());
                orgTv.setText(list.get(0).getName());
            }
            orgTv.setText(entity.getOrgName());
            intent.putExtra("userId", entity.getId() + "");
            if (!TextUtils.isEmpty(entity.getImageUrl()) && getActivity() != null) {
                Glide.with(getActivity()).load(entity.getImageUrl()).apply(options).into(headIv);
            } else {
                setDefaultHead();
            }
        }
    }
}