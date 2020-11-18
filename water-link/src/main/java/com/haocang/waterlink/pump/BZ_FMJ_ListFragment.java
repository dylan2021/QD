package com.haocang.waterlink.pump;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.utils.StringUtils;
import com.haocang.base.utils.ToastUtil;
import com.haocang.waterlink.R;
import com.haocang.waterlink.constant.HomeUrlConst;
import com.haocang.waterlink.utils.TextUtilsMy;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.util.HashMap;

/*
   泵站列表,阀门井列表
 */
@Route(path = "/pump/pumplist")
public class BZ_FMJ_ListFragment extends Fragment implements BaseRefreshListener {
    private EditText queryEt;
    private PullToRefreshLayout refreshLayout;
    private RecyclerView equimentRv;
    private TextView titleNameTv;
    private BZ_FMJ_ListAdapter adapter;
    private boolean isTypeBZ = true;
    @Autowired
    String title;
    private HashMap<Object, Object> map = new HashMap<>();
    private FragmentActivity context;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        context = getActivity();
        ARouter.getInstance().inject(this);
        isTypeBZ = "泵站工况".equals(title);
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_home_pump, null);
        initView(view);

        map.put("pageSize", 10);
        map.put("currentPage", 1);
        getData();
        return view;
    }

    private void initView(View view) {
        titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(title);

        refreshLayout = view.findViewById(R.id.pulltorefreshlayout);
        refreshLayout.setRefreshListener(this);
        equimentRv = view.findViewById(R.id.recyclerview);
        adapter = new BZ_FMJ_ListAdapter(R.layout.item_bz_fmj, isTypeBZ);
        equimentRv.setLayoutManager(new LinearLayoutManager(context));
        equimentRv.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position, Object item) {
                BZ_FMJ_Bean.ItemsBean info = (BZ_FMJ_Bean.ItemsBean) item;
                Intent intent = new Intent(context, BZ_FMJ_DeviceListActivity.class);
                intent.putExtra("processId", info.getProcessId());
                intent.putExtra("title", info.getProcessName());
                intent.putExtra("isTypeBZ", isTypeBZ);
                startActivity(intent);

            }

            @Override
            public void onLongClick(View view, int position, Object item) {

            }
        });

        queryEt = view.findViewById(R.id.patrol_query_et);
        queryEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    String searchStr = queryEt.getText().toString();
                    map.put("query", StringUtils.utfCode(searchStr));
                    refresh();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void refresh() {
        map.put("pageSize", 10);
        getData();
    }

    private void getData() {
        CommonModel<BZ_FMJ_Bean> progressModel = new CommonModelImpl<>();
        String url = isTypeBZ ? HomeUrlConst.URL_BZ : HomeUrlConst.URL_FMJ;
        progressModel.setContext(context)
                .setEntityType(BZ_FMJ_Bean.class)
                .setUrl(url)
                .setParamMap(map)
                .setEntityListener(new GetEntityListener<BZ_FMJ_Bean>() {
                    @Override
                    public void success(final BZ_FMJ_Bean entity) {
                        TextUtilsMy.finish(refreshLayout);
                        adapter.clear();
                        adapter.addAll(entity.getItems());
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void fail(final String err) {
                        TextUtilsMy.finish(refreshLayout);
                        ToastUtil.makeText(context, R.string.request_faild_retry);
                    }
                })
                .getEntityNew();
    }

    @Override
    public void loadMore() {
        map.put("pageSize", Integer.valueOf(map.get("pageSize").toString()) + 10);
        getData();
    }
}
