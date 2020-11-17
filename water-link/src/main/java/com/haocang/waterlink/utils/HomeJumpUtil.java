package com.haocang.waterlink.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.haocang.base.config.ArouterPathConstants;
import com.haocang.base.utils.ARouterUtil;
import com.haocang.base.utils.ToastUtil;
import com.haocang.curve.collection.ui.SignleCollectionFragment;
import com.haocang.maonlib.base.config.HCLicConstant;
import com.haocang.waterlink.R;
import com.haocang.waterlink.home.bean.MenuEntity;

import java.util.HashMap;
import java.util.Map;

public class HomeJumpUtil {
    private Activity activity;

    public void jump(final MenuEntity entity, Activity ac) {
        activity = ac;
        if (entity != null && !TextUtils.isEmpty(entity.getUrl()) && isMenuExist(entity.getUrl())) {
            if (isActivityUrl(entity.getUrl())) {
                toActivity(entity);
            } else {
                toFragment(entity);
            }
        } else {
            ToastUtil.makeText(ac, "正在开发中");
        }
    }

    //判断app是否有此功能
    private boolean isMenuExist(final String url) {
        String[] menuSr = activity.getResources().getStringArray(R.array.home_menu_all_url);
        for (String urlSr : menuSr) {
            if (urlSr.equals(url)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param url 路径.
     * @return
     */
    private boolean isActivityUrl(final String url) {
        boolean isActivityUrl = false;
        String[] activityUrls = activity.getResources().getStringArray(R.array.activity_url);
        if (activityUrls != null && activityUrls.length > 0 && url != null) {
            for (String u : activityUrls) {
                if (u.equals(url)) {
                    isActivityUrl = true;
                    break;
                }
            }
        }
        return isActivityUrl;
    }

    /**
     * 执行跳转.
     */
    protected void toFragment(MenuEntity entity) {
        String fragmentUri = entity.getUrl();
        if ("/monitor/main".equals(fragmentUri)) {
            fragmentUri = "/monitor/home/";
        }
        if ("/curve/main".equals(fragmentUri)) {
//            fragmentUri = ArouterPathConstants.Curve.CURVE_COLLECTION;
            fragmentUri = "/curve/SignleCollectionFragment";
            Map<String, Object> map = new HashMap<>();
            map.put("fragmentUri", fragmentUri);
            map.put("main", "main");
            HCLicConstant.JUMP_STATIC = "1";
            ARouterUtil.toFragment(map);
            return;
        }
        if ("/curve/two".equals(fragmentUri)) {
//            fragmentUri = ArouterPathConstants.Curve.CURVE_COLLECTION;
            fragmentUri = "/curve/SignleCollectionFragment";
            Map<String, Object> map = new HashMap<>();
            map.put("fragmentUri", fragmentUri);
            map.put("main", "main");
            HCLicConstant.JUMP_STATIC = "2";
            ARouterUtil.toFragment(map);
            return;
        }
        String params = entity.getParams();

        Map<String, Object> map = new HashMap<>();
        map.put("fragmentUri", fragmentUri);
        map.put("title", entity.getName());
        map.put("id", entity.getId());
        if (!TextUtils.isEmpty(params)) {
            map.put("params", params);
        }

        ARouterUtil.toFragment(map);
    }


    private void toActivity(final MenuEntity entity) {
        String url = entity.getUrl();
        Map<String, Object> map = new HashMap<>();
        map.put("noTitleBarFlag", true);
        if (!TextUtils.isEmpty(entity.getParams())) {
            map.put("params", entity.getParams());
        }
        ARouterUtil.toActivity(map, url);
    }

}
