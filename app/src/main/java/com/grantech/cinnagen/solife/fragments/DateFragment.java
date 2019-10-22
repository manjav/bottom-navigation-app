package com.grantech.cinnagen.solife.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import com.grantech.cinnagen.solife.R;

import java.util.Objects;

import ir.mirrajabi.persiancalendar.core.Constants;
import ir.mirrajabi.persiancalendar.core.PersianCalendarHandler;
import ir.mirrajabi.persiancalendar.core.adapters.CalendarAdapter;
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

        this.calendar = PersianCalendarHandler.getInstance(getContext());
        this.calendar.setOnEventUpdateListener(this::createViewPagers);
        this.calendar.setOnMonthChangedListener(this::changeHeader);

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

/*    public void changeMonth(int position) {
        viewPager.setCurrentItem(viewPager.getCurrentItem() + position, true);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void addEventOnCalendar(PersianDate persianDate) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI);

        CivilDate civil = DateConverter.persianToCivil(persianDate);

        intent.putExtra(CalendarContract.Events.DESCRIPTION, calendar.dayTitleSummary(persianDate));

        Calendar time = Calendar.getInstance();
        time.set(civil.getYear(), civil.getMonth() - 1, civil.getDayOfMonth());

        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, time.getTimeInMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, time.getTimeInMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);

        startActivity(intent);
    }

    private void bringTodayYearMonth() {
        Intent intent = new Intent(Constants.BROADCAST_INTENT_TO_MONTH_FRAGMENT);
        intent.putExtra(Constants.BROADCAST_FIELD_TO_MONTH_FRAGMENT, Constants.BROADCAST_TO_MONTH_FRAGMENT_RESET_DAY);
        intent.putExtra(Constants.BROADCAST_FIELD_SELECT_DAY, -1);

        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);

        if (viewPager.getCurrentItem() != Constants.MONTHS_LIMIT / 2) {
            viewPager.setCurrentItem(Constants.MONTHS_LIMIT / 2);
        }
    }

    public void bringDate(PersianDate date) {
        PersianDate today = calendar.getToday();
        mViewPagerPosition = (today.getYear() - date.getYear()) * 12 + today.getMonth() - date.getMonth();
        viewPager.setCurrentItem(mViewPagerPosition + Constants.MONTHS_LIMIT / 2);

        Intent intent = new Intent(Constants.BROADCAST_INTENT_TO_MONTH_FRAGMENT);
        intent.putExtra(Constants.BROADCAST_FIELD_TO_MONTH_FRAGMENT, mViewPagerPosition);
        intent.putExtra(Constants.BROADCAST_FIELD_SELECT_DAY, date.getDayOfMonth());

        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }*/

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
