package com.grantech.cinnagen.solife.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.adapters.CheckableListAdapter;
import com.grantech.cinnagen.solife.controls.PickerInput;
import com.grantech.cinnagen.solife.utils.FontsOverride;
import com.grantech.cinnagen.solife.utils.Fragments;
import com.grantech.cinnagen.solife.utils.PersianCalendar;
import com.grantech.cinnagen.solife.utils.Prefs;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;

/**
 * Created by ManJav on 1/23/2019.
 */

public class MedicationDoseFragment extends BaseFragment implements PickerInput.OnClickListener, AdapterView.OnItemClickListener, TimePickerDialog.OnTimeSetListener
{
    private PersianCalendar prevDate;
    private PersianCalendar nextDate;
    private PickerInput startDateInput;
    private PickerInput nextDateInput;
    private PickerInput nextTimeInput;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_medication_dose, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        Button submitButton = view.findViewById(R.id.dose_finish);
        prevDate = new PersianCalendar(Prefs.getInstance().getLong(Prefs.KEY_PREV, System.currentTimeMillis()));
        nextDate = new PersianCalendar(Prefs.getInstance().getLong(Prefs.KEY_NEXT, prevDate.getTimeInMillis() + Prefs.getInstance().getInt(Prefs.KEY_DOSE_GAP, 14) * 24 * 3600000));

        RadioGroup radioGroup = view.findViewById(R.id.dose_radio_group);
        startDateInput = view.findViewById(R.id.dose_start_input);
        nextDateInput = view.findViewById(R.id.dose_next_date_input);
        nextTimeInput = view.findViewById(R.id.dose_next_time_input);

        // select dose by milli-grams
        radioGroup.setOnCheckedChangeListener((group, checkedId) ->Prefs.getInstance().setInt(Prefs.KEY_DOSE_MG, checkedId==R.id.dose_radio_80 ? 1 : 0));

        // start dose date selection
        startDateInput.setText(FontsOverride.convertToPersianDigits(prevDate.getPersianShortDate()));
        startDateInput.setOnClickListener(this);

        nextTimeInput.setVisibility(View.GONE);

        // select gap of injections
        ListView gapList = view.findViewById(R.id.dose_gap_list);
        gapList.setAdapter(new CheckableListAdapter(getContext(), -1, getResources().getStringArray(R.array.dose_gaps)));
        gapList.setItemChecked(Prefs.getInstance().getInt(Prefs.KEY_DOSE_GAP, 14) == 7 ? 1 : 0, true);
        gapList.setOnItemClickListener(this);

        // maintain dose date selection
        nextDateInput.setOnClickListener(this);
        nextDateInput.setText(FontsOverride.convertToPersianDigits(nextDate.getPersianShortDate()));

        submitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch( view.getId() )
        {
            case R.id.dose_start_input:
                changeDate("prev");
                return;

            case R.id.dose_next_date_input:
                changeDate("next");
                return;

            case R.id.dose_next_time_input:
                changeTime();
                return;

            case R.id.dose_finish:
                Prefs.getInstance().setLong(Prefs.KEY_PREV, prevDate.getTimeInMillis());
                Prefs.getInstance().setLong(Prefs.KEY_NEXT, nextDate.getTimeInMillis());
                InjectionLogFragment.notifyNextInjection(getContext(), nextDate.getTimeInMillis());
                Fragments.getInstance().loadFragment(activity, R.dimen.position_medication_time);
        }
    }

    private void changeTime()
    {
        TimePickerDialog tpd = TimePickerDialog.newInstance(this, prevDate.get(PersianCalendar.HOUR_OF_DAY), prevDate.get(PersianCalendar.MINUTE), true);
        tpd.setOnCancelListener(dialogInterface -> Log.d(Fragments.TAG, "Dialog was cancelled"));
        tpd.show(activity.getFragmentManager(), null);
    }

    private void changeDate(String tag)
    {

        ir.hamsaa.persiandatepicker.util.PersianCalendar initDate = new ir.hamsaa.persiandatepicker.util.PersianCalendar(prevDate.getTimeInMillis());
        if( tag.equals("next") )
            initDate = new ir.hamsaa.persiandatepicker.util.PersianCalendar(prevDate.getTimeInMillis() + Prefs.getInstance().getInt(Prefs.KEY_DOSE_GAP, 14) * 24 * 3600000);

        PersianDatePickerDialog picker = new PersianDatePickerDialog(getContext())
                .setTodayButtonVisible(true)
                .setMinYear(1300)
                .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                .setInitDate(initDate)
                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
                .setShowInBottomSheet(true)
                .setListener(new Listener() {
                    @Override
                    public void onDateSelected(ir.hamsaa.persiandatepicker.util.PersianCalendar persianCalendar) {
                        onDateSet(tag, persianCalendar.getPersianYear(),persianCalendar.getPersianMonth() - 1, persianCalendar.getPersianDay() );
                    }

                    @Override
                    public void onDismissed() {
                    }
                });

        picker.show();
    }

    void onDateSet(String tag, int year, int monthOfYear, int dayOfMonth)
    {
        if( tag.equals("prev") )
        {
            prevDate.setPersianDate(year, monthOfYear, dayOfMonth);
            startDateInput.setText(FontsOverride.convertToPersianDigits(prevDate.getPersianShortDate()));
            Prefs.getInstance().setLong(Prefs.KEY_PREV, prevDate.getTimeInMillis());

            nextDate.setTimeInMillis(prevDate.getTimeInMillis() + Prefs.getInstance().getInt(Prefs.KEY_DOSE_GAP, 14) * 24 * 3600000);
            nextDateInput.setText(FontsOverride.convertToPersianDigits(nextDate.getPersianShortDate()));
            Prefs.getInstance().setLong(Prefs.KEY_NEXT, nextDate.getTimeInMillis());
        }
        else
        {
            nextDate.setPersianDate(year, monthOfYear, dayOfMonth);
            if (nextDate.getNumDays() <= prevDate.getNumDays()) {
                Toast.makeText(getContext(), R.string.medication_dose_maintain_alert, Toast.LENGTH_LONG).show();
                return;
            }
            nextDateInput.setText(FontsOverride.convertToPersianDigits(nextDate.getPersianShortDate()));
            Prefs.getInstance().setLong(Prefs.KEY_NEXT, nextDate.getTimeInMillis());
        }
        InjectionLogFragment.notifyNextInjection(getContext(), nextDate.getTimeInMillis());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Prefs.getInstance().setInt(Prefs.KEY_DOSE_GAP, position == 1 ? 7 : 14);
        nextDate.setTimeInMillis(prevDate.getTimeInMillis() + Prefs.getInstance().getInt(Prefs.KEY_DOSE_GAP, 14) * 24 * 3600000);
        nextDateInput.setText(FontsOverride.convertToPersianDigits(nextDate.getPersianShortDate()));
        Prefs.getInstance().setLong(Prefs.KEY_NEXT, nextDate.getTimeInMillis());
        InjectionLogFragment.notifyNextInjection(getContext(), nextDate.getTimeInMillis());
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute)
    {
        nextDate.setPersianDate(
                nextDate.getPersianYear(),
                nextDate.getPersianMonth(),
                nextDate.getPersianDay(),
                hourOfDay, minute, 0
        );
        nextTimeInput.setText(FontsOverride.convertToPersianDigits( nextDate.get(PersianCalendar.MINUTE) + " : " + nextDate.get(PersianCalendar.HOUR_OF_DAY)) );
        Prefs.getInstance().setLong(Prefs.KEY_NEXT, nextDate.getTimeInMillis());
        InjectionLogFragment.notifyNextInjection(getContext(), nextDate.getTimeInMillis());
    }
}