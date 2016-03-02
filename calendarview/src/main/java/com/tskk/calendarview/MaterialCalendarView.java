package com.tskk.calendarview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by stadiko on 2/29/16.
 */
public class MaterialCalendarView extends FrameLayout {
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("MMMM yyyy", Locale.US);

    private Context mContext;

    public MaterialCalendarView(Context context) {
        this(context, null);
    }

    public MaterialCalendarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;

        init(attrs, defStyleAttr);

        initView();
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.MaterialCalendarView, defStyleAttr, 0);

        /*if (a != null) {
            mPrefixText = a.getString(R.styleable.CountDownTimerView_prefixText);
            mSuffixText = a.getString(R.styleable.CountDownTimerView_suffixText);

            String milliStr = a.getString(R.styleable.CountDownTimerView_timeMilliSeconds);
            if (!TextUtils.isEmpty(milliStr) && TextUtils.isDigitsOnly(milliStr)) {
                mMilliSeconds = Long.parseLong(a.getString(R.styleable.CountDownTimerView_timeMilliSeconds));
                setTime(mMilliSeconds);
                startCountDown();
            }

            a.recycle();
        }*/
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.material_calendar_layout, this, true);
    }
}
