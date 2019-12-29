package com.grantech.cinnagen.solife.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.controls.InjectionBoard;
import com.grantech.cinnagen.solife.utils.FontsOverride;
import com.grantech.cinnagen.solife.utils.Fragments;
import com.grantech.cinnagen.solife.utils.PersianCalendar;
import com.grantech.cinnagen.solife.utils.Prefs;

/**
 * Created by ManJav on 9/26/2019.
 */

public class InjectionPrevFragment extends InjectionBaseFragment
{
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_injection_prev, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        PersianCalendar prev = new PersianCalendar(Prefs.getInstance().getLong(Prefs.KEY_PREV, 0));
        ((InjectionBoard)view.findViewById(R.id.injection_view)).setPoint(Prefs.getInstance().getInt(Prefs.KEY_PREV_X, 0), Prefs.getInstance().getInt(Prefs.KEY_PREV_Y, 0));
        ((TextView)view.findViewById(R.id.inject_prev_time)).setText(FontsOverride.convertToPersianDigits(prev.getPersianLongDateAndTime()));
    }

    @Override
    public void onClick(View view)
    {
        Fragments.getInstance().loadFragment(activity, R.dimen.position_injection_steps);
    }
}