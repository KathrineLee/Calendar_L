package com.example.kathrynl.calendar_k;

/**
 * Created by Kathryn.L on 2017/5/8.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * gridview
 */
public class GridWithoutScrollView extends GridView {
    public GridWithoutScrollView(Context context) {
        super(context);
    }

    public GridWithoutScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);//MAX_VALUE右移2位后，即使不是最大整数了， view的高度也一般不可能超过它
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
