package com.grantech.cinnagen.solife.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.adapters.CheckableListAdapter;
import com.grantech.cinnagen.solife.controls.PickerInput;
import com.grantech.cinnagen.solife.utils.FontsOverride;
import com.grantech.cinnagen.solife.utils.Fragments;
import com.grantech.cinnagen.solife.utils.PersianCalendar;
import com.grantech.cinnagen.solife.utils.Prefs;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;

import java.util.TimeZone;

/**
 * Created by ManJav on 1/23/2019.
 */

public class MedicationDoseFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener, PickerInput.OnClickListener, AdapterView.OnItemClickListener
{
    private PersianCalendar startDate;
    private PickerInput startDoseInput;
    private PersianCalendar maintainDate;
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

        // select dose by milligrams
        RadioGroup radioGroup = view.findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener((group, checkedId) ->Prefs.getInstance().setInt(Prefs.KEY_DOSE_MG, checkedId==R.id.dose_radio_80 ? 1 : 0));

        // start dose date selection
        startDate = new PersianCalendar(Prefs.getInstance().getLong(Prefs.KEY_DOSE_START, System.currentTimeMillis()));
        startDoseInput = view.findViewById(R.id.dose_start_date_input);
        startDoseInput.setText(FontsOverride.convertToPersianDigits(startDate.getPersianShortDate()));
        startDoseInput.setOnClickListener(this);

        // select gap of injections
        ListView gapList = view.findViewById(R.id.dose_gap_list);
        gapList.setAdapter(new CheckableListAdapter(getContext(), -1, getResources().getStringArray(R.array.dose_gaps)));
        gapList.setItemChecked(Prefs.getInstance().getInt(Prefs.KEY_DOSE_GAP, 14) == 7 ? 1 : 0, true);
        gapList.setOnItemClickListener(this);

        // maintain dose date selection
        long l = Prefs.getInstance().getLong(Prefs.KEY_DOSE_MAINTAIN, 0);
        maintainDate = new PersianCalendar(l);
        maintainDoseInput = view.findViewById(R.id.dose_maintain_date_input);
        maintainDoseInput.setOnClickListener(this);
        if( l > 0 )
            maintainDoseInput.setText(FontsOverride.convertToPersianDigits(maintainDate.getPersianShortDate()));

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
            datePicker = DatePickerDialog.newInstance(this, startDate.getPersianYear(), startDate.getPersianMonth(), startDate.getPersianDay());
        else
            datePicker = DatePickerDialog.newInstance(this, maintainDate.getPersianYear(), maintainDate.getPersianMonth(), maintainDate.getPersianDay());

        datePicker.setOnCancelListener(dialogInterface -> Log.d(Fragments.TAG, "Dialog was cancelled"));
        datePicker.show(activity.getFragmentManager(), tag);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth)
    {
        if( view.getTag().equals("start") )
        {
            startDate.setPersianDate(year, monthOfYear, dayOfMonth);
            startDoseInput.setText(FontsOverride.convertToPersianDigits(startDate.getPersianShortDate()));
            Prefs.getInstance().setLong(Prefs.KEY_DOSE_START, startDate.getTimeInMillis());
        }
        else
        {
            maintainDate.setPersianDate(year, monthOfYear, dayOfMonth);
            maintainDoseInput.setText(FontsOverride.convertToPersianDigits(maintainDate.getPersianShortDate()));
            Prefs.getInstance().setLong(Prefs.KEY_DOSE_MAINTAIN, maintainDate.getTimeInMillis());
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Prefs.getInstance().setInt(Prefs.KEY_DOSE_GAP, position == 1 ? 7 : 14);
    }
}