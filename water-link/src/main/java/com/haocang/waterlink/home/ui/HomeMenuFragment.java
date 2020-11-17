package com.haocang.waterlink.home.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haocang.base.adapter.BaseAdapter;
import com.haocang.waterlink.R;
import com.haocang.waterlink.home.adapter.MenuAdapter;
import com.haocang.waterlink.home.bean.MenuEntity;
import com.haocang.waterlink.home.iview.HomeMenuView;
import com.haocang.waterlink.home.presenter.impl.HomeMenuPresenterImpl;
import com.haocang.waterlink.self.ui.VoicerSetFragment;
import com.haocang.waterlink.utils.HomeJumpUtil;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class HomeMenuFragment extends Fragment implements HomeMenuView {
    /**
     * 上下文参数.
     */
    private FragmentActivity context;
    /**
     * P层，成员变量 需要被实例化.
     */
    private HomeMenuPresenterImpl presenter;

    private RecyclerView recyclerView;

    private MenuAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
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
        context = getActivity();
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
        adapter = new MenuAdapter(R.layout.adapter_menu, context);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int i, Object o) {
                MenuEntity entity = (MenuEntity) o;
                new HomeJumpUtil().jump(entity, context);
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
