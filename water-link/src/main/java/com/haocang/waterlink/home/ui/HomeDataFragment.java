package com.haocang.waterlink.home.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.config.LibConfig;
import com.haocang.base.config.LibConstants;
import com.haocang.base.utils.ARouterUtil;
import com.haocang.datamonitor.constants.ArouterPath;
import com.haocang.waterlink.R;
import com.haocang.waterlink.home.adapter.HomeDataAdapter;
import com.haocang.waterlink.home.bean.HomeDataEntity;
import com.haocang.waterlink.home.iview.HomeDataView;
import com.haocang.waterlink.home.presenter.HomeDataPresenter;
import com.haocang.waterlink.home.presenter.impl.HomeDataPresenterImpl;

import org.java_websocket.WebSocket;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.functions.Action1;
import ua.naiksoftware.stomp.LifecycleEvent;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.client.StompClient;
import ua.naiksoftware.stomp.client.StompMessage;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 公司地址：中国上海市徐汇区云锦路500号绿地汇中心A座20楼2001
 * 网址：http://www.haocang.com/
 * 标        题：关键指标实时值
 * 部        门：产品研发部
 * 版        本： 1.0
 * 创  建  者：whhc
 * 创建时间：2018/8/1下午5:24
 * 修  改  者：
 * 修改时间：
 */
public class HomeDataFragment
        extends Fragment implements HomeDataView, BaseAdapter.OnItemClickListener, View.OnClickListener {

    private HomeDataAdapter adapter;
    private HomeDataPresenter presenter;
    private TextView nodataTv;
    private RecyclerView recyclerView;
    private StompClient mStompClient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.home_data_fragment, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        adapter = new HomeDataAdapter(getActivity());
        recyclerView = view.findViewById(R.id.home_data_rv);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(adapter);
        nodataTv = view.findViewById(R.id.nodata_tv);
        presenter = new HomeDataPresenterImpl(this);
        adapter.setOnItemClickListener(this);
        view.findViewById(R.id.home_curve_iv).setOnClickListener(this);
        view.findViewById(R.id.home_curve_ll).setOnClickListener(this);
        presenter.getData();
    }

    Handler handler = new Handler();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            presenter.getData();
            try {
                handler.postDelayed(runnable, 10000);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    @Override
    public void onResume() {
        super.onResume();
//        handler.postDelayed(runnable, 10000);
    }

    public void onPause() {
        super.onPause();
        if (mStompClient != null) {
            mStompClient.disconnect();
        }
    }


    @Override
    public String getIds() {
        return adapter.getIds();
    }

    @Override
    public void renderData(final List<HomeDataEntity> list) {
        if (list != null && list.size() > 0) {
            adapter.clear();
            recyclerView.setVisibility(View.VISIBLE);
            for (HomeDataEntity entity : list) {
                adapter.add(entity);
            }
            if (adapter.getItemCount() > 0) {
                nodataTv.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            } else {
                nodataTv.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
            adapter.notifyDataSetChanged();
            presenter.getSubscription();
        } else {
            nodataTv.setVisibility(View.VISIBLE);
        }
    }

    private String path;

    @Override
    public void setTopic(String topic) {
        startStompTopic(topic);
    }

    private void startStompTopic(String path) {
        this.path = path;
        createStompClient();
        registerStompTopic(path);
    }


    private void createStompClient() {
        Map<String, String> map = new HashMap<>();
        map.put("Cookie", LibConfig.getCookie());
        mStompClient = Stomp.over(WebSocket.class, LibConstants.WS_ADDRESS_IP, map);
        mStompClient.connect();
        mStompClient.lifecycle().subscribe(new Action1<LifecycleEvent>() {
            @Override
            public void call(LifecycleEvent lifecycleEvent) {
                switch (lifecycleEvent.getType()) {
                    case OPENED:
                        Log.i("sssssss", "连接成功");
                        break;
                    case ERROR:
                        Log.i("sssssss", "连接异常" + lifecycleEvent.getException());
                        break;
                    case CLOSED:
                        startStompTopic(path);
                        Log.i("sssssss", "连接关闭" + lifecycleEvent.getException());
                        break;
                }
            }
        });
    }


    private void registerStompTopic(String path) {
        mStompClient.topic(path).subscribe(new Action1<StompMessage>() {
            @Override
            public void call(StompMessage stompMessage) {
                Bundle bundle = new Bundle();
                bundle.putString("result", stompMessage.getPayload());
                Message message = new Message();
                message.setData(bundle);
                handler2.sendMessage(message);
            }
        });
    }


    Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            HomeDataEntity entity = new Gson().fromJson(bundle.getString("result"), HomeDataEntity.class);
            adapter.notifyData(entity);
        }
    };

    @Override
    public void onClick(View view, int i, Object item) {
        HomeDataEntity entity = (HomeDataEntity) item;
        if (entity.getId() == null) {
            entity.setId(entity.getMpointId());
        }
        List<HomeDataEntity> pointList = new ArrayList<>();
        pointList.add(entity);
        Type type = new TypeToken<List<HomeDataEntity>>() {
        }.getType();

        String selectedPointsStr = new Gson().toJson(pointList, type);
        Map<String, Object> map = new HashMap<>();
        map.put("selectedPointsStr", selectedPointsStr);
        ARouterUtil.toActivity(map, ArouterPath.OuterPath.CURVE_MAIN);
    }

    @Override
    public void onLongClick(View view, int i, Object o) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.home_curve_iv || v.getId() == R.id.home_curve_ll) {
            toCurve();
        }
    }

    private void toCurve() {
        List<HomeDataEntity> pointList = adapter.mList;
        for (HomeDataEntity entity : pointList) {
            if (entity.getId() == null) {
                entity.setId(entity.getMpointId());
            }
        }
        Type type = new TypeToken<List<HomeDataEntity>>() {
        }.getType();
        String selectedPointsStr = new Gson().toJson(pointList, type);
        Map<String, Object> map = new HashMap<>();
        map.put("selectedPointsStr", selectedPointsStr);
        ARouterUtil.toActivity(map, ArouterPath.OuterPath.CURVE_MAIN);
    }
}
