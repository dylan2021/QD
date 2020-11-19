package com.haocang.curve.collection.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haocang.base.config.ArouterPathConstants;
import com.haocang.base.utils.ToastUtil;
import com.haocang.curve.R;
import com.haocang.curve.collection.adapter.GignleCollectionAdapter;
import com.haocang.curve.collection.adapter.GignlePointCollectionAdapter;
import com.haocang.curve.collection.bean.PointList;
import com.haocang.curve.collection.bean.SignleCurve;
import com.haocang.curve.collection.iview.SignleCollectionView;
import com.haocang.curve.collection.presenter.SignleCollectionPresenter;
import com.haocang.curve.collection.presenter.impl.SignleCollectionPresenterImpl;
import com.haocang.curve.main.bean.CurveConstans;
import com.haocang.curve.more.bean.PointEntity;
import com.haocang.maonlib.base.config.MangoConst;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * 水质查询,能耗查询,实时状态：
 */

@Route(path = "/curve/SignleCollectionFragment")
public class SignleCollectionFragment extends Fragment implements SignleCollectionView,
        View.OnClickListener, BaseRefreshListener {
    private GignleCollectionAdapter mAdapter;
    private GignlePointCollectionAdapter mPointAdapter;
    private SignleCollectionPresenter signleCollectionPresenter;
    private PullToRefreshLayout pullToRefreshLayout;
    private EditText queryEt;
    Map<String, Object> map = new HashMap<>();
    private FragmentActivity context;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        context = getActivity();
        View view = inflater.inflate(R.layout.curve_signlecollection_fragment, null);

        ((TextView) view.findViewById(R.id.title_common_tv)).setText("数据列表");

        map.put("pageSize", "10");
        map.put("datype", "");
        map.put("datasource", "AUTO,INPUT,CALC");
        map.put("currentPage", "1");
        if (MangoConst.CURVE_TYPE.equals("1")) {
            map.put("categoryId", "1");
        } else if (MangoConst.CURVE_TYPE.equals("2")) {
            map.put("categoryId", "3");
        } else {//测点列表
            map.put("datasource", "AUTO");
        }

        initView(view);
        initData();
        return view;
    }

    private void initView(final View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        mAdapter = new GignleCollectionAdapter(this);

        mPointAdapter = new GignlePointCollectionAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mPointAdapter);
        queryEt = view.findViewById(R.id.query_et);
        queryEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    refresh();
                    return true;
                }
                return false;
            }
        });
        signleCollectionPresenter = new SignleCollectionPresenterImpl(this);
        signleCollectionPresenter.getPoint(map);
        pullToRefreshLayout = view.findViewById(R.id.pulltorefreshlayout);
        pullToRefreshLayout.setRefreshListener(this);
        view.findViewById(R.id.see_curve_tv).setOnClickListener(this);

    }

    private List<SignleCurve> selectedList;
    private List<String> selectPointIdList = new ArrayList<>();

    private List<PointList> selectedPointList;

    private void initData() {
        Type type = new TypeToken<List<PointEntity>>() {
        }.getType();
        List<PointEntity> selectedPoints = new Gson()
                .fromJson(context.getIntent().getStringExtra("selectedPointsStr"), type);
        if (selectedPoints != null) {
            selectedList = new ArrayList<>();
            selectPointIdList.clear();
            for (PointEntity entity : selectedPoints) {
                SignleCurve curve = new SignleCurve();
                curve.setName(entity.getMpointName());
                String mpointId = entity.getMpointId();
                if (!TextUtils.isEmpty(mpointId)) {
                    // 转化接口请求的mpointId为曲线需要的id
                    curve.setId(Integer.parseInt(mpointId));
                } else {
                    curve.setId(entity.getId());
                }
                curve.setSelect(true);
                curve.setType(CurveConstans.TYPE_DATA);
                curve.setSiteName(entity.getSiteName());
                curve.setParentName(entity.getSiteName());
                selectedList.add(curve);
                selectPointIdList.add(CurveConstans.CHARTNAME_PRE + mpointId);
            }
        }
        signleCollectionPresenter.addSelectedPoints();
    }

    @Override
    public void refresh() {
        mPointAdapter.clear();
        map.put("currentPage", "1");
        signleCollectionPresenter.getPoint(map);
    }

    @Override
    public void loadMore() {
        map.put("currentPage", (Integer.valueOf(String.valueOf(map.get("currentPage"))) + 1) + "");
        signleCollectionPresenter.getPoint(map);
    }

    @Override
    public String getQueryName() {
        return queryEt.getText().toString();
    }

    @Override
    public void renderList(final List<SignleCurve> list) {
        mAdapter.clear();
        mAdapter.addAll(list);
        mAdapter.notifyDataSetChanged();
        pullToRefreshLayout.finishLoadMore();
        pullToRefreshLayout.finishRefresh();
    }

    @Override
    public void renderPointList(final List<PointList> list) {
        mPointAdapter.clear();
        mPointAdapter.addAll(list);
        mPointAdapter.notifyDataSetChanged();
        pullToRefreshLayout.finishLoadMore();
        pullToRefreshLayout.finishRefresh();
    }

    /**
     * @param entity 数据.
     */
    @Override
    public void onItemClick(final SignleCurve entity) {
        if (mAdapter.getSelectList().size() >= CurveConstans.MAX_CURVE_COUNT && !entity.isSelect()) {
            ToastUtil.makeText(getContext(), getString(R.string.curve_max_limit_tip));
        } else {
            entity.setSelect(!entity.isSelect());
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onItemClick(PointList entity) {
        if (mPointAdapter.getSelectList().size() >= CurveConstans.MAX_CURVE_COUNT && !entity.isSelect()) {
            ToastUtil.makeText(getContext(), getString(R.string.curve_max_limit_tip));
        } else {
            entity.setSelect(!entity.isSelect());
            mPointAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public List<SignleCurve> getSelectedList() {
        return selectedList;
    }


    @Override
    public List<PointList> getSelectedPointList() {
        return selectedPointList;
    }

    @Override
    public List<String> getSelectedIdList() {
        return selectPointIdList;
    }

    /**
     * @param v 被点击的控件.
     */
    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.see_curve_tv) {
            List<PointEntity> pointList = mPointAdapter.getSelectList();
            if (pointList == null || pointList.size() == 0) {
                ToastUtil.makeText(context, "请选择曲线");
                return;
            }
            Type type = new TypeToken<List<PointEntity>>() {
            }.getType();
            String selectedPointsStr = new Gson().toJson(pointList, type);
            if (!TextUtils.isEmpty(getIsHomeJump())) {
                Map<String, Object> map = new HashMap<>();
                map.put("selectedPointsStr", selectedPointsStr);
                startActivityForResult(map, context, ArouterPathConstants.Curve.CURVE_MAIN, CurveConstans.PICK_COLLECTION_REQUEST_CODE);
            } else {
                Intent intent = new Intent();
                intent.putExtra("selectedPointsStr", selectedPointsStr);
                ((Activity) getContext()).setResult(CurveConstans.PICK_COLLECTION_REQUEST_CODE, intent);
                ((Activity) getContext()).finish();
            }
        }
    }

    public void startActivityForResult(Map<String, Object> map, Activity activity, String path, int requestCode) {
        Postcard postcard = ARouter.getInstance().build(path);
        Iterator var4 = map.entrySet().iterator();
        while (var4.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry) var4.next();
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Boolean) {
                boolean b = (Boolean) value;
                postcard.withBoolean(key, b);
            } else if (value instanceof Integer) {
                int c = (Integer) value;
                postcard.withInt(key, c);
            } else if (entry.getValue() != null) {
                postcard.withString((String) entry.getKey(), entry.getValue().toString());
            }
        }
        postcard.navigation(activity, requestCode);
    }

    private String getIsHomeJump() {
        return context.getIntent().getStringExtra("main");
    }
}
