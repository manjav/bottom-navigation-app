package com.grantech.cinnagen.solife.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.adapters.CheckableListAdapter;
import com.grantech.cinnagen.solife.utils.Prefs;

/**
 * Created by ManJav on 1/23/2019.
 */

public class MedicationAlarmFragment extends BaseFragment implements AdapterView.OnItemClickListener, CompoundButton.OnCheckedChangeListener
{
    private View titleView;
    private View listGroup;
    private ListView firstList;
    private ListView secondList;
    private ListView thirdList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_medication_alarm, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        titleView = view.findViewById(R.id.dose_alarm_title);
        listGroup = view.findViewById(R.id.dose_alarm_lists_group);

        boolean settingMode = getArguments() != null && getArguments().containsKey("s");
        view.findViewById(R.id.dose_alarm_finish).setVisibility(settingMode ? View.GONE : View.VISIBLE);
        if( settingMode )
        {
            // notifications turn on / off
            Switch alarmSwitch = view.findViewById(R.id.dose_alarm_switch);
            alarmSwitch.setChecked(Prefs.getInstance().getBoolean(Prefs.KEY_ALARM_NOTIFICATION, true));
            alarmSwitch.setOnCheckedChangeListener(this);

            view.findViewById(R.id.dose_alarm_switch_group).setVisibility(View.VISIBLE);
            titleView.setVisibility(View.GONE);
        }
        else
        {
            view.findViewById(R.id.dose_alarm_finish).setOnClickListener(this);
            view.findViewById(R.id.dose_alarm_switch_group).setVisibility(View.GONE);
            titleView.setVisibility(View.VISIBLE);
        }

        // select first choice
        firstList = view.findViewById(R.id.dose_alarm_list_one);
        firstList.setAdapter(new CheckableListAdapter(getContext(), -1, getResources().getStringArray(R.array.dose_alarm_1)));
        firstList.setItemChecked(Prefs.getInstance().getInt(Prefs.KEY_ALARM_1, 0), true);
        firstList.setOnItemClickListener(this);

        // select second choice
        secondList = view.findViewById(R.id.dose_alarm_list_two);
        secondList.setAdapter(new CheckableListAdapter(getContext(), -1, getResources().getStringArray(R.array.dose_alarm_2)));
        secondList.setItemChecked(Prefs.getInstance().getInt(Prefs.KEY_ALARM_2, 0), true);
        secondList.setOnItemClickListener(this);

        // select third choice
        thirdList = view.findViewById(R.id.dose_alarm_list_three);
        thirdList.setAdapter(new CheckableListAdapter(getContext(), -1, getResources().getStringArray(R.array.dose_alarm_3)));
        thirdList.setItemChecked(Prefs.getInstance().getInt(Prefs.KEY_ALARM_3, 0), true);
        thirdList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        if( parent.equals(firstList) )
            Prefs.getInstance().setInt(Prefs.KEY_ALARM_1, position);
        else if( parent.equals(secondList) )
            Prefs.getInstance().setInt(Prefs.KEY_ALARM_2, position);
        else if( parent.equals(thirdList) )
            Prefs.getInstance().setInt(Prefs.KEY_ALARM_3, position);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        Prefs.getInstance().setBoolean(Prefs.KEY_ALARM_NOTIFICATION, isChecked);
        titleView.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        listGroup.setVisibility(isChecked ? View.VISIBLE : View.GONE);
    }
}