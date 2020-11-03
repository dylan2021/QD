package com.haocang.waterlink.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Display;
import android.view.WindowManager;

import com.haocang.util.DimenUtil;
import com.haocang.waterlink.R;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题：
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：hy
 * 创建时间：${DATA} 13:09
 * 修 改 者：
 * 修改时间：
 */
public class FiveTextView extends androidx.appcompat.widget.AppCompatTextView {
    /**
     * 文字长度
     */
    private float textLength = 0f;
    /**
     * 滚动条长度
     */
    private float viewWidth = 0f;

    /**
     *  文本x轴 的坐标 
     */
    private float tx = 0f;
    /**
     *  文本Y轴的坐标 
     */
    private float ty = 0f;
    /**
     *  文本当前长度 
     */
    private float temp_tx1 = 0.0f;
    /**
     *  文本当前变换的长度 
     */
    private float temp_tx2 = 0x0f;
    /**
     *  文本滚动开关 
     */
    private boolean isStarting = false;
    /**
     *  画笔对象 
     */
    private Paint paint = null;
    /**
     *  显示的文字 
     */
    private String text = "";
    /**
     *  文本滚动速度 
     **/
    private float sudu;

    public FiveTextView(Context context) {
        super(context);
    }

    public FiveTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     *      * 初始化自动滚动条,每次改变文字内容时，都需要重新初始化一次
     *      * 
     *      * @param windowManager
     *      *            获取屏幕
     *      * @param text
     *      *            显示的内容
     *      * @param type
     *      *            文字滚动的类型
     *      
     */
    public void initScrollTextView(WindowManager windowManager, String text, float su) {
// 得到画笔,获取父类的textPaint
        paint = this.getPaint();
// 得到文字
        this.text = text;
        this.sudu = su;
        textLength = paint.measureText(text);// 获得当前文本字符串长度
        viewWidth = this.getWidth();// 获取宽度return mRight - mLeft;
        if (viewWidth == 0) {
// 获取当前屏幕的属性
            Display display = windowManager.getDefaultDisplay();
            viewWidth = display.getWidth() / 2;// 获取屏幕宽度  viewWidth 是滚动的开始位置，需要修改的
// 可再此入手
        }
        tx = textLength;
        temp_tx1 = viewWidth + textLength;
        temp_tx2 = viewWidth + textLength * 2;// 自己定义，文字变化多少
// // 文字的大小+距顶部的距离
        ty = this.getTextSize() + this.getPaddingTop();
    }


    /**
     *      * 开始滚动
     *      
     */
    public void starScroll() {
// 开始滚动
        isStarting = true;
        this.invalidate();// 刷新屏幕
    }

    /**
     * * 停止方法,停止滚动
     *      
     */
    public void stopScroll() {
// 停止滚动
        isStarting = false;
        this.invalidate();// 刷新屏幕
    }


    /**
     *  重写onDraw方法 
     */
    @Override
    protected void onDraw(Canvas canvas) {
        if (isStarting) {
// A-Alpha透明度/R-Read红色/g-Green绿色/b-Blue蓝色
            paint.setColor(getContext().getResources().getColor(R.color.homehead_note_textcolor));
            int textSize = getContext().getResources().getInteger(R.integer.homehead_note_textsize);
            paint.setTextSize(DimenUtil.dp2px(getContext(), textSize));
            canvas.drawText(text, temp_tx1 - tx, ty, paint);
            tx += sudu;
// 当文字滚动到屏幕的最左边
            if (tx > temp_tx2) {
// 把文字设置到最右边开始
                tx = textLength;
            }
            this.invalidate();// 刷新屏幕
        }
        super.onDraw(canvas);
    }
}
