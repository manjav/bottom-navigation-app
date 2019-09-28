package com.grantech.cinnagen.solife.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.controls.PickerInput;
import com.grantech.cinnagen.solife.utils.FontsOverride;
import com.grantech.cinnagen.solife.utils.Fragments;
import com.grantech.cinnagen.solife.utils.PatientPrefs;

/**
 * Created by ManJav on 9/28/2019.
 */

public class InjectionBodyFragment extends BaseFragment
{
    @Override
    @SuppressLint("InflateParams")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_injection_body, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.inject_body_abdomen_button).setOnClickListener(this);
        view.findViewById(R.id.inject_body_leg_right_button).setOnClickListener(this);
        view.findViewById(R.id.inject_body_leg_left_button).setOnClickListener(this);
        ((PickerInput)view.findViewById(R.id.inject_body_pickerInput)).setText(FontsOverride.convertToPersianDigits(PatientPrefs.getInstance().maintainDate.getPersianLongDateAndTime()));

    }

    @Override
    public void onClick(View view)
    {
        Bundle args = new Bundle();
        args.putInt("pos", view.getId());
        Fragments.getInstance().loadFragment(activity, R.dimen.position_injection_log, 0, args);
    }
}