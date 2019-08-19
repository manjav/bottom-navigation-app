package com.grantech.cinnagen.solife.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.grantech.cinnagen.solife.R;

import ir.mirrajabi.persiancalendar.PersianCalendarView;
import ir.mirrajabi.persiancalendar.core.PersianCalendarHandler;
import ir.mirrajabi.persiancalendar.core.models.PersianDate;

/**
 * Created by ManJav on 1/23/2019.
 */

public class DateFragment extends BaseFragment
{
    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_date, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        final PersianCalendarView persianCalendarView = view.findViewById(R.id.persian_calendar);
        final PersianCalendarHandler calendar = persianCalendarView.getCalendar();
        final PersianDate today = calendar.getToday();
        final TextView txtYear = view.findViewById(R.id.txt_year_month);
        txtYear.setText(calendar.formatNumber(calendar.getMonthName(today) + " " + today.getYear()));
        calendar.setDaysFontSize(16);
//        calendar.setTypeface(FontsOverride.getTypeface("MONOSPACE"));
//        calendar.addLocalEvent(new CalendarEvent(today, "Custom event", false));
//        calendar.addLocalEvent(new CalendarEvent(today.clone().rollDay(2, true), "Custom event 2", false));
//        calendar.setHighlightOfficialEvents(false);
        calendar.setOnMonthChangedListener(date -> txtYear.setText(calendar.formatNumber(calendar.getMonthName(date) + " " + date.getYear())));
        persianCalendarView.update();
    }
}
