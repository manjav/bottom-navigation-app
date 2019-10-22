package com.grantech.cinnagen.solife.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.controls.PickerInput;
import com.grantech.cinnagen.solife.utils.FontsOverride;
import com.grantech.cinnagen.solife.utils.Fragments;
import com.grantech.cinnagen.solife.utils.PersianCalendar;
import com.grantech.cinnagen.solife.utils.Prefs;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;

import java.util.TimeZone;

/**
 * Created by ManJav on 1/23/2019.
 */

public class MedicationTimeFragment extends BaseFragment implements TimePickerDialog.OnTimeSetListener
{
    private PickerInput maintainTimeInput;
    private PersianCalendar maintainDate;

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_medication_time, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        maintainDate = new PersianCalendar(Prefs.getInstance().getLong(Prefs.KEY_DOSE_MAINTAIN, 0));
        maintainTimeInput = view.findViewById(R.id.dose_time_input);
        maintainTimeInput.setText(FontsOverride.convertToPersianDigits( maintainDate.get(PersianCalendar.MINUTE) + " : " + maintainDate.get(PersianCalendar.HOUR_OF_DAY)) );
        maintainTimeInput.setOnClickListener(this);
        view.findViewById(R.id.dose_time_finish).setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch( view.getId() )
        {
            case R.id.dose_time_input:
                TimePickerDialog tpd = TimePickerDialog.newInstance(this, maintainDate.get(PersianCalendar.HOUR_OF_DAY), maintainDate.get(PersianCalendar.MINUTE), true);
                tpd.setOnCancelListener(dialogInterface -> Log.d(Fragments.TAG, "Dialog was cancelled"));
                tpd.show(activity.getFragmentManager(), null);
                return;

            case R.id.dose_time_finish:
                Fragments.getInstance().loadFragment(activity, R.dimen.position_medication_alarms);
        }
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute)
    {
        maintainDate.setPersianDate(
                maintainDate.getPersianYear(),
                maintainDate.getPersianMonth(),
                maintainDate.getPersianDay(),
                hourOfDay, minute, 0
                );
        maintainTimeInput.setText(FontsOverride.convertToPersianDigits( maintainDate.get(PersianCalendar.MINUTE) + " : " + maintainDate.get(PersianCalendar.HOUR_OF_DAY)) );
        Prefs.getInstance().setLong(Prefs.KEY_DOSE_MAINTAIN, maintainDate.getTimeInMillis());
    }
}