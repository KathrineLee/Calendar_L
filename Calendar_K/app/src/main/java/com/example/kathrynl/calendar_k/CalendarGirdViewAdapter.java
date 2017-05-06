package com.example.kathrynl.calendar_k;

/**
 * 日历网格视图适配器
 * Created by Kathryn.L on 2017/5/6.
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

public class CalendarGirdViewAdapter extends BaseAdapter{
    /**
     * 自定义监听接口
     */
    public interface OnDaySelectListener{
        void onDaySelectListener(Calendar date);
    }
    private OnDaySelectListener mOnDaySelectListener;
    //
    private List<CalendarItem> mDatas = new ArrayList<CalendarItem>();
}
