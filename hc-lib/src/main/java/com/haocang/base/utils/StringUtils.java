package com.haocang.base.utils;

import android.content.Context;
import android.text.TextUtils;

import com.haocang.base.R;
import com.haocang.base.config.AppApplication;

import org.feezu.liuli.timeselector.Utils.TextUtil;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
 * 创 建 者：he
 * 创建时间：2018/1/1713:09
 * 修 改 者：
 * 修改时间：
 */
public class StringUtils {

    private static final long LIMIT = 1000000;
    private static final int WAN = 10000;

    public static String formatNumber(String data) {
        String result = "";
        double value = 0;
        try {
            value = Double.parseDouble(data);
        } catch (Exception e) {

        }
        DecimalFormat df = new DecimalFormat("#,###");
        if (value > LIMIT) {
            value = value / WAN;
            result = df.format(value) + AppApplication.getContext().getString(R.string.wan);//"万";
        } else {
            result = df.format(value);
        }
        return result;
    }

    public static String format2Decimal(String strValue, String numTail) {
        if (strValue != null && strValue.startsWith(".")) {
            strValue = "0" + strValue;
        }
        int scale = 2;
        if (!isEmpty(numTail) && !"0".equals(numTail)) {
            scale = Integer.parseInt(numTail);
        }
        if (isEmpty(strValue) || !isDigit(strValue)) {
            return strValue;
        }
        double f = Double.parseDouble(strValue);
        String attach = "";
        if (f > 1000000) {
            // int index = 5;
            // double divide = 100000;
            // double value = f;
            // while (value >= 10)
            // {
            // divide *= 10;
            // index++;
            // value = f / divide;
            // }
            // f = value;
            // attach = "*10^" + index;
            f = f / 10000;
            attach = "万";
        } else if (f > 1000) {
            BigDecimal b = new BigDecimal(f);
            String result = b.setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
            return fmtMicrometer(result + "");
        }
        BigDecimal b = new BigDecimal(f);
        String result = b.setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
        return fmtMicrometer(result + "") + attach;
    }

    public static String fmtMicrometer(String text) {
        String tail = "";
        DecimalFormat df = new DecimalFormat("###,##0");
        String result = text;
        if (text.indexOf(".") > 0) {
            tail = text.substring(text.indexOf("."), text.length());
            result = result.substring(0, text.indexOf("."));
        }
        double number = Double.parseDouble(result);
        return df.format(number) + tail;
    }

    public static boolean isDigit(String oStr) {
        for (int i = 0; i < oStr.length(); i++) {
            if (!Character.isDigit(oStr.charAt(i)) && '.' != oStr.charAt(i) && '-' != oStr.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static int getIdByName(Context context, String className, String name) {
        String packageName = context.getPackageName();
        Class<?> r = null;
        int id = 0;
        try {
            r = Class.forName(packageName + ".R");
            Class[] classes = r.getClasses();
            Class desireClass = null;
            for (int i = 0; i < classes.length; ++i) {
                if (classes[i].getName().split("\\$")[1].equals(className)) {
                    desireClass = classes[i];
                    break;
                }

            }
            if (desireClass != null) {
                id = desireClass.getField(name).getInt(desireClass);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobile(String number) {
        String num = "[1][34578]\\d{9}";//"[1]"代表第1位为数字1，"[34578]"代表第二位可以为3、4、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }


    public static String utfCode(String value) {
        if (TextUtil.isEmpty(value)) {
            value="";
        }
        try {
            value = URLEncoder.encode(value, "utf-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return value;
    }


    /**
     * @param value
     * @return 解析url的中文
     */
    public static String urlDecoder(String value) {

        if (!TextUtils.isEmpty(value)) {
            return URLDecoder.decode(value);
        } else {
            return "";
        }
    }

    public static boolean isEmpty(final String oldStr) {
        if (oldStr == null || "".equals(oldStr)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否为int
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static boolean isPicture(final String path) {
        boolean isPicture = false;
        if (path != null) {
            if (path.endsWith(".png") || path.endsWith(".jpg") || path.endsWith(".jpeg")) {
                isPicture = true;
            }
        }
        return isPicture;
    }

    public static boolean isContainAll(String str) {
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLowerCase = false;//定义一个boolean值，用来表示是否包含字母
        boolean isUpperCase = false;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            } else if (Character.isLowerCase(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                isLowerCase = true;
            } else if (Character.isUpperCase(str.charAt(i))) {
                isUpperCase = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        boolean isRight = isDigit && isLowerCase && isUpperCase && str.matches(regex);
        return isRight;
    }

}
