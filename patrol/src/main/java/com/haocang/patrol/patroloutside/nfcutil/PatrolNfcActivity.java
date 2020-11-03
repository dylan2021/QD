package com.haocang.patrol.patroloutside.nfcutil;

import android.content.Intent;
import android.graphics.Color;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.haocang.base.utils.ToastUtil;
import com.haocang.patrol.R;

import java.util.Arrays;

/**
 * author： Rick.Lau
 * time： 2018/9/4
 * E-mail：Rick.Lau@aliyun.com
 * description：
 * version:
 */
public class PatrolNfcActivity extends BaseNfcActivity {
    private TextView tvContent;
    public final static int PATROL_NFC = 2302;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_patrol_nfc);
        tvContent = findViewById(R.id.tv_content);
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (!ifNFCUse()) {
            return;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        //获取Tag
        Tag detectedTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        //获取Ndef实例
        Ndef ndef = Ndef.get(detectedTag);

        readNfcTag(intent);
    }

    /**
     * 检测工作,判断设备的NFC支持情况
     *
     * @return
     */
    protected Boolean ifNFCUse() {
        if (mNfcAdapter == null) {
            ToastUtil.makeText(this, "设备不支持NFC！");
            tvContent.setText("设备不支持NFC！");
            return false;
        }
        if (mNfcAdapter != null && !mNfcAdapter.isEnabled()) {
            ToastUtil.makeText(this, "请在系统设置中先启用NFC功能！");
            tvContent.setText("请在系统设置中先启用NFC功能！");
            return false;
        }
        return true;
    }

    /**
     * 读取NFC标签文本数据
     */
    private void readNfcTag(Intent intent) {
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                    NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage msgs[] = null;
//            int contentSize = 0;
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
//                    contentSize += msgs[i].toByteArray().length;
                }
            }
            try {
                if (msgs != null) {
                    NdefRecord record = msgs[0].getRecords()[0];
                    String textRecord = parseTextRecord(record);
                    Intent mIntent = new Intent();
                    mIntent.putExtra("NFCInfo", textRecord);
                    this.setResult(PATROL_NFC, mIntent);
                    finish();
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * 解析NDEF文本数据，从第三个字节开始，后面的文本数据
     *
     * @param ndefRecord
     * @return
     */
    public static String parseTextRecord(NdefRecord ndefRecord) {
        /**
         * 判断数据是否为NDEF格式
         */
        //判断TNF
        if (ndefRecord.getTnf() != NdefRecord.TNF_WELL_KNOWN) {
            return null;
        }
        //判断可变的长度的类型
        if (!Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
            return null;
        }
        try {
            //获得字节数组，然后进行分析
            byte[] payload = ndefRecord.getPayload();
            //下面开始NDEF文本数据第一个字节，状态字节
            //判断文本是基于UTF-8还是UTF-16的，取第一个字节"位与"上16进制的80，16进制的80也就是最高位是1，
            //其他位都是0，所以进行"位与"运算后就会保留最高位
            String textEncoding = ((payload[0] & 0x80) == 0) ? "UTF-8" : "UTF-16";
            //3f最高两位是0，第六位是1，所以进行"位与"运算后获得第六位
            int languageCodeLength = payload[0] & 0x3f;
            //下面开始NDEF文本数据第二个字节，语言编码
            //获得语言编码
            String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
            //下面开始NDEF文本数据后面的字节，解析出文本
            String textRecord = new String(payload, languageCodeLength + 1,
                    payload.length - languageCodeLength - 1, textEncoding);
            return textRecord;
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }
}
