package com.haocang.base.utils;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.fragment.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.haocang.base.R;
import com.haocang.base.bean.Navigate;

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
 * 标 题：
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/1/1816:02
 * 修 改 者：
 * 修改时间：
 */
public class NavigateUtil implements View.OnClickListener {
    public static final int NAVIGATE_COUNT = 5; /*导航个数，默认5个*/
    public static final int NAVIGATION_FIRST = 0;
    public static final int NAVIGATION_SECOND = 1;
    public static final int NAVIGATION_THIRD = 2;
    public static final int NAVIGATION_FOURTH = 3;
    public static final int NAVIGATION_FIFTH = 4;
    private final int NAVIGATION_NONE = -1;
    private List<Navigate> navigateList;
    private String[] fragmentArr;
    private String[] fragmentUri;
    private Context mContext;
    private int curPageIndex = NAVIGATION_FIRST;

    private OnSelectMenuListener mOnSelectMenuListener;

    private View mRootView;

    /**
     * @param context
     * @param rootView
     * @param onSelectMenuListener
     */
    public NavigateUtil(final Context context, final View rootView, final int curPageIndex, final OnSelectMenuListener onSelectMenuListener) {
        mContext = context;
        mRootView = rootView;
        mOnSelectMenuListener = onSelectMenuListener;
        this.curPageIndex = curPageIndex;
        setNavigate(mRootView);
    }


    public void setNavigate(View view) {
        fragmentArr = mContext.getResources().getStringArray(R.array.navigate_fragmentname);
        fragmentUri = mContext.getResources().getStringArray(R.array.navigate_fragment_uri);
        navigateList = new ArrayList<Navigate>();
        String[] navigateNameArr = mContext.getResources().getStringArray(R.array.navigate_name);
        TypedArray conmmonIconArray = mContext.getResources().obtainTypedArray(R.array.navigate_common_icon);
        TypedArray highlightIconArray = mContext.getResources().obtainTypedArray(R.array.navigate_hignlight_icon);
//        int[] navigateCommonIconArr = mContext.getResources().getStringArray(R.array.navigate_common_icon);
//        int[] navigateHighlightIconArr = mContext.getResources().getStringArray(R.array.navigate_hignlight_icon);
        ImageView[] navigateIvArr = getNavigateIvArr(view);
        TextView[] navigateTvArr = getNavigateTvArr(view);
        view.findViewById(R.id.navigate_first_ll).setOnClickListener(this);
        view.findViewById(R.id.navigate_second_ll).setOnClickListener(this);
        view.findViewById(R.id.navigate_third_ll).setOnClickListener(this);
        view.findViewById(R.id.navigate_fourth_ll).setOnClickListener(this);
        view.findViewById(R.id.navigate_fifth_ll).setOnClickListener(this);
        for (int i = 0; i < NAVIGATE_COUNT; i++) {
            Navigate navigate = new Navigate();
            navigate.setCommonIconRes(conmmonIconArray.getResourceId(i, 0));
            navigate.setSelectIconRes(highlightIconArray.getResourceId(i, 0));
            navigate.setName(navigateNameArr[i]);
            navigate.setNavigateIv(navigateIvArr[i]);
            navigate.setNavigateTv(navigateTvArr[i]);
            navigateIvArr[i].setOnClickListener(this);
            navigateTvArr[i].setOnClickListener(this);
            navigateList.add(navigate);
        }
        switchToContent(curPageIndex, true);
//        setNavigationMenuTv(curPageIndex);
    }

    private TextView[] getNavigateTvArr(final View view) {
        TextView[] navigateTvArr = new TextView[NAVIGATE_COUNT];
        navigateTvArr[0] = view.findViewById(R.id.navigate_first_tv);
        navigateTvArr[1] = view.findViewById(R.id.navigate_second_tv);
        navigateTvArr[2] = view.findViewById(R.id.navigate_third_tv);
        navigateTvArr[3] = view.findViewById(R.id.navigate_fourth_tv);
        navigateTvArr[4] = view.findViewById(R.id.navigate_fifth_tv);
        return navigateTvArr;
    }

    private ImageView[] getNavigateIvArr(final View view) {
        ImageView[] navigateIvArr = new ImageView[NAVIGATE_COUNT];
        navigateIvArr[0] = view.findViewById(R.id.navigate_first_iv);
        navigateIvArr[1] = view.findViewById(R.id.navigate_second_iv);
        navigateIvArr[2] = view.findViewById(R.id.navigate_third_iv);
        navigateIvArr[3] = view.findViewById(R.id.navigate_fourth_iv);
        navigateIvArr[4] = view.findViewById(R.id.navigate_fifth_iv);
        return navigateIvArr;
    }

    /**
     * @param pageIndex
     * @param isInit    是否是初始化
     */
    public void switchToContent(final int pageIndex, final boolean isInit) {
        if (pageIndex == curPageIndex && !isInit) {
            return;
        }
        Fragment fragment = null;
        try {
            fragment = (Fragment) ARouter.getInstance().build(fragmentUri[pageIndex]).navigation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        curPageIndex = pageIndex;
        setNavigationMenuTv(curPageIndex);
        if (fragment != null && mOnSelectMenuListener != null) {
            mOnSelectMenuListener.selectMenu(fragment);
        }

    }

    private void setNavigationMenuTv(final int pageIndex) {
        if (navigateList != null && pageIndex < navigateList.size()) {
            for (int i = 0; i < navigateList.size(); i++) {
                Navigate navigate = navigateList.get(i);
                if (pageIndex == i) {
                    navigate.getNavigateIv().setImageResource(navigate.getSelectIconRes());
                    navigate.getNavigateTv().setTextColor(mContext.getResources().getColor(R.color.color_blue));
                } else {
                    navigate.getNavigateIv().setImageResource(navigate.getCommonIconRes());
                    navigate.getNavigateTv().setTextColor(mContext.getResources().getColor(R.color.color_nav_dark));
                }
            }
        }
    }

    @Override
    public void onClick(final View v) {
        int id = v.getId();
        if (id == R.id.navigate_first_iv || id == R.id.navigate_first_tv || id == R.id.navigate_first_ll) {
            switchToContent(NAVIGATION_FIRST, false);

        } else if (id == R.id.navigate_second_iv || id == R.id.navigate_second_tv || id == R.id.navigate_second_ll) {
            switchToContent(NAVIGATION_SECOND, false);

        } else if (id == R.id.navigate_third_iv || id == R.id.navigate_third_tv || id == R.id.navigate_third_ll) {
            switchToContent(NAVIGATION_THIRD, false);

        } else if (id == R.id.navigate_fourth_iv || id == R.id.navigate_fourth_tv || id == R.id.navigate_fourth_ll) {
            switchToContent(NAVIGATION_FOURTH, false);

        } else if (id == R.id.navigate_fifth_iv || id == R.id.navigate_fifth_tv || id == R.id.navigate_fifth_ll) {
            switchToContent(NAVIGATION_FIFTH, false);
        }
    }

    /**
     * .
     */
    public interface OnSelectMenuListener {
        void selectMenu(Fragment fragment);
    }

}
