package com.haocang.base.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;

import com.haocang.base.config.AppApplication;
import com.haocang.base.config.LibConfig;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvent;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

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
 * 创建时间：2018/12/1011:17 AM
 * 修  改  者：
 * 修改时间：
 */
public class VoiceUtil {
    // 语音合成对象
    private SpeechSynthesizer mTts;
    // 默认发音人
    private String DEFAUL_VOICER = "xiaoyan";
    private Context mContext;

    // 缓冲进度
    private int mPercentForBuffering = 0;
    // 播放进度
    private int mPercentForPlaying = 0;

    TextToSpeech tts;

    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    private String words;

    public VoiceUtil(Context context) {
        // 初始化合成对象
        mContext = context;
        mTts = SpeechSynthesizer.createSynthesizer(mContext, mTtsInitListener);
        if (mTts != null) {
            setParam();
        }
    }

    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
//            Log.d(TAG, "InitListener init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
//                showTip("初始化失败,错误码："+code);
            } else {
//                if (words != null) {
//                    code = mTts.startSpeaking(words, mTtsListener);
//                }
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
            }
        }
    };

    public void speak(String words) {
        if (!TextUtils.isEmpty(AppApplication.getInstance().getVoiceName())) {
            mTts.setParameter(SpeechConstant.VOICE_NAME, AppApplication.getInstance().getVoiceName());
        }
        if (!StringUtils.isEmpty(words)) {
            int code = mTts.startSpeaking(words, mTtsListener);
//            ErrorCode.SUCCESS;
//            ToastUtil.makeText(mContext, code+"");
        }

    }

    public void setParam() {
        SharedPreferences sp;
        sp = mContext.getSharedPreferences(LibConfig.HOME_SETUP, Context.MODE_PRIVATE);
//        String voicer = "xiaoyan";
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        if (mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            mTts.setParameter(SpeechConstant.TTS_DATA_NOTIFY, "1");
            // 设置在线合成发音人
            AppApplication.getInstance().setVoiceName(sp.getString(LibConfig.SP_KEY.VOICER, DEFAUL_VOICER));
            mTts.setParameter(SpeechConstant.VOICE_NAME, sp.getString(LibConfig.SP_KEY.VOICER, DEFAUL_VOICER));
            //设置合成语速
            mTts.setParameter(SpeechConstant.SPEED, "40");
            //设置合成音调
            mTts.setParameter(SpeechConstant.PITCH, "50");
            //设置合成音量
            mTts.setParameter(SpeechConstant.VOLUME, "80");
        } else {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
            // 设置本地合成发音人 voicer为空，默认通过语记界面指定发音人。
            mTts.setParameter(SpeechConstant.VOICE_NAME, "");
            /**
             * TODO 本地合成不设置语速、音调、音量，默认使用语记设置
             * 开发者如需自定义参数，请参考在线合成参数设置
             */
        }
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
//        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "pcm");
//        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, FileUtils.saveUnderFile() + "tts.pcm");
    }

    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
//            showTip("开始播放");
        }

        @Override
        public void onSpeakPaused() {
//            showTip("暂停播放");
        }

        @Override
        public void onSpeakResumed() {
//            showTip("继续播放");
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
            // 合成进度
            mPercentForBuffering = percent;
//            showTip(String.format(getString(R.string.tts_toast_format),
//                    mPercentForBuffering, mPercentForPlaying));
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度
            mPercentForPlaying = percent;
//            showTip(String.format(getString(R.string.tts_toast_format),
//                    mPercentForBuffering, mPercentForPlaying));

//            SpannableStringBuilder style=new SpannableStringBuilder(texts);
//            Log.e(TAG,"beginPos = "+beginPos +"  endPos = "+endPos);
//            style.setSpan(new BackgroundColorSpan(Color.RED),beginPos,endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            ((EditText) findViewById(R.id.tts_text)).setText(style);
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {
//                showTip("播放完成");
                if (mOnSpeakCompleteListener != null) {
                    mOnSpeakCompleteListener.speakComplete();
                }
            } else if (error != null) {
//                showTip(error.getPlainDescription(true));

            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}

            if (SpeechEvent.EVENT_TTS_BUFFER == eventType) {
                byte[] buf = obj.getByteArray(SpeechEvent.KEY_EVENT_TTS_BUFFER);
//                Log.e("MscSpeechLog", "buf is =" + buf);
            }

        }
    };

    public void onDestroy() {
        if (null != mTts) {
            mTts.stopSpeaking();
            // 退出时释放连接
            mTts.destroy();
        }
    }

    private OnSpeakCompleteListener mOnSpeakCompleteListener;

    public OnSpeakCompleteListener getmOnSpeakCompleteListener() {
        return mOnSpeakCompleteListener;
    }

    public void setmOnSpeakCompleteListener(OnSpeakCompleteListener mOnSpeakCompleteListener) {
        this.mOnSpeakCompleteListener = mOnSpeakCompleteListener;
    }


    public interface OnSpeakCompleteListener {
        void speakComplete();
    }
}
