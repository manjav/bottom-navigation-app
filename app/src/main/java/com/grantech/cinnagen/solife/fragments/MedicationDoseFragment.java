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
import com.grantech.cinnagen.solife.utils.FontsOverride;
import com.grantech.cinnagen.solife.utils.Fragments;
import com.grantech.cinnagen.solife.utils.PatientPrefs;
import com.grantech.cinnagen.solife.controls.PickerInput;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;

/**
 * Created by ManJav on 1/23/2019.
 */

public class MedicationDoseFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener, PickerInput.OnClickListener
{
    private PickerInput startDoseInput;
    private PickerInput maintainDoseInput;

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_medication_dose, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        startDoseInput = view.findViewById(R.id.dose_start_date_input);
        startDoseInput.setText(FontsOverride.convertToPersianDigits(PatientPrefs.getInstance().startDate.getPersianShortDate()));
        startDoseInput.setOnClickListener(this);

        maintainDoseInput = view.findViewById(R.id.dose_maintain_date_input);
        maintainDoseInput.setText(FontsOverride.convertToPersianDigits(PatientPrefs.getInstance().maintainDate.getPersianShortDate()));
        maintainDoseInput.setOnClickListener(this);

        view.findViewById(R.id.dose_date_finish).setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch( view.getId() )
        {
            case R.id.dose_start_date_input:
                changeDate("start");
                return;

            case R.id.dose_maintain_date_input:
                changeDate("maintain");
                return;

            case R.id.dose_date_finish:
                Fragments.getInstance().loadFragment(activity, R.dimen.position_medication_time);
        }
    }

    private void changeDate(String tag)
    {
        DatePickerDialog datePicker;
        if( tag.equals("start") )
            datePicker = DatePickerDialog.newInstance(this, PatientPrefs.getInstance().startDate.getPersianYear(), PatientPrefs.getInstance().startDate.getPersianMonth(), PatientPrefs.getInstance().startDate.getPersianDay());
        else
            datePicker = DatePickerDialog.newInstance(this, PatientPrefs.getInstance().maintainDate.getPersianYear(), PatientPrefs.getInstance().maintainDate.getPersianMonth(), PatientPrefs.getInstance().maintainDate.getPersianDay());

        datePicker.setOnCancelListener(dialogInterface -> Log.d(Fragments.TAG, "Dialog was cancelled"));
        datePicker.show(activity.getFragmentManager(), tag);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth)
    {
        if( view.getTag().equals("start") )
        {
            PatientPrefs.getInstance().startDate.setPersianDate(year, monthOfYear, dayOfMonth);
            startDoseInput.setText(FontsOverride.convertToPersianDigits(PatientPrefs.getInstance().startDate.getPersianShortDate()));
            PatientPrefs.getInstance().maintainDate.setPersianDate(year, monthOfYear, dayOfMonth, 14 * 24, 0, 0);
            maintainDoseInput.setText(FontsOverride.convertToPersianDigits(PatientPrefs.getInstance().maintainDate.getPersianShortDate()));
        }
        else
        {
            PatientPrefs.getInstance().maintainDate.setPersianDate(year, monthOfYear, dayOfMonth);
            maintainDoseInput.setText(FontsOverride.convertToPersianDigits(PatientPrefs.getInstance().maintainDate.getPersianShortDate()));
        }
    }
}