package com.haocang.waterlink.self.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.haocang.actionanalysis.utils.ActionAnalysisUtil;
import com.haocang.base.config.AppApplication;
import com.haocang.base.config.LibConfig;
import com.haocang.base.config.OnReceiveVoiceListener;
import com.haocang.base.ui.BaseActivity;
import com.haocang.base.widgets.MyGridLayoutManager;
import com.haocang.waterlink.R;
import com.haocang.waterlink.constant.WaterLinkConstant;
import com.haocang.waterlink.home.bean.MenuEntity;
import com.haocang.waterlink.self.adapter.VoicerSetAdapter;


/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 公司地址：中国上海市徐汇区云锦路500号绿地汇中心A座20楼2001
 * 网址：http://www.haocang.com/
 * 标        题：
 * 部        门：产品研发部
 * 版        本： 1.0
 * 创  建  者：whhc
 * 创建时间：2018/12/108:45 PM
 * 修  改  者：
 * 修改时间：
 */
public class VoicerSetFragment extends Fragment implements View.OnClickListener {

    public static boolean isMenu = false;
    private ImageView firstIv;
    private ImageView secondIv;
    private SharedPreferences sp;
    private ImageView voiceSetIv;
    private SeekBar seekbar;
    /**
     * 男声.
     */
    public static String VOICER_MALE = "xiaoyu";
    /**
     * 女声，默认女声.
     */
    public static String VOICER_FEMALE = "xiaoyan";

    /**
     * 语音灵敏度设置
     */
    public static String VOICER_MAX;


    private RecyclerView recyclerview;
    private VoicerSetAdapter voicerAdapter;

    public static final int THRESHHOLD_DEFAULT_VALUE = 16;

    public static final int THRESHHOLD_MIN_VALUE = 5;

    /**
     * 初始化.
     *
     * @param inflater           .
     * @param container          .
     * @param savedInstanceState .
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.self_voicerset_fragment, null);
        sp = getActivity().getSharedPreferences(LibConfig.HOME_SETUP, Context.MODE_PRIVATE);
        initView(view);
        return view;
    }

    private void initView(View view) {
        TextView titleTv = view.findViewById(R.id.title_common_tv);
        titleTv.setText(R.string.self_voicer_set);
        view.findViewById(R.id.first_ll).setOnClickListener(this);
        view.findViewById(R.id.second_ll).setOnClickListener(this);
        view.findViewById(R.id.voice_set_ll).setOnClickListener(this);
        firstIv = view.findViewById(R.id.first_iv);
        secondIv = view.findViewById(R.id.second_iv);
        voiceSetIv = view.findViewById(R.id.voice_close_iv);
        seekbar = view.findViewById(R.id.seekbar);
        recyclerview = view.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new MyGridLayoutManager(getActivity(), 2));
        voicerAdapter = new VoicerSetAdapter(R.layout.adapter_self_voicerset);
        recyclerview.setAdapter(voicerAdapter);
        /**
         * 默认女声.
         */
        if (VOICER_FEMALE.equals(sp.getString(LibConfig.SP_KEY.VOICER, VOICER_FEMALE))) {
            setSecond();
        } else {
            setFirst();
        }
        if (sp.getInt(VOICER_MAX, 0) >= 0) {
            seekbar.setProgress(sp.getInt(VOICER_MAX, THRESHHOLD_DEFAULT_VALUE));
        } else {
            seekbar.setProgress(THRESHHOLD_DEFAULT_VALUE);
        }
        setVoiceSetBackgroud();
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                AppApplication.getInstance().setThreshhold(progress + THRESHHOLD_MIN_VALUE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putInt(VOICER_MAX, progress);
                edit.commit();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        getVoicerKeywordList();
    }

    private void setVoiceSetBackgroud() {
        boolean voiceSetFlag = sp.getBoolean(WaterLinkConstant.SP_KEY.VOICE_SET, false);
        if (voiceSetFlag) {
            voiceSetIv.setBackgroundResource(R.drawable.icon_voicerset_switchblue);
        } else {
            voiceSetIv.setBackgroundResource(R.drawable.icon_voicerset_switchgrey);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.first_ll) {
            setFirst();
        } else if (view.getId() == R.id.second_ll) {
            setSecond();
        }
        if (view.getId() == R.id.voice_set_ll) {
            toggleVoice();
        }
    }

    private void toggleVoice() {
        boolean voiceSetFlag = sp.getBoolean(WaterLinkConstant.SP_KEY.VOICE_SET, false);
        if (voiceSetFlag) {
            /**
             * 如果原本是开着的，则关闭
             */
            AppApplication.getInstance().setOnReceiveVoiceListener(null);
            voiceSetIv.setBackgroundResource(R.drawable.icon_voicerset_switchgrey);
            SharedPreferences.Editor edit = sp.edit();
            edit.putBoolean(WaterLinkConstant.SP_KEY.VOICE_SET, false);
            edit.commit();
        } else {
            /**
             *
             */
            if (AppApplication.getInstance().getOnReceiveVoiceListener() == null) {
                AppApplication.getInstance().setOnReceiveVoiceListener(new OnReceiveVoiceListener() {
                    @Override
                    public void receiveVoice(String sentence) {
                        new ActionAnalysisUtil().analysis(sentence);
                    }
                });
            }
            voiceSetIv.setBackgroundResource(R.drawable.icon_voicerset_switchblue);
            SharedPreferences.Editor edit = sp.edit();
            edit.putBoolean(WaterLinkConstant.SP_KEY.VOICE_SET, true);
            edit.commit();
        }
    }

    private void setSecond() {
        firstIv.setVisibility(View.INVISIBLE);
        secondIv.setVisibility(View.VISIBLE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(LibConfig.SP_KEY.VOICER, VOICER_FEMALE);
        edit.commit();
        setVoiceParam();
    }

    private void setFirst() {
        firstIv.setVisibility(View.VISIBLE);
        secondIv.setVisibility(View.INVISIBLE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(LibConfig.SP_KEY.VOICER, VOICER_MALE);
        edit.commit();
        setVoiceParam();
    }

    public void setVoiceParam() {
        BaseActivity activity = (BaseActivity) getActivity();
        activity.setVoiceParam();
    }

    private void getVoicerKeywordList() {
        String[] keywordName = getActivity().getResources().getStringArray(R.array.voicerset_name);
        String[] keywordValue = getActivity().getResources().getStringArray(R.array.voicerset_value);
        for (int i = 0; i < keywordName.length && i < keywordValue.length; i++) {
            MenuEntity entity = new MenuEntity();
            entity.setTitle(keywordValue[i]);
            entity.setName(keywordName[i]);
            voicerAdapter.add(entity);
        }
        voicerAdapter.notifyDataSetChanged();
    }

}
