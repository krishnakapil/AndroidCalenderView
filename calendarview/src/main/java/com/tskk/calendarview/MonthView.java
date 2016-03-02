package com.tskk.calendarview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.ArrayRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by stadiko on 2/29/16.
 */
public class MonthView extends LinearLayout implements View.OnClickListener {
    private static SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);

    public static final int THEME_LIGHT = 1;
    public static final int THEME_DARK = 2;

    private int mTheme = THEME_LIGHT;

    private LinearLayout mDayRow;
    private LinearLayout mDateFirstRow;
    private LinearLayout mDateSecondRow;
    private LinearLayout mDateThirdRow;
    private LinearLayout mDateFourthRow;
    private LinearLayout mDateFifthRow;

    private SparseArray<TextView> mDaysViewMap;
    private SparseArray<TextView> mDateViewMap;

    private Calendar mCurrentDate = Calendar.getInstance();

    private int mWeekdayDateTextAppearance;
    private int mWeekendDateTextAppearance;
    private int mWeekdayDateBackground;
    private int mWeekendDateBackground;

    private OnDateSelectedListener mOnDateSelectedListener;

    public interface OnDateSelectedListener {
        void onDateSelected(Calendar calendar);
    }

    public void setOnDateSelectedListener(OnDateSelectedListener listener) {
        mOnDateSelectedListener = listener;
    }

    public MonthView(Context context) {
        this(context, null);
    }

    public MonthView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MonthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mDaysViewMap = new SparseArray<>();
        mDateViewMap = new SparseArray<>();

        initView();

        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MonthView, defStyleAttr, 0);

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
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.month_calendar_layout, this, true);

        mDayRow = (LinearLayout) view.findViewById(R.id.day_layout);
        mDateFirstRow = (LinearLayout) view.findViewById(R.id.date_one_layout);
        mDateSecondRow = (LinearLayout) view.findViewById(R.id.date_two_layout);
        mDateThirdRow = (LinearLayout) view.findViewById(R.id.date_three_layout);
        mDateFourthRow = (LinearLayout) view.findViewById(R.id.date_four_layout);
        mDateFifthRow = (LinearLayout) view.findViewById(R.id.date_five_layout);

        initDayRowView();
        initDateMap(0, mDateFirstRow);
        initDateMap(1, mDateSecondRow);
        initDateMap(2, mDateThirdRow);
        initDateMap(3, mDateFourthRow);
        initDateMap(4, mDateFifthRow);

        //Styles
        setTheme(THEME_DARK);
        setBackgroundColor(ContextCompat.getColor(getContext(), mTheme == THEME_LIGHT ? R.color.lightBg : R.color.darkBg));
        setWeekdayTextAppearance(getContext(), mTheme == THEME_LIGHT ? R.style.WeekDayTextAppearance : R.style.WeekDayTextAppearanceDark);
        setWeekendTextAppearance(getContext(), mTheme == THEME_LIGHT ? R.style.WeekEndTextAppearance : R.style.WeekEndTextAppearanceDark);

        mWeekdayDateTextAppearance = mTheme == THEME_LIGHT ? R.style.WeekDayDateTextAppearance : R.style.WeekDayDateTextAppearanceDark;
        mWeekendDateTextAppearance = mTheme == THEME_LIGHT ? R.style.WeekEndDateTextAppearance : R.style.WeekEndDateTextAppearanceDark;

        mWeekdayDateBackground = mTheme == THEME_LIGHT ? R.drawable.round_shape_selector : R.drawable.round_shape_dark_selector;
        mWeekendDateBackground = mTheme == THEME_LIGHT ? R.drawable.round_shape_selector : R.drawable.round_shape_dark_selector;

        setDayLabels(R.array.days_one_letter);

        setCurrentDate(mCurrentDate);
    }

    private void initDayRowView() {
        mDaysViewMap.put(Calendar.SUNDAY, (TextView) mDayRow.findViewById(R.id.sun_txt));
        mDaysViewMap.put(Calendar.MONDAY, (TextView) mDayRow.findViewById(R.id.mon_txt));
        mDaysViewMap.put(Calendar.TUESDAY, (TextView) mDayRow.findViewById(R.id.tue_txt));
        mDaysViewMap.put(Calendar.WEDNESDAY, (TextView) mDayRow.findViewById(R.id.wed_txt));
        mDaysViewMap.put(Calendar.THURSDAY, (TextView) mDayRow.findViewById(R.id.thu_txt));
        mDaysViewMap.put(Calendar.FRIDAY, (TextView) mDayRow.findViewById(R.id.fri_txt));
        mDaysViewMap.put(Calendar.SATURDAY, (TextView) mDayRow.findViewById(R.id.sat_txt));
    }

    private void initDateMap(int row, View view) {
        int value = row * 7;
        mDateViewMap.put(Calendar.SUNDAY + value, (TextView) view.findViewById(R.id.sun_txt));
        mDateViewMap.put(Calendar.MONDAY + value, (TextView) view.findViewById(R.id.mon_txt));
        mDateViewMap.put(Calendar.TUESDAY + value, (TextView) view.findViewById(R.id.tue_txt));
        mDateViewMap.put(Calendar.WEDNESDAY + value, (TextView) view.findViewById(R.id.wed_txt));
        mDateViewMap.put(Calendar.THURSDAY + value, (TextView) view.findViewById(R.id.thu_txt));
        mDateViewMap.put(Calendar.FRIDAY + value, (TextView) view.findViewById(R.id.fri_txt));
        mDateViewMap.put(Calendar.SATURDAY + value, (TextView) view.findViewById(R.id.sat_txt));
    }

    public void setTheme(@IntRange(from = 1, to = 2) int theme) {
        mTheme = theme;
    }

    public Calendar getCurrentDate() {
        return mCurrentDate;
    }

    /**
     * Set Selected Date in Calendar
     *
     * @param calendar
     */
    public void setCurrentDate(Calendar calendar) {
        if (calendar == null) {
            return;
        }
        mCurrentDate = calendar;
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int dayOfWeek = getFirstDayOfWeekOfMonth(calendar.getTimeInMillis());

        int i = 1;
        int date = 1;

        for (i = 1; i < dayOfWeek; i++) {
            TextView textView = mDateViewMap.get(i);
            if (textView != null) {
                textView.setVisibility(INVISIBLE);
                textView.setOnClickListener(null);
                textView.setTag(null);
            }
        }

        for (i = dayOfWeek, date = 1; i < dayOfWeek + daysInMonth; i++, date++) {
            TextView textView = mDateViewMap.get(i);
            if (textView != null) {
                textView.setVisibility(VISIBLE);
                textView.setText(String.valueOf(date));
                textView.setOnClickListener(this);

                Calendar selectedDate = Calendar.getInstance();
                selectedDate.setTimeInMillis(calendar.getTimeInMillis());
                selectedDate.set(Calendar.DAY_OF_MONTH, date);

                textView.setTag(selectedDate);
            }
        }

        for (i = dayOfWeek + daysInMonth; i <= 35; i++) {
            TextView textView = mDateViewMap.get(i);
            if (textView != null) {
                textView.setVisibility(INVISIBLE);
                textView.setOnClickListener(null);
                textView.setTag(null);
            }
        }

        setWeekdayDateTextAppearance(getContext(), mWeekdayDateTextAppearance);
        setWeekendDateTextAppearance(getContext(), mWeekendDateTextAppearance);

        setWeekdayDateBackground(mWeekdayDateBackground);
        setWeekendDateBackground(mWeekendDateBackground);

        selectCurrentDate();
    }

    private void selectCurrentDate() {
        int day = mCurrentDate.get(Calendar.DAY_OF_MONTH);

        setDateTextAppearance(getContext(), day, mTheme == THEME_LIGHT ? R.style.WeekDayDateTextAppearanceDark : R.style.WeekDayDateTextAppearance);
        setDateBackground(day, mTheme == THEME_LIGHT ? R.drawable.round_shape_dark_selector : R.drawable.round_shape_selector);

        if (isTodayInCurrentMonth() && !isCurrentDayToday()) {
            Calendar today = Calendar.getInstance();
            day = today.get(Calendar.DAY_OF_MONTH);
            setDateBackground(day, R.drawable.round_shape_selected);
        }
    }

    private boolean isCurrentDayToday() {
        Calendar today = Calendar.getInstance();

        return mCurrentDate.get(Calendar.YEAR) == today.get(Calendar.YEAR) && mCurrentDate.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR);
    }

    private boolean isTodayInCurrentMonth() {
        Calendar today = Calendar.getInstance();
        return mCurrentDate.get(Calendar.YEAR) == today.get(Calendar.YEAR) && mCurrentDate.get(Calendar.MONTH) == today.get(Calendar.MONTH);
    }

    /**
     * Set Day labels starting with sunday
     *
     * @param resId
     */
    public void setDayLabels(@ArrayRes int resId) {
        String[] data = getContext().getResources().getStringArray(resId);
        if (data == null || data.length < 7) {
            throw new RuntimeException("There should be 7 values for days");
        }
        for (int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; i++) {
            mDaysViewMap.get(i).setText(data[i - 1]);
        }
    }

    /**
     * Set Background for all Weekday Day Labels
     *
     * @param resId
     */
    public void setWeekDayBackground(@DrawableRes int resId) {
        for (int i = Calendar.MONDAY; i < Calendar.SATURDAY; i++) {
            setDayBackground(i, resId);
        }
    }

    /**
     * Set Background for all Weekend Day Labels
     *
     * @param resId
     */
    public void setWeekendDayBackground(@DrawableRes int resId) {
        setDayBackground(Calendar.SATURDAY, resId);
        setDayBackground(Calendar.SUNDAY, resId);
    }

    /**
     * Set Background for all Weekday Date Labels
     *
     * @param resId
     */
    public void setWeekdayDateBackground(@DrawableRes int resId) {
        mWeekdayDateBackground = resId;
        for (int i = Calendar.MONDAY; i < Calendar.SATURDAY; i++) {
            for (int j = 1; j <= 5; j++) {
                mDateViewMap.get(i + ((j - 1) * 7)).setBackgroundResource(resId);
            }
        }
    }

    /**
     * Set Background for all Weekend Date Labels
     *
     * @param resId
     */
    public void setWeekendDateBackground(@DrawableRes int resId) {
        mWeekendDateBackground = resId;
        for (int j = 1; j <= 5; j++) {
            mDateViewMap.get(Calendar.SUNDAY + ((j - 1) * 7)).setBackgroundResource(resId);
            mDateViewMap.get(Calendar.SATURDAY + ((j - 1) * 7)).setBackgroundResource(resId);
        }
    }

    /**
     * Set Background for specific Day Label
     *
     * @param day
     * @param resId
     */
    public void setDayBackground(@IntRange(from = Calendar.SUNDAY, to = Calendar.SATURDAY) int day, @DrawableRes int resId) {
        TextView textView = mDaysViewMap.get(day);
        textView.setBackgroundResource(resId);
    }

    /**
     * Set Background for specific Date Label
     *
     * @param date
     * @param resId
     */
    public void setDateBackground(@IntRange(from = 1, to = 31) int date, @DrawableRes int resId) {
        int dayOfWeek = getFirstDayOfWeekOfMonth(mCurrentDate.getTimeInMillis());
        TextView textView = mDateViewMap.get(dayOfWeek + date - 1);
        textView.setBackgroundResource(resId);
    }

    /**
     * Set Text Appearance for all Days of Selected day(MON,TUE etc) and the Day Label itself
     *
     * @param context
     * @param day
     * @param resId
     */
    public void setDayWithDateTextAppearance(Context context, @IntRange(from = Calendar.SUNDAY, to = Calendar.SATURDAY) int day, @StyleRes int resId) {
        setDayTextAppearance(context, day, resId);
        for (int j = 1; j <= 5; j++) {
            setTextAppearance(context, mDateViewMap.get(day + ((j - 1) * 7)), resId);
        }
    }

    /**
     * Set Text Appearance for a specific Day Label
     *
     * @param context
     * @param day
     * @param resId
     */
    public void setDayTextAppearance(Context context, @IntRange(from = Calendar.SUNDAY, to = Calendar.SATURDAY) int day, @StyleRes int resId) {
        TextView textView = mDaysViewMap.get(day);
        setTextAppearance(context, textView, resId);
    }

    /**
     * Set Text Appearance for a specific the Date Label
     *
     * @param context
     * @param date
     * @param resId
     */
    public void setDateTextAppearance(Context context, @IntRange(from = 1, to = 31) int date, @StyleRes int resId) {
        int dayOfWeek = getFirstDayOfWeekOfMonth(mCurrentDate.getTimeInMillis());
        TextView textView = mDateViewMap.get(dayOfWeek + date - 1);
        setTextAppearance(context, textView, resId);
    }

    /**
     * Set Text Appearance for all Week Day Labels
     *
     * @param context
     * @param resId
     */
    public void setWeekdayTextAppearance(Context context, @StyleRes int resId) {
        for (int i = Calendar.MONDAY; i < Calendar.SATURDAY; i++) {
            setTextAppearance(context, mDaysViewMap.get(i), resId);
        }
    }

    /**
     * Set Text Appearance for all WeekEnd Day Labels
     *
     * @param context
     * @param resId
     */
    public void setWeekendTextAppearance(Context context, @StyleRes int resId) {
        setTextAppearance(context, mDaysViewMap.get(Calendar.SATURDAY), resId);
        setTextAppearance(context, mDaysViewMap.get(Calendar.SUNDAY), resId);
    }

    /**
     * Set Text Appearance for all Week Day Date Labels
     *
     * @param context
     * @param resId
     */
    public void setWeekdayDateTextAppearance(Context context, @StyleRes int resId) {
        mWeekdayDateTextAppearance = resId;
        for (int i = Calendar.MONDAY; i < Calendar.SATURDAY; i++) {
            for (int j = 1; j <= 5; j++) {
                setTextAppearance(context, mDateViewMap.get(i + ((j - 1) * 7)), resId);
            }
        }
    }

    /**
     * Set Text Appearance for all WeekEnd Date Labels
     *
     * @param context
     * @param resId
     */
    public void setWeekendDateTextAppearance(Context context, @StyleRes int resId) {
        mWeekendDateTextAppearance = resId;
        for (int j = 1; j <= 5; j++) {
            setTextAppearance(context, mDateViewMap.get(Calendar.SUNDAY + ((j - 1) * 7)), resId);
            setTextAppearance(context, mDateViewMap.get(Calendar.SATURDAY + ((j - 1) * 7)), resId);
        }
    }


    private void setTextAppearance(Context context, TextView textView, int resId) {
        if (Build.VERSION.SDK_INT < 23) {
            textView.setTextAppearance(context, resId);
        } else {
            textView.setTextAppearance(resId);
        }
    }

    private int getFirstDayOfWeekOfMonth(long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    @Override
    public void onClick(View view) {
        if (view.getTag() != null) {
            Calendar selectedDate = (Calendar) view.getTag();
            if (mOnDateSelectedListener != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(selectedDate.getTimeInMillis());
                mOnDateSelectedListener.onDateSelected(calendar);
            }
            setCurrentDate(selectedDate);
            Log.d(MonthView.class.getSimpleName(), "Selected Date " + mSimpleDateFormat.format(selectedDate.getTime()));
        }
    }
}
