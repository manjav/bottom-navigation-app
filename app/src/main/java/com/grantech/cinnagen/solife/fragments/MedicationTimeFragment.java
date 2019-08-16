package com.grantech.cinnagen.solife.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.utils.FontsOverride;
import com.grantech.cinnagen.solife.utils.Fragments;
import com.grantech.cinnagen.solife.utils.PatientPrefs;
import com.grantech.cinnagen.solife.utils.PersianCalendar;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;

/**
 * Created by ManJav on 1/23/2019.
 */

public class MedicationTimeFragment extends BaseFragment implements TimePickerDialog.OnTimeSetListener
{
    private TextView maintainTimeInput;

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

        maintainTimeInput = view.findViewById(R.id.dose_maintain_time_text);
        maintainTimeInput.setText(FontsOverride.convertToPersianDigits( PatientPrefs.getInstance().maintainDate.get(PersianCalendar.MINUTE) + " : " + PatientPrefs.getInstance().maintainDate.get(PersianCalendar.HOUR_OF_DAY)) );
//        maintainTimeInput.setText(FontsOverride.convertToPersianDigits( PatientPrefs.getInstance().maintainDate.getPersianShortDateTime()) );
        view.findViewById(R.id.dose_time_input).setOnClickListener(this);

        view.findViewById(R.id.dose_time_finish).setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch( view.getId() )
        {
            case R.id.dose_time_input:
                TimePickerDialog tpd = TimePickerDialog.newInstance(this, PatientPrefs.getInstance().maintainDate.get(PersianCalendar.HOUR_OF_DAY), PatientPrefs.getInstance().maintainDate.get(PersianCalendar.MINUTE), true);
    //        tpd.setThemeDark(modeDarkTime.isChecked());
    //        tpd.setTypeface(fontName);
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
        PatientPrefs.getInstance().maintainDate.setPersianDate(
                PatientPrefs.getInstance().maintainDate.getPersianYear(),
                PatientPrefs.getInstance().maintainDate.getPersianMonth(),
                PatientPrefs.getInstance().maintainDate.getPersianDay(),
                hourOfDay, minute, 0
                );
        maintainTimeInput.setText(FontsOverride.convertToPersianDigits( PatientPrefs.getInstance().maintainDate.get(PersianCalendar.MINUTE) + " : " + PatientPrefs.getInstance().maintainDate.get(PersianCalendar.HOUR_OF_DAY)) );
    }
}