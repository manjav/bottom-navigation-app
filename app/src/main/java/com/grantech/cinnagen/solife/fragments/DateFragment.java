package com.grantech.cinnagen.solife.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.utils.Fragments;
import com.grantech.cinnagen.solife.utils.PersianCalendar;
import com.grantech.cinnagen.solife.utils.Prefs;

import java.util.Objects;

import ir.mirrajabi.persiancalendar.core.Constants;
import ir.mirrajabi.persiancalendar.core.PersianCalendarHandler;
import ir.mirrajabi.persiancalendar.core.adapters.CalendarAdapter;
import ir.mirrajabi.persiancalendar.core.models.CalendarEvent;
import ir.mirrajabi.persiancalendar.core.models.PersianDate;

/**
 * Created by ManJav on 1/23/2019.
 */

public class DateFragment extends BaseFragment implements ViewPager.OnPageChangeListener
{
    private TextView headerView;
    private ViewPager viewPager;
    private PersianCalendarHandler calendar;

    private void changeHeader(PersianDate day) {
        headerView.setText(calendar.formatNumber(calendar.getMonthName(day) + " " + day.getYear()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_date, container, false);
        this.viewPager = view.findViewById(R.id.date_pager);
        this.headerView = view.findViewById(R.id.txt_year_month);

        view.findViewById(R.id.date_left_button).setOnClickListener(this);
        view.findViewById(R.id.date_right_button).setOnClickListener(this);

        this.calendar = PersianCalendarHandler.getInstance(getContext());
        this.calendar.setOnEventUpdateListener(this::createViewPagers);
        this.calendar.setOnMonthChangedListener(this::changeHeader);

        // create events
        int gap = Prefs.getInstance().getInt(Prefs.KEY_DOSE_GAP, 14);
        long next = Prefs.getInstance().getLong(Prefs.KEY_NEXT, 0);
        PersianCalendar p = new PersianCalendar(Prefs.getInstance().getLong(Prefs.KEY_PREV, 0));
        this.calendar.addLocalEvent(new CalendarEvent(new PersianDate(p.getPersianYear(), p.getPersianMonth() + 1, p.getPersianDay()), "", false));
        for (long i = 0; i <400; i++)
        {
            p.setTimeInMillis(next + i * gap * 24 * 3600000);
            this.calendar.addLocalEvent(new CalendarEvent(new PersianDate(p.getPersianYear(), p.getPersianMonth() + 1, p.getPersianDay()), "", false));
        }

        this.changeHeader(calendar.getToday());
        this.createViewPagers();
        return view;
    }

    private void createViewPagers()
    {
        this.viewPager.setAdapter(new CalendarAdapter(getChildFragmentManager()));
        this.viewPager.setCurrentItem(Constants.MONTHS_LIMIT / 2);
        this.viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.date_left_button)
            this.viewPager.setCurrentItem(this.viewPager.getCurrentItem() - 1);
        else if(view.getId() == R.id.date_right_button)
            this.viewPager.setCurrentItem(this.viewPager.getCurrentItem() + 1);
        super.onClick(view);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        int mViewPagerPosition = position - Constants.MONTHS_LIMIT / 2;

        Intent intent = new Intent(Constants.BROADCAST_INTENT_TO_MONTH_FRAGMENT);
        intent.putExtra(Constants.BROADCAST_FIELD_TO_MONTH_FRAGMENT, mViewPagerPosition);
        intent.putExtra(Constants.BROADCAST_FIELD_SELECT_DAY, -1);

        LocalBroadcastManager.getInstance(Objects.requireNonNull(getContext())).sendBroadcast(intent);
    }

    @Override
    public void onPageScrollStateChanged(int state) { }
}
