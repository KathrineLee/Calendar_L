package com.example.kathrynl.calendar_k;

/**
 * 日历网格视图适配器
 * Created by Kathryn.L on 2017/5/3.
 */
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kathrynl.calendar_k.R;
import com.example.kathrynl.calendar_k.TimeUtil;

public class CalendarGridViewAdapter extends BaseAdapter{

     //自定义监听接口
    public interface OnDaySelectListener{
        void onDaySelectListener(Calendar date);
    }
    private OnDaySelectListener mOnDaySelectListener;
    //创建LIST 用于显示在ListView的各个文件信息
    private List<CalendarItem> mDatas = new ArrayList<CalendarItem>();//数组队列（相当于动态数组）
    private Calendar mSelectedCal;//选择的日期

    private LayoutInflater mInflater;
    private Context mContext;//上下文，表示是哪个Active使用的
    /**
     *实例化LayoutInflater并调用context.getSystemService()
     *LAYOUT_INFLATER_SERVICE 取得xml里定义的view
     **/
    public  CalendarGridViewAdapter(Context context){//构造方法
        this.mContext=context;
        mInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * 设置显示style
     */
    private int mTodayTextStyle = R.style.textView_sp12_green;
    private int mNotCurrentTextStyle = R.style.textView_12_grey_light;
    private int mDayTextStyle = R.style.textView_sp12_white;
    private int mDaySelector = R.drawable.tl_widget_calendar_item_common_selector;

    public void initStyle(int todayTextStyle, int notCurrentTextStyle,
                          int dayTextStyle, int daySelector) {

        this.mTodayTextStyle = todayTextStyle;

        this.mNotCurrentTextStyle = notCurrentTextStyle;

        this.mDayTextStyle = dayTextStyle;

        this.mDaySelector = daySelector;
    }

    @Override
    public int getCount() {
        return mDatas.size();//返回calendaritem的数量
    }

    /**
     * 根据当前位置获取指定的文件信息
     * @param position
     * @return
     */
    @Override
    public CalendarItem getItem(int position) {
        return mDatas.get(position);
    }

    /**
     * 根据当前位置返回当前数据项在LIST中的行ID
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     *
     * @param position
     * @param convertView
     * @param parent 为使用当前适配器的适配器控件
     * @return
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridViewHolder holder;
        //如果convertView为空就创建一个View，否则使用getTag缓存的View
        if (convertView == null) {
            holder = new GridViewHolder();
            convertView = mInflater.inflate(R.layout.item_widget_common_calendar_gridview, parent, false);
            holder.tvDay = (TextView) convertView.findViewById(R.id.widget_common_calendar_gridview_item_date);
            convertView.setTag(holder);
        } else {
            holder = (GridViewHolder) convertView.getTag();
        }
        final CalendarItem calendarItem = getItem(position);//根据当前位置获取指定的文件信息

        TextView tvDay = holder.tvDay;

        tvDay.setText(String.valueOf(calendarItem.calendar.get(Calendar.DAY_OF_MONTH)));//文字为获取到的日期
        tvDay.setTextAppearance(mContext, getTextStyle(calendarItem));
        tvDay.setBackgroundResource(mDaySelector);
        tvDay.setSelected(TimeUtil.isSameDay(mSelectedCal,
                calendarItem.calendar));
        tvDay.setEnabled(calendarItem.monthPos == CalendarItem.MONTH_CURRENT);
        tvDay.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                checkItem(calendarItem.calendar);
            }
        });
        return convertView;//根据当前数据项的位置返回convertview
    }

    // check one item
    private void checkItem(Calendar cal) {
        // 点击的和当前选中的是同一天
        if (TimeUtil.isSameDay(cal, mSelectedCal)) {
            return;
        }
        mSelectedCal = (Calendar) cal.clone();//将点击的作为选中的
        notifyDataSetChanged();//如果适配器内容改变时需要用getView来刷新每个item的内容
        if (mOnDaySelectListener != null) {
            mOnDaySelectListener.onDaySelectListener(mSelectedCal);
        }
    }

    /**
     * get textview's color <功能简述>
     *
     * @param calendarItem
     * @return
*/
    private int getTextStyle(CalendarItem calendarItem) {
        int style;
        if (calendarItem.monthPos == CalendarItem.MONTH_CURRENT) {
            // current month
            if (calendarItem.isToday) {
                style = mTodayTextStyle;
            } else {
                style = mDayTextStyle;
            }
        } else {
            // 非本月
            style = mNotCurrentTextStyle;
        }
        return style;
    }

public static class GridViewHolder {
    public TextView tvDay;
}

    public void setOnDaySelectListener(OnDaySelectListener onDaySelectListener) {
        this.mOnDaySelectListener = onDaySelectListener;
    }

    public List<CalendarItem> getDatas() {
        return mDatas;
    }

    public void setDatas(List<CalendarItem> datas) {
        this.mDatas = datas;
    }

    public void setSelectedDate(Calendar cal) {
        checkItem(cal);
    }

    public Calendar getSelecterDate() {
        return mSelectedCal;
    }

}

