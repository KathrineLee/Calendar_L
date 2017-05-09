package com.example.kathrynl.calendar_k;

/**
 * Created by Kathryn.L on 2017/5/8.
 */
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.kathrynl.calendar_k.R;
import com.example.kathrynl.calendar_k.CalendarGridViewAdapter.OnDaySelectListener;
import com.example.kathrynl.calendar_k.TimeUtil;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * 单个月的日历视图 <功能简述> <Br>
 * 可以定义样式<功能详细描述> <Br>
 *
 * @author Kyson http://www.hikyson.cn/
 */
@SuppressLint("ClickableViewAccessibility")
public class CalendarCard extends LinearLayout {

    /**
     * 控件的日期改变 <功能简述> <Br>
     * <功能详细描述> <Br>
     *
     * @author Kyson
     */
    public interface OnCalendarChangeListener {
        void onCalendarChange(Calendar cal);
    }

    private Context mContext;

    private GridWithoutScrollView mGv;

    private CalendarGridViewAdapter mGridViewAdapter;

    private Calendar mCurrentCal = Calendar.getInstance();

    private OnCalendarChangeListener mOnCalendarChangeListener;

    private OnDaySelectListener mOnDaySelectListener;

    public CalendarCard(Context context) {
        this(context, null);
    }

    public CalendarCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(attrs);
    }

    // 可以定义的属性

    // 星期文字样式
    private int mWeekTextStyle;
    // 今日文字样式
    private int mTodayTextStyle;

    private int mNotCurrentTextStyle;
    // 普通日期文字样式
    private int mDayTextStyle;
    // 选中文字背景
    private int mDaySelector;

    /**
     * 初始化日期以及view等控件
     */
    private void init(AttributeSet attrs) {
        // 获取所有的资源文件
        if (attrs != null) {

            TypedArray a = mContext.obtainStyledAttributes(attrs,
                    R.styleable.TlCalendar);
            mWeekTextStyle = a.getResourceId(
                    R.styleable.TlCalendar_weekTextStyle,
                    R.style.textView_sp13_grey_bg_bold);
            mTodayTextStyle = a.getResourceId(
                    R.styleable.TlCalendar_todayTextStyle,
                    R.style.textView_sp12_green);
            mNotCurrentTextStyle = a.getResourceId(
                    R.styleable.TlCalendar_notCurrentTextStyle,
                    R.style.textView_12_grey_light);
            mDayTextStyle = a.getResourceId(
                    R.styleable.TlCalendar_dayTextStyle,
                    R.style.textView_sp12_white);
            mDaySelector = a.getResourceId(R.styleable.TlCalendar_daySelector,
                    R.drawable.tl_widget_calendar_item_common_selector);

            a.recycle();
        } else {
            mWeekTextStyle = R.style.textView_sp13_grey_bg_bold;
            mTodayTextStyle = R.style.textView_sp12_green;
            mNotCurrentTextStyle = R.style.textView_12_grey_light;
            mDayTextStyle = R.style.textView_sp12_white;
            mDaySelector = R.drawable.tl_widget_calendar_item_common_selector;
        }

        View view = LayoutInflater.from(mContext).inflate(
                R.layout.widget_calendar_card, this, true);

        LinearLayout weekdaysLl = (LinearLayout) view
                .findViewById(R.id.widget_calendar_card_weekdays);

        // 设置星期文字样式
        for (int i = 0; i < weekdaysLl.getChildCount(); i++) {
            View child = weekdaysLl.getChildAt(i);
            if (child instanceof TextView) {
                ((TextView) child).setTextAppearance(mContext, mWeekTextStyle);
            }
        }

        mGv = (GridWithoutScrollView) view
                .findViewById(R.id.widget_calendar_card_gv);
        mGridViewAdapter = new CalendarGridViewAdapter(mContext);
        mGv.setAdapter(mGridViewAdapter);

        mGridViewAdapter.initStyle(mTodayTextStyle, mNotCurrentTextStyle,
                mDayTextStyle, mDaySelector);

        mGridViewAdapter.setOnDaySelectListener(new OnDaySelectListener() {

            @Override
            public void onDaySelectListener(Calendar date) {
                mCurrentCal = (Calendar) date.clone();
                if (mOnDaySelectListener != null) {
                    mOnDaySelectListener.onDaySelectListener(date);
                }
            }
        });
    }

    /**
     * 设置控件的选中日期 <功能简述>
     *
     * @param calendar
     */
    public void selectCurrentCalendar(Calendar calendar) {
        mCurrentCal = (Calendar) calendar.clone();
        // printCal();
        mGridViewAdapter.setSelectedDate(calendar);
        notifyDataChanged();
    }

    /**
     * jump to pre month <功能简述>
     */
    public void toBeforeMonth() {
        mCurrentCal.add(Calendar.MONTH, -1);
        notifyDataChanged();
    }

    /**
     * jump to next month <功能简述>
     */
    public void toAfterMonth() {
        mCurrentCal.add(Calendar.MONTH, 1);
        notifyDataChanged();
    }

    public void selectBeforeDay() {
        mCurrentCal.add(Calendar.DAY_OF_MONTH, -1);
        mGridViewAdapter.setSelectedDate(mCurrentCal);
        notifyDataChanged();
    }

    public void selectAfterDay() {
        mCurrentCal.add(Calendar.DAY_OF_MONTH, 1);
        mGridViewAdapter.setSelectedDate(mCurrentCal);
        notifyDataChanged();
    }

    private GestureDetector mDetector = new GestureDetector(mContext,
            new SimpleOnGestureListener() {

                @Override
                public boolean onDown(MotionEvent e) {
                    return true;
                }

                public boolean onFling(MotionEvent e1, MotionEvent e2,
                                       float velocityX, float velocityY) {
                    // L.i("onFling");
                    if (Math.abs(velocityX) > Math.abs(velocityY) * 0.8) { // 降噪处理，Y方向的动作要更大一点才会认为是垂直滚动
                        if (velocityX > 0) {
                            // 向右边
                            toBeforeMonth();
                        } else {
                            // 向左边
                            toAfterMonth();
                        }
                        return true;
                    } else {
                        return false;
                    }
                };
            });

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDetector.onTouchEvent(event);
    }

    // 初始点击点
    private float mDx;
    // 用于判断是移动还是点击
    private static final int MIN_MOVE_X = 20;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDx = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float my = ev.getX();
                if (Math.abs(my - mDx) > MIN_MOVE_X) {// 如果X方向滑动的距离大于指定距离则拦截手势动作，执行本View的OnTouch事件
                    return true;
                }
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 给gridview设置,设置数据 <功能简述>
     *
     * @param cal
     */
    private List<CalendarItem> getGvDataByYearAndMonth() {
        // 前面的空格数
        int firstDaySpaceCount = getFirstDayOfSpaceCount(mCurrentCal);
        // 后面的空格数
        int lastDaySpaceCount = getLastDayOfSpaceCount(mCurrentCal);
        // 获取当前月有多少天
        int dayCount = getDayNumInMonth(mCurrentCal);

        return getGvListData(firstDaySpaceCount, lastDaySpaceCount, dayCount);
    }

    /**
     * 控件数据变化，通知改变样式 <功能简述>
     */
    private void notifyDataChanged() {
        performAnim();
        mGridViewAdapter.setDatas(getGvDataByYearAndMonth());
        mGridViewAdapter.notifyDataSetChanged();
        if (mOnCalendarChangeListener != null) {
            mOnCalendarChangeListener.onCalendarChange(mCurrentCal);
        }
    }

    /**
     * perform this view's anim <功能简述>
     */
    private void performAnim() {
        ObjectAnimator.ofFloat(mGv, "alpha", 0f, 1f).setDuration(150).start();
    }

    /**
     * 为gridview中添加需要展示的数据
     *
     * @param tempSum
     * @param dayNumInMonth
     */
    private List<CalendarItem> getGvListData(int first, int last, int dayCount) {
        List<CalendarItem> list = new ArrayList<CalendarItem>();
        // 当前选中的月份对应的calendar
        Calendar currentCalendar = (Calendar) mCurrentCal.clone();
        currentCalendar.set(Calendar.DAY_OF_MONTH, 1);

        // 前面的空格，填充
        for (int i = 0; i < first; i++) {
            CalendarItem calendarItem = new CalendarItem();
            Calendar calendar = (Calendar) currentCalendar.clone();
            calendar.add(Calendar.MONTH, -1);
            calendar.set(Calendar.DAY_OF_MONTH, getDayNumInMonth(calendar)
                    - first + i + 1);
            calendarItem.calendar = calendar;
            calendarItem.isToday = isToday(calendar);
            calendarItem.monthPos = CalendarItem.MONTH_PRE;
            list.add(calendarItem);
        }
        // 当前月的日期
        for (int j = 0; j < dayCount; j++) {
            CalendarItem calendarItem = new CalendarItem();
            Calendar calendar = (Calendar) currentCalendar.clone();
            calendar.set(Calendar.DAY_OF_MONTH, j + 1);
            calendarItem.calendar = calendar;
            calendarItem.isToday = isToday(calendar);
            calendarItem.monthPos = CalendarItem.MONTH_CURRENT;
            list.add(calendarItem);
        }

        // 后面的空格填充
        for (int k = 0; k < last; k++) {
            CalendarItem calendarItem = new CalendarItem();
            Calendar calendar = (Calendar) currentCalendar.clone();
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, k + 1);
            calendarItem.calendar = calendar;
            calendarItem.isToday = isToday(calendar);
            calendarItem.monthPos = CalendarItem.MONTH_NEXT;
            list.add(calendarItem);
        }
        return list;
    }

    public void setOnCalendarChangeListener(
            OnCalendarChangeListener onCalendarChangeListener) {
        this.mOnCalendarChangeListener = onCalendarChangeListener;
    }

    public Calendar getSelectedDate() {
        return mGridViewAdapter.getSelecterDate();
    }

    /**
     * 获取月份第一天前面的空格 <功能简述>
     *
     * @param cal
     * @return
     */
    private static int getFirstDayOfSpaceCount(Calendar cal) {
        Calendar calTemp = (Calendar) cal.clone();
        calTemp.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayInWeek = calTemp.get(Calendar.DAY_OF_WEEK);
        // 换算为空格数
        return weekToSpaceCount(firstDayInWeek);
    }

    /**
     * 获取月份最后一天后面的空格数 <功能简述>
     *
     * @param cal
     * @return
     */
    private static int getLastDayOfSpaceCount(Calendar cal) {
        Calendar calTemp = (Calendar) cal.clone();
        calTemp.set(Calendar.DAY_OF_MONTH, getDayNumInMonth(cal));
        int lastDayInWeek = calTemp.get(Calendar.DAY_OF_WEEK);
        return 6 - weekToSpaceCount(lastDayInWeek);
    }

    /**
     * 将周几换算为空格数 <功能简述>
     *
     * @param week
     * @return
     */
    private static int weekToSpaceCount(int week) {
        int space = (7 + (week - 2)) % 7;
        return space;
    }

    /**
     * 获取当前月的总共天数
     *
     * @param cal
     * @return
     */
    private static int getDayNumInMonth(Calendar cal) {
        return cal.getActualMaximum(Calendar.DATE);
    }

    /**
     * 是否为今天 <功能简述>
     *
     * @return
     */
    private static boolean isToday(Calendar cal) {
        return TimeUtil.isToday(cal.getTimeInMillis());
    }

    public void setOnDaySelectListener(OnDaySelectListener onDaySelectListener) {
        this.mOnDaySelectListener = onDaySelectListener;
    }
}
