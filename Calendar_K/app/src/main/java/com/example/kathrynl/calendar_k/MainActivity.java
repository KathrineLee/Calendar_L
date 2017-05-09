package com.example.kathrynl.calendar_k;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.kathrynl.calendar_k.CalendarGridViewAdapter.OnDaySelectListener;
import com.example.kathrynl.calendar_k.CommonCalendarView;

/**
 *
 * 测试页面<功能简述> <Br>
 * <功能详细描述> <Br>
 *
 * @author Kyson http://www.hikyson.cn/
 */
public class MainActivity extends Activity {
    private CommonCalendarView mCalendarCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCalendarCardView = (CommonCalendarView) this.findViewById(R.id.calendar);
        mCalendarCardView.setCurrentDate(Calendar.getInstance());
        mCalendarCardView.setOnDaySelectListener(new OnDaySelectListener() {

            @Override
            public void onDaySelectListener(Calendar date) {

                String s = date.get(Calendar.YEAR) + "-"
                        + (date.get(Calendar.MONTH) + 1) + "-"
                        + date.get(Calendar.DAY_OF_MONTH);
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
