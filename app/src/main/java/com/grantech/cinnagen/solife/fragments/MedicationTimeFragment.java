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
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

/**
 * Created by ManJav on 1/23/2019.
 */

public class MedicationTimeFragment extends BaseFragment implements TimePickerDialog.OnTimeSetListener
{
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
        view.findViewById(R.id.dose_time_input).setOnClickListener(this);
        view.findViewById(R.id.dose_time_finish).setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch( view.getId() )
        {
            case R.id.dose_time_input:
                PersianCalendar now = new PersianCalendar();
                TimePickerDialog tpd = TimePickerDialog.newInstance(this, now.get(PersianCalendar.HOUR_OF_DAY), now.get(PersianCalendar.MINUTE), true);
    //        tpd.setThemeDark(modeDarkTime.isChecked());
    //        tpd.setTypeface(fontName);
                tpd.setOnCancelListener(dialogInterface -> Log.d(Fragments.TAG, "Dialog was cancelled"));
                tpd.show(activity.getFragmentManager(), "sssss");
                return;

            case R.id.dose_time_finish:
                Fragments.getInstance().clearStack(activity);
        }
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute)
    {
        Log.i(Fragments.TAG, "MedicationDoseFragment hourOfDay: " + hourOfDay + ", minute: " + minute);
    }
}