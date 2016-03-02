package com.tskk.android.calendarview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tskk.calendarview.MonthView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MonthView monthView = (MonthView) findViewById(R.id.month_view);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        //monthView.setCurrentDate(calendar);
    }
}
