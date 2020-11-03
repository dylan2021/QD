package com.haocang.waterlink.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题：  圆形imageview
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/3/2613:22
 * 修 改 者：
 * 修改时间：
 */
@SuppressLint("AppCompatCustomView")
public class ImageViewPlus extends ImageView {
    private Paint mPaintBitmap = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap mRawBitmap;
    private BitmapShader mShader;
    private Matrix mMatrix = new Matrix();

    public ImageViewPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        Bitmap rawBitmap = getBitmap(getDrawable());
        if (rawBitmap != null) {
            int viewWidth = getWidth();
            int viewHeight = getHeight();
            int viewMinSize = Math.min(viewWidth, viewHeight);
            float dstWidth = viewMinSize;
            float dstHeight = viewMinSize;
            if (mShader == null || !rawBitmap.equals(mRawBitmap)) {
                mRawBitmap = rawBitmap;
                mShader = new BitmapShader(mRawBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            }
            if (mShader != null) {
                mMatrix.setScale(dstWidth / rawBitmap.getWidth(), dstHeight / rawBitmap.getHeight());
                mShader.setLocalMatrix(mMatrix);
            }
            mPaintBitmap.setShader(mShader);
            float radius = viewMinSize / 2.0f;
            canvas.drawCircle(radius, radius, radius, mPaintBitmap);
        } else {
            super.onDraw(canvas);
        }
    }

    private Bitmap getBitmap(final Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof ColorDrawable) {
            Rect rect = drawable.getBounds();
            int width = rect.right - rect.left;
            int height = rect.bottom - rect.top;
            int color = ((ColorDrawable) drawable).getColor();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawARGB(Color.alpha(color), Color.red(color), Color.green(color), Color.blue(color));
            return bitmap;
        } else {
            return null;
        }
    }
}
