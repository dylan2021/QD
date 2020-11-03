package com.haocang.curve.more.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.Gson;
import com.haocang.base.adapter.ContactAdapter;
import com.haocang.base.bean.Contact;
import com.haocang.base.config.ArouterPathConstants;
import com.haocang.base.config.MethodConstants;
import com.haocang.base.http.AddParameters;
import com.haocang.base.http.OkHttpClientManager;
import com.haocang.base.utils.HanziToPinyin;
import com.haocang.base.utils.ProgressBarDialog;
import com.haocang.base.widgets.SideBar;
import com.haocang.curve.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区龙兰路277号一号楼9楼
 * 网址：http://www.haocang.com/
 * 标 题：区域选择
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：hy
 * 创建时间：2018/6/4 13:43
 * 修 改 者：
 * 修改时间：
 */
@Route(path = ArouterPathConstants.Curve.CURVE_SITE)
public class RegionSelectionFragment extends Fragment implements SideBar
        .OnTouchingLetterChangedListener, TextWatcher, View.OnClickListener, TextView.OnEditorActionListener {
    private ListView mListView;
    private ContactAdapter mAdapter;
    private TextView titleNameTv;
    private TextView commonTv;
    private List<String> historyIdList;
    private EditText queryEt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_region_selection, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        historyIdList = getSiteList();
        mListView = view.findViewById(R.id.school_friend_member);
        queryEt = view.findViewById(R.id.query_et);
        queryEt.setOnEditorActionListener(this);
        SideBar mSideBar = view.findViewById(R.id.school_friend_sidrbar);
        TextView mDialog = view.findViewById(R.id.school_friend_dialog);
        titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getResources().getString(R.string.curve_region_selection));
        commonTv = view.findViewById(R.id.common_tv);
        commonTv.setVisibility(View.VISIBLE);
        commonTv.setText(getString(R.string.base_complete));
        commonTv.setOnClickListener(this);
        mSideBar.setTextView(mDialog);
        mSideBar.setOnTouchingLetterChangedListener(this);
        getData();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact entity = mAdapter.getItem(position);
                if (entity.isSelect()) {
                    entity.setSelect(false);
                } else {
                    entity.setSelect(true);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onTouchingLetterChanged(String s) {
        int position = 0;
        // 该字母首次出现的位置
        if (mAdapter != null) {
            position = mAdapter.getPositionForSection(s.charAt(0));
        }
        if (position != -1) {
            mListView.setSelection(position);
        } else if (s.contains("#")) {
            mListView.setSelection(0);
        }
    }

    private void getData() {
        AddParameters addParameters = new AddParameters();
        addParameters.addParam("queryName", queryEt.getText().toString().trim());
        new OkHttpClientManager()
                .setUrl(MethodConstants.Uaa.BASE_PROCESSE + addParameters.formGet())
                .setLoadDialog(new ProgressBarDialog(getActivity()))
                .setOnNetWorkReponse(onNetworkResponse)
                .builder();
    }

    OkHttpClientManager.OnNetworkResponse onNetworkResponse = new OkHttpClientManager.OnNetworkResponse() {
        @Override
        public void onNetworkResponse(String result) {
            ArrayList<Contact> list = new ArrayList<>();
            try {
                JSONArray array = new JSONArray(result);
                for (int i = 0; i < array.length(); i++) {
                    Contact labelEntity = new Contact();
                    JSONObject object = array.optJSONObject(i);
                    labelEntity.setName(object.optString("name"));
                    labelEntity.setId(object.optInt("id"));
                    if (historyIdList.contains(object.optInt("id") + "")) {
                        labelEntity.setSelect(true);
                    } else {
                        labelEntity.setSelect(false);
                    }
                    labelEntity.setPinyin(HanziToPinyin.getPinYin(labelEntity.getName()));
                    list.add(labelEntity);
                }
                mAdapter = new ContactAdapter(mListView, list);
                mListView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onErrorResponse(Response response) {

        }
    };

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        if (mAdapter.getDatas().size() > 0) {
            intent.putExtra("processList", new Gson().toJson(mAdapter.getDatas()));
        }
        getActivity().setResult(PointScreePopupWindowActivity.REQUESTCODE, intent);
        getActivity().finish();

    }

    private List<String> getSiteList() {
        List<String> list = new ArrayList<>();
        String siteId = getActivity().getIntent().getStringExtra("siteid");
        if (!TextUtils.isEmpty(siteId)) {
            String[] sr = siteId.split(",");
            for (String s : sr) {
                list.add(s);
            }
        }
        return list;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//        if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
//            getData();
//            return true;
//        }
        if (v.getId() == R.id.query_et) {
            getData();
            return true;
        }
        return false;
    }
}
