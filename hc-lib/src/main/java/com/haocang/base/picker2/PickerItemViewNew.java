package com.haocang.base.picker2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

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
 * 创建时间：${DATA} 14:23
 * 修 改 者：
 * 修改时间：
 */
public class PickerItemViewNew extends View {

    /**
     * 变量
     */
    private List<ItemEntity> itemEntities;
    private float startY = 0;//记录手指按下时候的Y坐标
    private float itemHeight = 0;//每一个文本的高度（包括上下空白的地方）
    private float minFontSize;//最小的文字字体大小
    private float maxFontSize;//最大的文字字体大小
    private boolean isLoop = false;//是否循环
    private String lastValue;//
    private int defaultShowItemNum = 5;//默认显示的item个数，有改动的话不保证适配
    //用于根据高度设置最小最大文字的大小，范围2-80；
    private int minFontSizePercent = 35;
    private int maxFontSizePercent = 65;
    private int normalFontColor = Color.parseColor("#809b9b9b");//正常的文字颜色
    private int pressFontColor = Color.parseColor("#ff0cabdf");//选中的文字颜色
    private String defaultValue;//默认显示的值
    private boolean isSetList = false;//是否已经设置了参数数组，用于判定设定值的时候是设置默认值还是直接设置值

    /**
     * 外部接口
     */
    private OnStringListener onPitchOnChangeListener;//拖动的时候，返回当前选中的

    public PickerItemViewNew(Context context) {
        super(context);
    }

    public PickerItemViewNew(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PickerItemViewNew(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        itemHeight = getHeight() / defaultShowItemNum;
        minFontSize = itemHeight * minFontSizePercent / 100;
        maxFontSize = itemHeight * maxFontSizePercent / 100;
        if (itemEntities != null) {
            drawText(canvas);
        }
    }

    /**
     * 重写触摸监听
     *
     * @param event
     * @return
     */
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float y = event.getY() - startY;
                startY = event.getY();
                onPitchOnBack();
                deviationY(y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                //手指抬起的时候，计算并偏移Y轴，相当于对齐一下位置，让被选中的那个保持在画布中间
                if (itemEntities != null && itemEntities.size() > 0) {
                    float intervalY = (itemEntities.get(0).getY()) % itemHeight;
                    intervalY = (intervalY + itemHeight * 2) % itemHeight;
                    if (intervalY >= itemHeight / 2) {
                        deviationY((itemHeight - intervalY));
                    } else {
                        deviationY(-intervalY);
                    }
                    invalidate();
                    onPitchOnBack();
                }
                break;
        }
        return true;
    }


    /**
     * 绘制文本
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        for (int i = 0; i < itemEntities.size(); i++) {
            ItemEntity itemEntity = itemEntities.get(i);
            if ((itemEntity.getY() > (-itemHeight)) && itemEntity.getY() < itemHeight * 6) {
                //在范围内，绘制出来
                float itemCoreY = itemEntity.getY() + itemHeight / 2;
                float itemCoreX = getWidth() / 2;
                float size = getFontSize(itemEntity.getY());
                int color = normalFontColor;
                if (size == maxFontSize) {
                    color = pressFontColor;
                }
                Bitmap bitmap = str2Bitmap(itemEntity.getValue(), (int) size, color);
                float x = itemCoreX - bitmap.getWidth() / 2;
                float y = itemCoreY - bitmap.getHeight() / 2;
                canvas.drawBitmap(bitmap, x, y, null);
            }
        }
    }

    /**
     * 将文本绘制成一个bitmap,并居中
     *
     * @param testString
     * @param size
     * @param txtColor
     * @return
     */
    public Bitmap str2Bitmap(String testString, int size, int txtColor) {
        float x = size * testString.length() + 1;
        float y = size + 1;
        Bitmap bmp = Bitmap.createBitmap((int) x, (int) y, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Rect targetRect = new Rect(0, 0, (int) x, (int) y);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(size);
        paint.setColor(txtColor);
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int baseline = (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(testString, targetRect.centerX(), baseline, paint);
        return bmp;
    }

    /**
     * Y 轴偏移，即移动
     *
     * @param y
     */
    private void deviationY(float y) {
        if (itemEntities == null || itemEntities.size() <= 0) {
            return;
        }
        //是否循环
        if (isLoop) {
            //判定头部
            if ((itemEntities.get(0).getY() + y) >= -itemHeight) {
                ItemEntity itemEntity = itemEntities.get(itemEntities.size() - 1);
                itemEntities.remove(itemEntities.size() - 1);
                itemEntity.setY(itemEntities.get(0).getY() - itemHeight);
                itemEntities.add(0, itemEntity);
            }
            //判定尾部
            if ((itemEntities.get(itemEntities.size() - 1).getY() + y) <= itemHeight * 6) {
                ItemEntity itemEntity = itemEntities.get(0);
                itemEntities.remove(0);
                itemEntity.setY(itemEntities.get(itemEntities.size() - 1).getY() + itemHeight);
                itemEntities.add(itemEntities.size(), itemEntity);
            }

            if ((itemEntities.get(0).getY() + y) >= -itemHeight || (itemEntities.get(itemEntities.size() - 1).getY() + y) <= itemHeight * 6) {
                //如果头部或者底部还存在空缺，则需要再度调整，因为目前还没完成偏移操作，所以再次调整的时候依旧可以传入y，直到全部递归调整完成之后才执行偏移
                deviationY(y);
                return;
            }
        } else {
            //不可以循环，上下到顶了就不给滑了
            if ((itemEntities.get(0).getY() + y) > itemHeight * 2) {
                return;
            }
            if ((itemEntities.get(itemEntities.size() - 1).getY() + y) < itemHeight * 2) {
                return;
            }
        }
        for (int i = 0; i < itemEntities.size(); i++) {
            itemEntities.get(i).setY(itemEntities.get(i).getY() + y);
        }
    }

    /**
     * 计算Y轴不同位置的需要映射的字体大小
     *
     * @param y
     * @return
     */
    private float getFontSize(float y) {
        y = y + itemHeight / 2;
        if (y <= itemHeight / 2 || y > 4.5 * itemHeight) {
            return minFontSize;
        }
        if (y > itemHeight / 2 && y < itemHeight * 2) {
            //慢慢大
            return (float) ((maxFontSize - minFontSize) * (y - (itemHeight / 2)) / (1.5 * itemHeight) + minFontSize);
        }
        if (y >= itemHeight * 2 && y <= itemHeight * 3) {
            return maxFontSize;
        }
        if (y > itemHeight * 3 && y <= 4.5 * itemHeight) {
            //慢慢小
            return (float) (maxFontSize - (maxFontSize - minFontSize) * (y - itemHeight * 3) / (1.5 * itemHeight));
        }
        return minFontSize;
    }

    /**
     * 改变值的时候回调通知
     */
    private void onPitchOnBack() {
        if (onPitchOnChangeListener != null) {
            String str = getPitchOn();
            if (lastValue == null || !str.equals(lastValue)) {
                onPitchOnChangeListener.onClick(str);
                lastValue = str;
            }
        }
    }

    /**
     * 设置数组
     *
     * @param strings
     */
    public void setList(final List<String> strings) {
        if (strings == null || strings.size() <= 0) {
            return;
        }
        if (strings.size() < 9) {
            isLoop = false;
        } else {
            isLoop = true;
        }
        post(new Runnable() {
            @Override
            public void run() {
                itemHeight = getHeight() / 5;
                itemEntities = new ArrayList<>();
                for (int i = 0; i < strings.size(); i++) {
                    ItemEntity itemEntity = new ItemEntity();
                    itemEntity.setValue(strings.get(i));
                    itemEntity.setY(itemHeight * i);
                    itemEntities.add(itemEntity);
                }
                isSetList = true;
                if (!TextUtils.isEmpty(defaultValue)) {
                    //必须放在isSetList=true;后面，不然就没意义了
                    setPitchOn(defaultValue);
                }
                invalidate();
            }
        });
    }

    /**
     * 设置是否循环，如果小于9条记录，直接默认不给循环，必须在setList()之后调用，因为setList会有一个自动设置是否循环的动作
     *
     * @param bln
     */
    private void setIsLoop(boolean bln) {
        isLoop = bln;
        if (itemEntities.size() < 9) {
            isLoop = false;
        }
    }

    /**
     * 设置文字颜色
     *
     * @param normal 未选中颜色
     * @param press  选中颜色
     */
    public void setFontColor(int normal, int press) {
        normalFontColor = normal;
        pressFontColor = press;
    }


    /**
     * 根据高度设置最大最小字体的大小，范围是20-80
     *
     * @param minPercent
     * @param maxPercent
     */
    public void setFontSize(int minPercent, int maxPercent) {
        if (minPercent < 20) {
            minPercent = 20;
        }
        if (maxPercent < 20) {
            maxPercent = 20;
        }
        if (minPercent > 80) {
            minPercent = 80;
        }
        if (maxPercent > 80) {
            maxPercent = 80;
        }
        minFontSizePercent = minPercent;
        maxFontSizePercent = maxPercent;
        invalidate();
    }

    /**
     * 设置改变监听
     *
     * @param onStringListener
     */
    public void setOnPitchOnChangeListener(OnStringListener onStringListener) {
        this.onPitchOnChangeListener = onStringListener;
    }

    /**
     * 返回选中的
     *
     * @return
     */
    public String getPitchOn() {
        if (itemEntities != null) {
            for (int i = 0; i < itemEntities.size(); i++) {
                if (itemEntities.get(i).getY() > 1.8 * itemHeight && itemEntities.get(i).getY() < 2.8 * itemHeight) {
                    return itemEntities.get(i).getValue();
                }
            }
        }
        return "";
    }

    /**
     * 返回当前的列表
     *
     * @return
     */
    public List<String> getList() {
        List<String> strings = new ArrayList<>();
        if (itemEntities == null) {
            return strings;
        }
        for (int i = 0; i < itemEntities.size(); i++) {
            strings.add(itemEntities.get(i).getValue());
        }
        return strings;
    }

    /**
     * 设置当前选中的内容
     *
     * @param str
     */
    public void setPitchOn(String str) {
        if (isSetList) {
            //列表已经初始化了
            if (itemEntities == null) {
                return;
            }
            for (int i = 0; i < itemEntities.size(); i++) {
                if (itemEntities.get(i).getValue().equals(str)) {
                    setPitchOn(i);
                    return;
                }
            }
        } else {
            //列表还没初始化
            defaultValue = str;
        }
    }

    /**
     * 设置当前选中第几项
     *
     * @param position
     */
    public void setPitchOn(int position) {
        if (itemEntities == null || itemHeight == 0) {
            return;
        }
        itemEntities.get(position).setY(itemHeight * 2);
        for (int i = position - 1; i >= 0; i--) {
            itemEntities.get(i).setY(itemEntities.get(i + 1).getY() - itemHeight);
        }
        for (int i = position + 1; i < itemEntities.size(); i++) {
            itemEntities.get(i).setY(itemEntities.get(i - 1).getY() + itemHeight);
        }
        deviationY(0);//防止可循环情况下出现空缺，执行一下偏移操作进行调整
        invalidate();
    }

    class ItemEntity {
        private String value;//需要显示的文本
        private float y;//当前的y坐标

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }
    }

    public int getNormalFontColor() {
        return normalFontColor;
    }

    public int getPressFontColor() {
        return pressFontColor;
    }

    public float getMinFontSize() {
        return minFontSize;
    }

    public float getMaxFontSize() {
        return maxFontSize;
    }

    public interface OnStringListener {
        void onClick(String str);
    }

}
