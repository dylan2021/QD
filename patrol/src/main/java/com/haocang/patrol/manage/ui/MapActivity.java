package com.haocang.patrol.manage.ui;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.view.View;
import android.widget.TextView;

import com.haocang.base.ui.BaseActivity;
import com.haocang.patrol.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 地图轨迹
 */
public class MapActivity extends BaseActivity {
    @Override
    protected void doOnCreate() {
        setContentView(R.layout.activity_map);
    }

    private void setView() {
        TextView titleNameTv = findViewById(R.id.title_common_tv);
        titleNameTv.setText(getIntent().getStringExtra("taskName") );
    }

}

