package com.grantech.cinnagen.solife.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.utils.Fragments;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

/**
 * Created by ManJav on 1/23/2019.
 */

public class MedicationDoseFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener
{
    private TextView startDoseInput;
    private TextView maintainDoseInput;

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
        view.findViewById(R.id.dose_start_date_input).setOnClickListener(this);
        view.findViewById(R.id.dose_maintain_date_input).setOnClickListener(this);
        view.findViewById(R.id.dose_date_finish).setOnClickListener(this);
    }


    @Override
    public void onClick(View view)
    {
        switch( view.getId() )
        {
            case R.id.dose_start_date_input:
                setStartDoseDate();
                return;

            case R.id.dose_maintain_date_input:
                setMaintainDoseDate();
                return;

            case R.id.dose_date_finish:
                Fragments.getInstance().loadFragment(activity, new MedicationTimeFragment(), R.dimen.position_medication_time, R.string.medication_settings);
        }
    }

    private void setStartDoseDate()
    {
        changeDate();
    }

    private void setMaintainDoseDate()
    {
        changeDate();
    }

    private void changeDate()
    {
        PersianCalendar now = new PersianCalendar();
        DatePickerDialog datePicker = DatePickerDialog.newInstance(this, now.getPersianYear(), now.getPersianMonth(), now.getPersianDay());
//        datePicker.setStyle(R.style.AppTheme, R.style.AppTheme);
        datePicker.setOnCancelListener(dialogInterface -> Log.d(Fragments.TAG, "Dialog was cancelled"));
        datePicker.show(activity.getFragmentManager(), "sssss");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth)
    {
        Log.i(Fragments.TAG, "MedicationDoseFragment year: " + year + ", monthOfYear: " + monthOfYear + ", dayOfMonth: " + dayOfMonth);
    }
}