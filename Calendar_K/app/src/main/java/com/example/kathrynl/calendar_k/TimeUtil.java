package com.example.kathrynl.calendar_k;
/**
 * 时间获取
 * Created by Kathryn.L on 2017/5/1.
 */
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {
    /**
     * 获取系统时间
     */
    public static long getSystemTime(){
        return System.currentTimeMillis();
    }
    /**
     *获取时间的每个域 ：年月日时分秒 （数组储存）
     * @param time
     * @return
     */
    public static int[] getTimeFields(long time){
        Calendar calendar =Calendar.getInstance();//获取当前系统时间
        calendar.setTimeInMillis(time);//根据毫秒数设置Calendar时间
        int[] timeFields = new int[6];//以数组方式储存
        timeFields[0]=calendar.get(Calendar.YEAR);
        timeFields[1]=calendar.get(Calendar.MONTH);
        timeFields[2] = calendar.get(Calendar.DAY_OF_MONTH);
        timeFields[3] = calendar.get(Calendar.HOUR_OF_DAY);
        timeFields[4] = calendar.get(Calendar.MINUTE);
        timeFields[5] = calendar.get(Calendar.SECOND);
        return timeFields;
    }
    /**
     * 获取格式化的时间（把date类型时间转换为指定格式的字符串）
     */
    public static String getFormatTime(String formatter ,long time){
        SimpleDateFormat format =new SimpleDateFormat(formatter,Locale.getDefault());
        //Locale.getDefault()获取当前的语言环境，把返回值放进SimpleDateFormat的构造里实现通用化
        return format.format(new Date(time));
        //返回本地规则格式化后的时间
    }

    /**
     * 判断时间是否为今天 <功能简述>
     *
     * @param time
     */
    public static boolean isToday(long time) {
        long now = getSystemTime();
        int[] nowFields = getTimeFields(now);
        int[] timeFields = getTimeFields(time);
        return nowFields[0] == timeFields[0] && nowFields[1] == timeFields[1]
                && nowFields[2] == timeFields[2];
    }

    /**
     * 比较两个日期是否为同一天 <功能简述>
     *
     * @param fromCalendar
     * @param toCalendar
     * @return
     */
    public static boolean isSameDay(Calendar fromCalendar, Calendar toCalendar) {
        if (fromCalendar == null || toCalendar == null) {
            return false;
        }
        // 年月日都一样，则为同一天
        return fromCalendar.get(Calendar.YEAR) == toCalendar.get(Calendar.YEAR)
                && fromCalendar.get(Calendar.MONTH) == toCalendar.get(Calendar.MONTH)
                && fromCalendar.get(Calendar.DAY_OF_MONTH) == toCalendar.get(Calendar.DAY_OF_MONTH);
    }

}
