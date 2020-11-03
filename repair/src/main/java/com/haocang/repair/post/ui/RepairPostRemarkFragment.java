package com.haocang.repair.post.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haocang.base.config.ArouterPathConstants;
import com.haocang.base.config.LibConfig;
import com.haocang.base.config.LibConstants;
import com.haocang.base.utils.PermissionsProcessingUtil;
import com.haocang.base.utils.SpeechService;
import com.haocang.base.utils.ToastUtil;
import com.haocang.repair.R;
import com.haocang.repair.manage.bean.RepairConstans;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题：缺陷备注
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/4/2610:50
 * 修 改 者：
 * 修改时间：
 */

@Route(path = ArouterPathConstants.Repair.POST_REMARK)
public class RepairPostRemarkFragment extends Fragment
        implements View.OnClickListener,
        SpeechService.OnSpeechResult,
        PermissionsProcessingUtil.OnPermissionsCallback {


    private EditText remarkEdt;
    private TextView charLengthTv;//字符最大数值
    private TextView titleNameTv;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.repair_post_remark_fragment, null);
        initView(view);
        return view;
    }

    private void initView(final View view) {
        view.findViewById(R.id.audio_ll).setOnClickListener(this);
        remarkEdt = view.findViewById(R.id.remarks_edt);
        remarkEdt.addTextChangedListener(remarkTt);
        charLengthTv = view.findViewById(R.id.char_lenth_tv);
        titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getString(R.string.repair_add_remarks));
        view.findViewById(R.id.common_complete_tv).setOnClickListener(this);
        remarkEdt.setText(getRemark());
    }

    /**
     *
     */
    private TextWatcher remarkTt = new TextWatcher() {
        @Override
        public void beforeTextChanged(final CharSequence s,
                                      final int start,
                                      final int count,
                                      final int after) {

        }

        @Override
        public void onTextChanged(final CharSequence s,
                                  final int start,
                                  final int before,
                                  final int count) {

        }

        @Override
        public void afterTextChanged(final Editable s) {
            String result = s.toString();
            if (result.length() > LibConstants.Base.REMARK_MAX_LENGTH) {
                result = result.substring(0, result.length());
                remarkEdt.setText(result);
            }
            charLengthTv.setText(result.length() + "/" +
                    LibConstants.Base.REMARK_MAX_LENGTH);
            remarkEdt.setSelection(remarkEdt.getText().length());
        }
    };

    @Override
    public void onClick(final View v) {
        int id = v.getId();
        if (id == R.id.audio_ll) {
            PermissionsProcessingUtil
                    .setPermissions(this, LibConfig.AUDIO, this);
        } else if (id == R.id.common_complete_tv) {
            Intent intent = new Intent();
            intent.putExtra("name", remarkEdt.getText().toString());
            getActivity()
                    .setResult(RepairConstans.StartActivityCode.REMARK_RESULT_CODE, intent);
            getActivity().finish();
        }
    }

    @Override
    public void callBack(final boolean flag, final String permission) {
        if (flag) {
            SpeechService.btnVoice(getActivity(), this);
        } else {
            ToastUtil.makeText(getActivity(),
                    getResources().getString(R.string.permissions_audio));
        }
    }

    @Override
    public void onSpeechResult(final String result) {
        String oldResult = remarkEdt.getText().toString();
        remarkEdt.setText(oldResult + result);
    }

    /**
     * @return 备注
     */
    private String getRemark() {
        return getActivity().getIntent().getStringExtra("remark");
    }
}
