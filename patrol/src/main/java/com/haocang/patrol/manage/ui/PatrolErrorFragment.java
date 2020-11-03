package com.haocang.patrol.manage.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.haocang.base.config.LibConfig;
import com.haocang.base.http.AddParameters;
import com.haocang.base.http.OkHttpClientManager;
import com.haocang.base.utils.ToastUtil;
import com.haocang.patrol.R;
import com.haocang.patrol.constants.PatrolMethod;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Response;


/**
 * 巡检异常提交
 */

public class PatrolErrorFragment extends Fragment implements View.OnClickListener {
    private EditText remarkEt;
    private TextView titleNameTv;
    private TextView commonTv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_patrol_error, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        titleNameTv = view.findViewById(R.id.title_common_tv);
        commonTv = view.findViewById(R.id.common_tv);
        titleNameTv.setText("添加备注");
        remarkEt = view.findViewById(R.id.remark_et);
        commonTv.setOnClickListener(this);
        commonTv.setVisibility(View.VISIBLE);
        commonTv.setText("保存");

    }

    @Override
    public void onClick(View view) {
        if (!TextUtils.isEmpty(remarkEt.getText().toString())) {
            submit();
        } else {
            ToastUtil.makeText(getActivity(), "请添加备注");
        }

    }

    private void submit() {
        String remark = remarkEt.getText().toString();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", getTaskId());
            jsonObject.put("comment", remark);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AddParameters addParameters = new AddParameters();
        new OkHttpClientManager()
                .setUrl(PatrolMethod.PATROL_COMMENT)
                .setRequestBody(addParameters.formBodyByObject(jsonObject))
                .setRequestMethod(LibConfig.HTTP_PUT)
                .setOnNetWorkReponse(new OkHttpClientManager.OnNetworkResponse() {
                    @Override
                    public void onNetworkResponse(String s) {
                        ToastUtil.makeText(getActivity(),"提交成功");
                        getActivity().finish();
                    }

                    @Override
                    public void onErrorResponse(Response response) {

                    }
                }).builder();

    }

    private int getTaskId() {
        return getActivity().getIntent().getIntExtra("taskId", 0);
    }

}
