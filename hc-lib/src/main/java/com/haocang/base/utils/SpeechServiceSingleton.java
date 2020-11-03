package com.haocang.base.utils;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.haocang.base.R;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;


//

public class SpeechServiceSingleton {
    private RecognizerDialog dialog;
    private Context mContext;

    public SpeechServiceSingleton(Context ctx, final OnSpeechResult onSpeechResult) {
        mContext = ctx;
        dialog = new RecognizerDialog(ctx, null);
        dialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");//设置语言
        dialog.setParameter(SpeechConstant.ACCENT, "mandarin");//
        dialog.setParameter(SpeechConstant.NET_TIMEOUT, "30000");//设置连接时间
        dialog.setParameter(SpeechConstant.KEY_SPEECH_TIMEOUT, "30000");//设置说话最长时间
        dialog.setParameter(SpeechConstant.VAD_BOS, "5000");//设置没声音的时候等待时间
        dialog.setParameter(SpeechConstant.VAD_EOS, "1800");//设置说话停顿时间
        dialog.setParameter(SpeechConstant.ASR_PTT, "0");///设置是否显示标点0表示不显示,1表示显示
        dialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(final RecognizerResult recognizerResult, boolean b) {
                onSpeechResult.onSpeechResult(parseIatResult(recognizerResult.getResultString()));
            }

            @Override
            public void onError(SpeechError speechError) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            dialog.cancel();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 3000);
            }
        });
    }


    private static void printResult(RecognizerResult results) {
        String text = parseIatResult(results.getResultString());
        String tk = text;
//         自动填写地址
    }

    private OnSpeechResult onSpeechResult;

    public void show(boolean isFlowMode) {
        try {
            dialog.show();
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            LinearLayout ll = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.speech_cover_dialog, null);
            params.gravity = Gravity.BOTTOM;
            dialog.getWindow().addContentView(ll, params);
            if (isFlowMode) {
                dialog.getWindow().getDecorView().setVisibility(View.GONE);
            } else {
                dialog.getWindow().getDecorView().setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        try {
            dialog.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void dismiss() {
        try {
            dialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnSpeechResult {
        void onSpeechResult(String result);
    }


    public static String parseIatResult(String json) {
        StringBuffer ret = new StringBuffer();
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);
            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                // 转写结果词，默认使用第一个结果
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                JSONObject obj = items.getJSONObject(0);
                ret.append(obj.getString("w"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.toString();
    }


}