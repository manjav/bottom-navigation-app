package com.grantech.cinnagen.solife.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.grantech.cinnagen.solife.MainActivity;
import com.grantech.cinnagen.solife.R;

import ir.mirrajabi.persiancalendar.PersianCalendarView;
import ir.mirrajabi.persiancalendar.core.PersianCalendarHandler;
import ir.mirrajabi.persiancalendar.core.interfaces.OnDayClickedListener;
import ir.mirrajabi.persiancalendar.core.interfaces.OnMonthChangedListener;
import ir.mirrajabi.persiancalendar.core.models.CalendarEvent;
import ir.mirrajabi.persiancalendar.core.models.PersianDate;

/**
 * Created by Belal on 1/23/2018.
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
        calendar.addLocalEvent(new CalendarEvent(today, "Custom event", false));
        calendar.addLocalEvent(new CalendarEvent(today.clone().rollDay(2, true), "Custom event 2", true));
        calendar.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onChanged(PersianDate date) {
                Toast.makeText(getActivity(), calendar.getMonthName(date), Toast.LENGTH_SHORT).show();
            }
        });
        persianCalendarView.setOnDayClickedListener(new OnDayClickedListener() {
            @Override
            public void onClick(PersianDate date)
            {
                for (CalendarEvent e : calendar.getAllEventsForDay(date))
                    Toast.makeText(getActivity(), e.getTitle(), Toast.LENGTH_LONG).show();


                calendar.addLocalEvent(new CalendarEvent(
                        today.clone().rollDay(2, false), "Some event that will be added in runtime", false
                ));
                persianCalendarView.update();
            }
        });

        //        calendar.setHighlightOfficialEvents(false);
        TextView txtDayMonth = view.findViewById(R.id.txt_day_month);
        TextView txtYear = view.findViewById(R.id.txt_year);

        String dayAndMonth = calendar.getWeekDayName(today) + calendar.formatNumber(today.getDayOfMonth()) + calendar.getMonthName(today);
        txtDayMonth.setText(dayAndMonth);
        txtYear.setText(calendar.formatNumber(today.getYear()));

        calendar.setColorBackground(getResources().getColor(android.R.color.holo_blue_dark));
        persianCalendarView.update();
    }
}
