package com.org.sleepgod.utils;

import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by cool on 2017/1/9.
 */

public class DateUtil {
    /**
     * 判断选择的日期是否是本周
     * 周日到周六
     * @param dataString
     * @return
     */
    public static boolean isThisWeek2(String dataString) {

        if (TextUtils.isEmpty(dataString)) {
            return false;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(dataString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        calendar.setTime(date);
        int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        if (paramWeek == currentWeek) {
            return true;
        }
        return false;
    }

    /**
     * 判断选择的日期是否是本周
     * 周一到周日
     * @param dataString
     * @return
     */
    public static boolean isThisWeek(String dataString) {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = cal.getTime();

        cal.add(Calendar.DATE, 6);
        Date endDate = cal.getTime();

        try {
            Date currentDate = simpleDateFormat.parse(dataString);
            String cDate = simpleDateFormat.format(currentDate);
            String bDate = simpleDateFormat.format(beginDate);
            String eDate = simpleDateFormat.format(endDate);
            //Log.e("399", "cDate:" + cDate + " bDate:" + bDate + " eDate:" + eDate);
            if (cDate.equals(bDate)) {
                return true;
            }
            if (currentDate.after(beginDate) && currentDate.before(endDate)) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断选择的日期是否是今天
     */
    public static boolean isToday(String dataString) {

        if (TextUtils.isEmpty(dataString)) {
            return false;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(dataString);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isThisTime(date, "yyyy-MM-dd");
    }

    /**
     * 判断选择一个日期期间是否包含今天
     */
    public static boolean isContainToday(String startTime, String endTime) {

        if (TextUtils.isEmpty(startTime) || TextUtils.isEmpty(endTime)) {
            return false;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = simpleDateFormat.parse(startTime);
            endDate = simpleDateFormat.parse(endTime);
            Date today = new Date();
            if (today.after(startDate) && today.before(endDate)) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断选择的日期是否是本月
     * @param dataString
     * @return
     */
    public static boolean isThisMonth(String dataString) {

        if (TextUtils.isEmpty(dataString)) {
            return false;
        }


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(dataString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isThisTime(date, "yyyy-MM");
    }

    /**
     * 判断日期是否本季度
     * @param dataString
     * @return
     */
    public static boolean isThisSeason(String dataString) {

        if (TextUtils.isEmpty(dataString)) {
            return false;
        }


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(dataString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date currentQuarterStartTime = getCurrentQuarterStartTime();
        Date currentQuarterEndTime = getCurrentQuarterEndTime();
//        if (date.after(currentQuarterStartTime) && date.before(currentQuarterEndTime)) {
//            return true;
//        }
        if (date.getTime() >= currentQuarterStartTime.getTime() && date.getTime() <= currentQuarterEndTime.getTime()) {
            return true;
        }
        return false;
    }

    /**
     * 是否是本年
     * @param dataString
     * @return
     */
    public static boolean isThisYear(String dataString) {

        if (TextUtils.isEmpty(dataString)) {
            return false;
        }


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(dataString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isThisTime(date, "yyyy");
    }

    private static boolean isThisTime(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String param = sdf.format(date);//参数时间
        String now = sdf.format(new Date());//当前时间
        if (param.equals(now)) {
            return true;
        }
        return false;
    }

    /**
     * 获取当前季度的开始时间
     * @return
     */
    public static Date getCurrentQuarterStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 4);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前季度的结束时间
     *
     * @return
     */
    public static Date getCurrentQuarterEndTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getCurrentQuarterStartTime());
        cal.add(Calendar.MONTH, 3);
        return cal.getTime();
    }

    /**
     * 获取今天
     *
     * @return
     */
    public static String getToday() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today = simpleDateFormat.format(date);
        return today;
    }

    /**
     * 获取本周的第一天和最后一天
     *
     * @return
     */
    public static String[] getWeekFirstDayAndLastDay() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = cal.getTime();

        cal.add(Calendar.DATE, 6);
        Date endDate = cal.getTime();
        String beginDay = simpleDateFormat.format(beginDate);
        String endDay = simpleDateFormat.format(endDate);
        return new String[]{beginDay, endDay};
    }

    /**
     * 获取本月的第一天和最后一天
     * @return
     */
    public static String[] getMonthFirstDayAndLastDay() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前月第一天：
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        String first = simpleDateFormat.format(c.getTime());
        //获取当前月最后一天
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = simpleDateFormat.format(ca.getTime());
        Log.e("399","本月: " + "first: " + first + "last: " + last);
        return new String[]{first,last};
    }

    /**
     * 获取本季度的第一天和最后一天
     * @return
     */
    public static String[] getQuarterFirstDayAndLastDay(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date quarterStartTime = getCurrentQuarterStartTime();
        Date quarterEndTime = getCurrentQuarterEndTime();
        String startTime = simpleDateFormat.format(quarterStartTime);
        String endTime = simpleDateFormat.format(quarterEndTime);
        Log.e("399","本季度：" + "startTime:" + startTime + "endTime: "+ endTime);
        return new String[]{startTime,endTime};
    }
}