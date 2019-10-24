package com.grantech.cinnagen.solife.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.controls.InjectionBoard;
import com.grantech.cinnagen.solife.utils.FontsOverride;
import com.grantech.cinnagen.solife.utils.PersianCalendar;
import com.grantech.cinnagen.solife.utils.Prefs;

/**
 * Created by ManJav on 1/23/2019.
 */

public class InjectionFragment extends BaseFragment
{
    private InjectionBoard board;

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_injection, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.prep_button).setOnClickListener(this);
        view.findViewById(R.id.medication_button).setOnClickListener(this);
        view.findViewById(R.id.alarm_button).setOnClickListener(this);
        board = view.findViewById(R.id.injection_view);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        PersianCalendar next = new PersianCalendar(Prefs.getInstance().getLong(Prefs.KEY_NEXT, 0));
        board.setNextTime(FontsOverride.convertToPersianDigits(next.getPersianDay()+"") + " " + next.getPersianMonthName());
        PersianCalendar prev = new PersianCalendar(Prefs.getInstance().getLong(Prefs.KEY_PREV, 0));
        board.setPrevTime(FontsOverride.convertToPersianDigits(prev.getPersianDay()+"") + " " + prev.getPersianMonthName());

        board.setPoint(Prefs.getInstance().getInt(Prefs.KEY_PREV_X, 0), Prefs.getInstance().getInt(Prefs.KEY_PREV_Y, 0));
    }
}