package com.haocang.base.utils;

import android.content.Context;
import android.content.pm.PackageManager;

public class MetaDataUtil {
    public String getMetaData(Context context, String metaDataName, String defaultValue) {
        String channel = "";
        try {
            channel = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA).metaData.get(metaDataName) + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtils.isEmpty(channel)) {
            channel = defaultValue;
        }
        return channel;
    }
}
