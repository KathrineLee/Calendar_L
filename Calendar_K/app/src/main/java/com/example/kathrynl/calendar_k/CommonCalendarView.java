package com.example.kathrynl.calendar_k;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kathrynl.calendar_k.R;
import com.example.kathrynl.calendar_k.CalendarCard;
import com.example.kathrynl.calendar_k.CalendarCard.OnCalendarChangeListener;
import com.example.kathrynl.calendar_k.CalendarGridViewAdapter.OnDaySelectListener;

import java.util.Calendar;


/**
 * Created by Kathryn.L on 2017/5/8.
 */

public class CommonCalendarView extends LinearLayout {
    public static final char DATE_SPLIT = '/';

    private Context mContext;
    private ImageView mLeftBtn;
    private ImageView mRightBtn;
    private TextView mBackBtn;
    private TextView mDateTextView;
    private CalendarCard mCalendarCard;

    public CommonCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public CommonCalendarView(Context context) {
        this(context, null);
    }

    private void init() {
        initView();
        initEvent();
    }

    private void initView() {
        setOrientation(LinearLayout.VERTICAL);
        inflate(mContext, R.layout.widget_calendar_common_view, this);
        mLeftBtn = (ImageView) this
                .findViewById(R.id.widget_calendar_common_view_left);
        mRightBtn = (ImageView) this
                .findViewById(R.id.widget_calendar_common_view_right);
        mBackBtn = (TextView) this
                .findViewById(R.id.widget_calendar_common_view_back);
        mDateTextView = (TextView) this
                .findViewById(R.id.widget_calendar_common_view_date);
        mCalendarCard = (CalendarCard) this
                .findViewById(R.id.widget_calendar_common_view_calendarcard);
    }

    private void initEvent() {
        mLeftBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mCalendarCard.toBeforeMonth();
            }
        });
        mRightBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mCalendarCard.toAfterMonth();
            }
        });

        mBackBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mCalendarCard.selectCurrentCalendar(Calendar.getInstance());
        }
        });
        mCalendarCard.setOnCalendarChangeListener(new OnCalendarChangeListener() {

                    @Override
                    public void onCalendarChange(Calendar cal) {
                        mDateTextView.setText(String.valueOf(cal.get(Calendar.YEAR)) + DATE_SPLIT + String.
                                valueOf(cal.get(Calendar.MONTH) + 1));
                        mCalendarCard.getLayoutParams().height = LayoutParams.MATCH_PARENT;
                    }
                });
        mCalendarCard.setOnDaySelectListener(new OnDaySelectListener() {

            @Override
            public void onDaySelectListener(Calendar date) {
                if (mOnDaySelectListener != null) {
                    mOnDaySelectListener.onDaySelectListener(date);
                }
            }
        });
    }

    private OnDaySelectListener mOnDaySelectListener;

    public Calendar getSelectedDate() {
        return mCalendarCard.getSelectedDate();
    }

    public void setCurrentDate(Calendar cal) {
        mCalendarCard.selectCurrentCalendar(cal);
    }

    /**
     * set listenr of selecting date
     */
    public OnDaySelectListener getOnDaySelectListener(){
        return mOnDaySelectListener;
    }

    public void setOnDaySelectListener(OnDaySelectListener mOnDaySelectListener) {
        this.mOnDaySelectListener = mOnDaySelectListener;
    }
}

