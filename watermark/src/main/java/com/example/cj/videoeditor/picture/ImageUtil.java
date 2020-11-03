package com.example.cj.videoeditor.picture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.cj.videoeditor.utils.DensityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtil {


    public static Bitmap fileInputStream(String path) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(fis);
    }

    public static void saveBitmap(Bitmap bitmap, String path, String filename) throws IOException {
        File file = new File(path + filename);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置水印图片到左下角
     *
     * @param src
     * @param watermark
     * @param paddingLeft
     * @param paddingBottom
     * @return
     */
    public static Bitmap createWaterMaskLeftBottom(
            Context context, Bitmap src, Bitmap watermark,
            int paddingLeft, int paddingBottom) {
        return createWaterMaskBitmap(src, watermark, DensityUtils.dp2px(context, 10),
                src.getHeight() - watermark.getHeight() - DensityUtils.dp2px(context, paddingBottom));
    }

    /**
     * 绘制水印图片
     *
     * @param src         原图
     * @param watermark   水印
     * @param paddingLeft
     * @param paddingTop
     * @return
     */
    private static Bitmap createWaterMaskBitmap(Bitmap src, Bitmap watermark,
                                                int paddingLeft, int paddingTop) {
        if (src == null) {
            return null;
        }
        int width = src.getWidth();
        int height = src.getHeight();
        //创建一个bitmap
        Bitmap newb = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        //将该图片作为画布
        Canvas canvas = new Canvas(newb);
        //在画布 0，0坐标上开始绘制原始图片
        canvas.drawBitmap(src, 0, 0, null);
        //在画布上绘制水印图片
        canvas.drawBitmap(watermark, paddingLeft, paddingTop, null);
        // 保存
        canvas.save();
//        canvas.save(Canvas.ALL_SAVE_FLAG);
        // 存储
        canvas.restore();
        return newb;
    }

}
