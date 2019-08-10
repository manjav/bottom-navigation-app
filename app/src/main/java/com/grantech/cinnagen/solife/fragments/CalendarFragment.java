package com.grantech.cinnagen.solife.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.grantech.cinnagen.solife.R;

import ir.mirrajabi.persiancalendar.PersianCalendarView;
import ir.mirrajabi.persiancalendar.core.PersianCalendarHandler;
import ir.mirrajabi.persiancalendar.core.interfaces.OnMonthChangedListener;
import ir.mirrajabi.persiancalendar.core.models.PersianDate;

/**
 * Created by ManJav on 1/23/2019.
 */

public class CalendarFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar_layout, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        final PersianCalendarView persianCalendarView = view.findViewById(R.id.persian_calendar);
        final PersianCalendarHandler calendar = persianCalendarView.getCalendar();
        final PersianDate today = calendar.getToday();
        final TextView txtYear = view.findViewById(R.id.txt_year_month);
        txtYear.setText(calendar.formatNumber(calendar.getMonthName(today) + " " + today.getYear()));
//        calendar.setTypeface(FontsOverride.getTypeface("MONOSPACE"));
        calendar.setDaysFontSize(16);
        calendar.setDaysFontSize(18);
//        calendar.addLocalEvent(new CalendarEvent(today, "Custom event", false));
//        calendar.addLocalEvent(new CalendarEvent(today.clone().rollDay(2, true), "Custom event 2", false));
        calendar.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onChanged(PersianDate date)
            {
                txtYear.setText(calendar.formatNumber(calendar.getMonthName(date) + " " + date.getYear()));
            }
        });
//        calendar.setHighlightOfficialEvents(false);
        persianCalendarView.update();
    }
}
