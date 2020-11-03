package com.haocang.base.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//@SuppressLint("WrongConstant")
@SuppressLint("SimpleDateFormat")
public class TimeUtil {
    private static long day = 86400;
    private static long hour = 3600;
    private static long minute = 60;
    private static long mouth = 2592000;
    private static long second = 1;

    // 把字符串转为日期
    public static Date converDate(String strDate) {

        try {
            DateFormat df = new SimpleDateFormat("MM-dd HH:mm:ss");
            return df.parse(strDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new Date();
    }

    public static String converStrMMDD(final Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd");
        return sdf.format(date);
    }

    public static String converMMDD(final Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
        return sdf.format(date);
    }

    public static String converStr_MMDD(final Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        return sdf.format(date);
    }

    public static String converSr(final Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static Date getTranData(String time) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getNewTranData(String time) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getDateStrHHmm(final Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }

    public static String getDateStryyyyMMdd(final Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String getDateStryyyyMM(final Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
        return sdf.format(date);
    }

    public static String getDateStryyMM(final Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(date);
    }

    public static String getDateStrMMddHHmm(final Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        return sdf.format(date);
    }

    public static String getDateStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
        return sdf.format(date);
    }

    public static String getDateStr4(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(date);
    }

    public static String getDayStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String getDateTimeStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String getDateTimeStrTemp(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd+HH:mm:ss");
        return sdf.format(date);
    }

    public static String getTime() {
        return TimeUtil.getDateTimeStr(TimeUtil.getMonthLastTime(new Date()));
    }

    public static String getDateTimeStr(String oldStr) {
        String newStr = "";
        if (!StringUtils.isEmpty(oldStr)) {
            SimpleDateFormat oldSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date = oldSdf.parse(oldStr);
                SimpleDateFormat newSdf = new SimpleDateFormat("MM-dd HH:mm");
                newStr = newSdf.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return newStr;
    }

    public static String getCurdate() {
        SimpleDateFormat formatter3 = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
        Date curDate3 = new Date(System.currentTimeMillis());// 获取当前时间
        return formatter3.format(curDate3);
    }

    public static String getCurdate2() {
        SimpleDateFormat formatter3 = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
        Date curDate3 = new Date(System.currentTimeMillis());// 获取当前时间
        return formatter3.format(curDate3);
    }

    @SuppressWarnings("deprecation")
    public static String getDay(String timeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = sdf.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return d == null ? "" : d.getDate() + "";
    }

    public static String getDateSTimetr(final Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String getDateTimeStrWithoutSpace(final Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        return sdf.format(date);
    }

    public static Date getDateTimeWithoutSpace(final String dateTimeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        Date d = null;
        try {
            d = sdf.parse(dateTimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    public static String getMonth(String timeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = sdf.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return d == null ? "" : d.getMonth() + 1 + ""; // 月份+1
    }

    public static String getYear(String timeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = sdf.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return d == null ? "" : d.getYear() + "";
    }

    public static String getDuring(String startTime, String endTime) {
        String duration = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startT = sdf.parse(startTime);
            Date endT = sdf.parse(endTime);
            long intervalSecond = (endT.getTime() - startT.getTime()) / 1000;
            duration = getDuringByInterval(intervalSecond);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return duration;
    }

    public static String getDuring(final Date startT, final Date endT) {
        String duration = "";
        try {
//            SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
//            Date startT = sdf.parse(startTime);
//            Date endT = sdf.parse(endTime);
            long intervalSecond = (endT.getTime() - startT.getTime()) / 1000;
            duration = getDuringByInterval(intervalSecond);
//            duration += intervalSecond + "秒";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return duration;
    }

    public static String getDuringTwo(final Date startT, final Date endT) {
        String duration = "";
        try {
//            SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
//            Date startT = sdf.parse(startTime);
//            Date endT = sdf.parse(endTime);
            long intervalSecond = (endT.getTime() - startT.getTime()) / 1000;
            duration = getDuringByIntervalTwo(intervalSecond);
//            duration += intervalSecond + "秒";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return duration;
    }

    private static String getDuringByInterval(long intervalSecond) {
        String duration = "";
        if (intervalSecond > mouth) {
            duration += intervalSecond / mouth + "月";
            intervalSecond = intervalSecond % mouth;
        }
        if (intervalSecond > day) {
            duration += intervalSecond / day + "天";
            intervalSecond = intervalSecond % day;
        }
        if (intervalSecond > hour) {
            duration = +intervalSecond / hour + "小时";
            intervalSecond = intervalSecond % hour;
        }
        if (intervalSecond > minute) {
            duration += intervalSecond / minute + "分钟";
            intervalSecond = intervalSecond % minute;
        }
        if (intervalSecond > second) {
            duration += intervalSecond + "秒";
        }
        return duration;
    }

    public static String getDuringByIntervalTwo(long intervalSecond) {
        String duration = "";
        if (intervalSecond > mouth) {
            duration += intervalSecond / mouth + "月";
            intervalSecond = intervalSecond % mouth;
        }
        if (intervalSecond > day) {
            duration += intervalSecond / day + "天";
            intervalSecond = intervalSecond % day;
        }
        if (intervalSecond > hour) {
            duration +=intervalSecond / hour + "时";
            intervalSecond = intervalSecond % hour;
        }
        if (intervalSecond > minute) {
            duration += intervalSecond / minute + "分";
        }
        return duration;
    }

    public static Date getDayStart(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        setStartTime(c);
        return c.getTime();
    }

    public static Date getDayEnd(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        setEndTime(c);
        return c.getTime();
    }

    //    @SuppressLint("WrongConstant")
    public static Date getWeekFirstDay(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        if (isWeekLastDay(d)) {
            // d = getWeekLastDay(getLastWeek(d));
            c.add(Calendar.DAY_OF_YEAR, -6);
        } else {
            c.set(Calendar.DAY_OF_WEEK, 1);
            c.add(Calendar.DAY_OF_YEAR, 1);
        }

        setStartTime(c);
        return c.getTime();
    }

    private static boolean isWeekLastDay(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return Calendar.SUNDAY == c.get(Calendar.DAY_OF_WEEK);
    }

    public static Date getWeekLastDay(final Date d) {
        Date time = null;
        // if (isNowDay(d))
        // {/* 如果是今天，本月最后一天就截止到今天 */
        // time = new Date();
        // }
        // else
        // {/* 如果不是今天,就取一个星期的最后一天 */
        time = getWeekFirstDay(getNextWeek(d));
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        c.add(Calendar.DAY_OF_YEAR, -1);
        setEndTime(c);
        time = c.getTime();
        // }
        return time;
    }

    /**
     * 获取一个月的第一天
     *
     * @return
     */
    public static Date getMonthFirstDay(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.DAY_OF_MONTH, 1);
        setStartTime(c);
        return c.getTime();
    }

    /**
     * 描述：获取本年的第一天
     *
     * @param d
     * @return
     */
    public static Date getYearStart(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.DAY_OF_YEAR, 1);
        setStartTime(c);
        return c.getTime();
    }

    public static Date getYearLastDay(Date d) {
        Date time = null;
        if (isNowDay(d)) {/* 如果是本年， */
            time = new Date();
        } else {/* 去下一年第一天的前一天就是这一年的最后一天 */
            time = getYearStart(getNextYear(d));
            Calendar c = Calendar.getInstance();
            c.setTime(time);
            c.add(Calendar.DAY_OF_YEAR, -1);
            setEndTime(c);
            time = c.getTime();
        }
        return time;
    }

    public static Date getNextYear(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.YEAR, 1);
        setStartTime(c);
        return c.getTime();
    }

    /**
     * 获取本月的第一天
     *
     * @param d
     * @return
     */
    public static Date getMonthLastTime(Date d) {
        Date time = null;
        // if (isNowDay(d))
        // {/* 如果是本月，本月最后一天就截止到今天 */
        // time = new Date();
        // }
        // else
        {/* 如果不是本月,就取一个月的最后一天 */
            time = getMonthFirstDay(getNextMonth(d));
            Calendar c = Calendar.getInstance();
            c.setTime(time);
            c.add(Calendar.DAY_OF_YEAR, -1);
            setEndTime(c);
            time = c.getTime();
        }
        return time;
    }

    /**
     * 描述：是否是今天
     *
     * @param d
     * @return
     */
    public static boolean isNowDay(Date d) {
        Calendar c = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        return c.get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR)
                && c.get(Calendar.YEAR) == cal.get(Calendar.YEAR);
    }

    public static Date getNextMonth(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.MONTH, 1);
        return c.getTime();
    }

    public static Date getLastMonth(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.MONTH, -1);
        return c.getTime();
    }

    public static Date getNextWeek(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.WEEK_OF_YEAR, 1);
        return c.getTime();
    }

    public static Date getLastWeek(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.WEEK_OF_YEAR, -1);
        return c.getTime();
    }

    public static Date getLastYear(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.YEAR, -1);
        return c.getTime();
    }

    public static Date getNextDay(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DAY_OF_YEAR, 1);
        return c.getTime();
    }

    /**
     * 获取一小时后
     *
     * @param d
     * @return
     */
    public static Date geNextHour(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.HOUR, +1);
        return c.getTime();
    }

    /**
     * 一天后
     *
     * @param d
     * @return
     */
    public static Date geNextDay(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE, +1);
        return c.getTime();
    }

    /**
     * 七天后
     *
     * @param d
     * @return
     */
    public static Date geNextDay2(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE, +7);
        return c.getTime();
    }

    public static Date getLastDay(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DAY_OF_YEAR, -1);
        return c.getTime();
    }

    public static String getStrByTime(Date time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        return df.format(cal.getTime());
    }

    public static void setStartTime(final Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
    }

    public static void setEndTime(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
    }

    public static String getDuration(long intervalSecond) {
        String duration = "";
        if (intervalSecond > mouth) {
            duration = intervalSecond / mouth + "月";
        }
        if (intervalSecond > day) {
            duration = intervalSecond / day + "天";
        } else if (intervalSecond > hour) {
            duration = intervalSecond / hour + "小时";
        } else if (intervalSecond > minute) {
            duration = intervalSecond / minute + "分钟";
        } else {
            duration = intervalSecond + "秒";
        }

        return duration;
    }

    public static CharSequence toDateMinite(String timeStr) {
        SimpleDateFormat olddf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat newDf = new SimpleDateFormat("MM-dd HH:mm:ss");
        Date time = null;
        String result = null;
        try {
            time = olddf.parse(timeStr);
            result = newDf.format(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static CharSequence getMonthYearStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        return format.format(date);
    }

    public static Date getDateByStr(String str) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = sdf.parse(str);
        } catch (Exception e) {
            date = new Date();
        }
        return date;
    }

    @SuppressWarnings("deprecation")
    public static String getHourMinute(Date date) {
        if (date == null) {
            return "";
        }
        int minute = date.getMinutes();
        date.setMinutes(minute / 10 * 10);
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        return sdf.format(date);
    }

    public static String getMonthDay(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        return sdf.format(date);
    }

    public static String getYeart(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        return sdf.format(date);
    }


    private static String format(int i) {
        if (i < 10) {
            return "0" + i;
        }
        return "" + i;
    }

    private static List<String> getDayLabel() {
        List<String> labelList = new ArrayList<String>();
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                labelList.add("0" + i + "时");
            } else {
                labelList.add(i + "时");
            }
        }
        return labelList;
    }

    private static List<String> getYeayLabel() {
        List<String> labelList = new ArrayList<String>();
        for (int i = 1; i <= 12; i++) {
            if (i < 10) {
                labelList.add("0" + i + "月");
            } else {
                labelList.add(i + "月");
            }
        }
        return labelList;
    }

    /**
     * 本年最后一天
     *
     * @return
     */
    public static Date getCurrYearLast() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearLast(currentYear);
    }

    public static Date getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();

        return currYearLast;
    }

    /***
     * 当选择周的特殊需要
     *
     * @return
     */
    public static String getDateStr2(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd 00:00:00 ");
        return sdf.format(date);
    }

    /***
     *
     * @param date
     * @return 当选择周的特殊需要
     */
    public static String getDateStr3(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd 23:59:59 ");
        return sdf.format(date);
    }

    /***
     * 返回指定天的 周一的时间 描述：TODO
     *
     * @param time
     * @return
     */
    public static Date convertWeekByDate(Date time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        return cal.getTime();// 返回周一的时间
    }

    /***
     * 返回指定日 周末的时间 描述：TODO
     *
     * @param time
     * @return
     */
    public static Date convertWeekByDate2(Date time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        // String imptimeBegin = sdf.format(cal.getTime());// 所在周星期一的日期
        cal.add(Calendar.DATE, 6);
        // String imptimeEnd = sdf.format(cal.getTime());// 所在 周 星期天的日期
        return cal.getTime();
    }

    public static String getMonthStart(int i) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, i);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return format.format(calendar.getTime()) + " 00:00";
    }

    public static String getMonthEnd(int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, i);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(calendar.getTime()) + " 23:59";
    }


    // 把字符串转为日期
    public static Date ConverToDate(String strDate) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.parse(strDate);
    }


    public static String localDateTime(String time) {
        if (!TextUtils.isEmpty(time)) {
            time = time.replaceAll("T", " ").replaceAll("Z", " ");
        }
        return time;
    }

    public static String minusDateTime(String time) {
        if (!TextUtils.isEmpty(time)) {
            time = time.replaceAll("T", "").replaceAll("Z", "");
        }
        return time;
    }

    public static String addLocalTime(String time) {

        if (!TextUtils.isEmpty(time)) {
            StringBuilder builder = new StringBuilder(time);
            builder.insert(10, "T");
            builder.insert(time.length() + 1, "Z");
            return builder.toString().trim();
        }
        return "";
    }

//    public static String addLocalTime2(String time) {
//        time = time.replace(" ", "T");
//        if (!TextUtils.isEmpty(time)) {
//            StringBuilder builder = new StringBuilder(time);
////            builder.insert(10, "T");
//            builder.insert(time.length() + 1, "Z");
//            return builder.toString().trim();
//        }
//        return "";
//    }

    public static String getShowTime_YYYYMMDD(final String finishDate) {
        if (finishDate == null) {
            return "";
        }
        String time = minusDateTime(finishDate);
        Date date = getDateTimeWithoutSpace(time);
        return getDayStr(date);
    }

    public static String getLocalTimeStr(final Date startTime) {
        String time = TimeUtil.getDateTimeStrWithoutSpace(startTime);
        time = addLocalTime(time);
        return time;
    }

//    public static String getShowLocalTime(String time) {
//        if (!TextUtils.isEmpty(time)) {
//            time = time.replaceAll("T", " ").replaceAll("Z", "");
//        }
//        return time;
//    }
}
