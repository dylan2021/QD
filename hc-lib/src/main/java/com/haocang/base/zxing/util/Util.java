package com.haocang.base.zxing.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.haocang.base.utils.PermissionsProcessingUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Util {

    public static Activity currentActivity = null;

    /**
     * 获得屏幕宽度
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static int getWindowWidthPix() {
        int ver = Build.VERSION.SDK_INT;
        Display display = currentActivity.getWindowManager()
                .getDefaultDisplay();
        int width = 0;
        if (ver < 13) {
            DisplayMetrics dm = new DisplayMetrics();
            display.getMetrics(dm);
            width = dm.widthPixels;
        } else {
            Point point = new Point();
            display.getSize(point);
            width = point.x;
        }
        return width;
    }

    /**
     * 获得屏幕高度
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static int getWindowHeightPix() {
        int ver = Build.VERSION.SDK_INT;
        Display display = currentActivity.getWindowManager()
                .getDefaultDisplay();
        int height = 0;
        if (ver < 13) {
            DisplayMetrics dm = new DisplayMetrics();
            display.getMetrics(dm);
            height = dm.heightPixels;
        } else {
            Point point = new Point();
            display.getSize(point);
            height = point.y;
        }
        return height;
    }

    public static String getIMEI(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String imeiCode = tm.getDeviceId();
            return imeiCode;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static Bitmap Create2DCode(String str) {
        //生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        BitMatrix matrix = null;
        try {
            //无法解析中文
            matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, 300, 300);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        //二维矩阵转为一维像素数组,也就是一直横着排了
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                } else {
                    pixels[y * width + x] = 0xffffffff;
                }

            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    public static File saveImage(Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "Mango");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
