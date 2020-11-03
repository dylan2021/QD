package com.haocang.waterlink.myapp.ui;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.tabs.TabLayout;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.utils.ProgressBarDialog;
import com.haocang.base.utils.ToastUtil;
import com.haocang.waterlink.R;
import com.haocang.waterlink.constant.WaterLinkConstant;
import com.haocang.waterlink.home.bean.MenuEntity;
import com.haocang.waterlink.myapp.adapter.MyAppEditAdapter;
import com.haocang.waterlink.myapp.adapter.MyAppEditTabAdapter;
import com.haocang.waterlink.myapp.iview.MyAppEditView;
import com.haocang.waterlink.myapp.iview.MyAppView;
import com.haocang.waterlink.myapp.presenter.MyAppEditPresenter;
import com.haocang.waterlink.myapp.presenter.MyAppPresenter;
import com.haocang.waterlink.myapp.presenter.impl.MyAppEditPresenterImpl;
import com.haocang.waterlink.myapp.presenter.impl.MyAppPresenterImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static androidx.core.content.ContextCompat.getSystemService;

/**
 * 我的应用编辑
 */
@Route(path = WaterLinkConstant.HOME_MY_APP_EDIT)
public class MyAppEditFragment extends Fragment implements MyAppView, View.OnClickListener, MyAppEditView {

    private RecyclerView myRecyclerView;
    private RecyclerView tabRecyclerView;
    private MyAppEditAdapter myAppEditAdapter;
    private MyAppEditTabAdapter tabAdapter;
    private GridLayoutManager gridLayoutManager;
    private MyAppPresenter presenter;
    private MyAppEditPresenter editPresenter;
    private TabLayout tablayout;
    private List<String> tabList = new ArrayList<>();
    private List<Integer> indexList = new ArrayList<>();
    private ProgressBarDialog loacDialog;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_app_edit_menu, null);
        initView(view);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initView(View view) {
        loacDialog = new ProgressBarDialog(getActivity());
        view.findViewById(R.id.submit_tv).setOnClickListener(this);
        tablayout = view.findViewById(R.id.tablayout);
        myRecyclerView = view.findViewById(R.id.my_recyclerview);
        myRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        myAppEditAdapter = new MyAppEditAdapter(R.layout.adapter_my_app_edit, getActivity());
        myRecyclerView.setAdapter(myAppEditAdapter);

        tabRecyclerView = view.findViewById(R.id.tab_recyclerview);
        gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int type = tabAdapter.getItemViewType(position);
                if (type == 0) {
                    return 4;
                } else {
                    return 1;
                }
            }
        });
        tabAdapter = new MyAppEditTabAdapter(getActivity());
        tabRecyclerView.setLayoutManager(gridLayoutManager);
        tabRecyclerView.setAdapter(tabAdapter);
        tabRecyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                for (int i = 0; i < indexList.size(); i++) {
                    int first = gridLayoutManager.findFirstVisibleItemPosition();
                    if (first < indexList.get(i)) {
                        tablayout.setScrollPosition(i, 0, true);
                        return;
                    }
                }
            }
        });
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = 0;
                for (int i = 0; i < indexList.size(); i++) {
                    int tabIndex = tab.getPosition();
                    if (i == tabIndex) {
                        if (tabIndex == 0) {
                            gridLayoutManager.scrollToPositionWithOffset(0, 0);
                        } else {
                            index = indexList.get(i - 1);
                            gridLayoutManager.scrollToPositionWithOffset(index, 0);
                        }
                        return;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        myAppEditAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int i, Object o) {
                MenuEntity entity = (MenuEntity) o;
                myAppEditAdapter.removeItem(i);
                notifyTabApp(entity.getId());
            }

            @Override
            public void onLongClick(View view, int i, Object o) {

            }
        });
        tabAdapter.setOnItemOnclick(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int i, Object o) {
                MenuEntity entity = (MenuEntity) o;
                if (!entity.isShowHomepage()) {
                    if (myAppEditAdapter.getItemCount() >= 11) {
                        ToastUtil.makeText(getActivity(), "最多添加11个应用");
                        return;
                    }
                    myAppEditAdapter.add(myAppEditAdapter.getItemCount(), entity);//未添加的进行添加操作
                    myAppEditAdapter.notifyDataSetChanged();
                    entity.setShowHomepage(true);
                } else {
                    //进行删除操作
                    entity.setShowHomepage(false);
                    removeMyApp(entity.getId());
                }
                tabAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLongClick(View view, int i, Object o) {

            }
        });
        editPresenter = new MyAppEditPresenterImpl(this);
        editPresenter.getMyAppList();
        presenter = new MyAppPresenterImpl(this);
        presenter.getMyAllAppList();
        helper.attachToRecyclerView(myRecyclerView);
    }

    ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        //线性布局和网格布局都可以使用
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFrlg = 0;
            if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                dragFrlg = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            } else if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                dragFrlg = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            }

            return makeMovementFlags(dragFrlg, 0);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            //滑动事件  下面注释的代码，滑动后数据和条目错乱，被舍弃
//            Collections.swap(datas,viewHolder.getAdapterPosition(),target.getAdapterPosition());
//            ap.notifyItemMoved(viewHolder.getAdapterPosition(),target.getAdapterPosition());

            //得到当拖拽的viewHolder的Position
            int fromPosition = viewHolder.getAdapterPosition();
//            if (viewHolder != null && myAppEditAdapter.mList.get(viewHolder.getAdapterPosition()).getId() == 0) {
//                return false;
//            }
            //拿到当前拖拽到的item的viewHolder
            int toPosition = target.getAdapterPosition();
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(myAppEditAdapter.mList, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(myAppEditAdapter.mList, i, i - 1);
                }
            }
            myAppEditAdapter.notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            //侧滑删除可以使用；
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }

        /**
         * 长按选中Item的时候开始调用
         * 长按高亮
         * @param viewHolder
         * @param actionState
         */
        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
//            if (viewHolder != null && myAppEditAdapter.mList.get(viewHolder.getAdapterPosition()).getId() == 0) {
//                return;
//            }
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT);
                //获取系统震动服务//震动70毫秒
                Vibrator vib = (Vibrator) getActivity().getSystemService(Service.VIBRATOR_SERVICE);
                vib.vibrate(70);
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        /**
         * 手指松开的时候还原高亮
         * @param recyclerView
         * @param viewHolder
         */
        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            viewHolder.itemView.setBackgroundColor(0);
            myAppEditAdapter.notifyDataSetChanged();  //完成拖动后刷新适配器，这样拖动后删除就不会错乱
        }
    });


    @Override
    public void setTabList(List<MenuEntity> list) {
        for (int i = 0; i < list.size(); i++) {
            tablayout.addTab(tablayout.newTab());
            tablayout.getTabAt(i).setText(list.get(i).getName());
            tabList.add(list.get(i).getName());
        }

    }

    @Override
    public void setIndexList(List<Integer> list) {
        indexList.addAll(list);
    }

    @Override
    public void setHomePage(List<MenuEntity> list) {
        //不需要使用这里。。。
    }

    /**
     * tabrecycler删除的时候 ，上面的应用也跟着删除
     *
     * @param id
     */
    private void removeMyApp(int id) {
        for (int i = 0; i < myAppEditAdapter.mList.size(); i++) {
            if (myAppEditAdapter.mList.get(i).getId() == id) {
                myAppEditAdapter.mList.remove(i);
            }
        }
        myAppEditAdapter.notifyDataSetChanged();
    }

    /**
     * 上面删除的时候 ，tab下面的列表也要跟着删除
     */
    private void notifyTabApp(int id) {
        for (int i = 0; i < tabAdapter.mList.size(); i++) {
            if (tabAdapter.mList.get(i).getId() == id) {
                tabAdapter.mList.get(i).setShowHomepage(false);
            }
        }
        tabAdapter.notifyDataSetChanged();

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submit_tv) {
            loacDialog.show();
            editPresenter.submit();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loacDialog.cancel();
        loacDialog = null;
    }

    @Override
    public void success() {
        if (loacDialog != null) {
            loacDialog.cancel();
        }
        ToastUtil.makeText(getActivity(), "保存成功");
        getActivity().setResult(3001);
        getActivity().finish();
    }

    @Override
    public String getIds() {
        return myAppEditAdapter.getIds();
    }

    @Override
    public void setMyAllAppList(List<MenuEntity> list) {
        //获取所有的菜单
        tabAdapter.addAll(list);
        tabAdapter.notifyDataSetChanged();
    }

    @Override
    public void setAppList(List<MenuEntity> list) {
        //获取已保存的应用列表
//        if (list == null) {
//            list = new ArrayList<>();
//        }
//        MenuEntity entity = new MenuEntity();
//        entity.setId(0);
//        list.add(list.size(), entity);
        myAppEditAdapter.addAll(list);
        myAppEditAdapter.notifyDataSetChanged();
    }
}
