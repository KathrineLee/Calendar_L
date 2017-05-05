package com.example.calendarview;

/**
 * Created by Kathryn.L on 2017/5/4.
 */
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.*;

@SuppressWarnings("unused")
public class CalendarView extends FrameLayout{
    private ViewPager mViewPager;
    private List<Calendar>mSchemeDate;//日历表格布局
    private OnDateChangeListener mListener;
}
