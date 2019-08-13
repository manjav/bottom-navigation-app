package com.grantech.cinnagen.solife.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.grantech.cinnagen.solife.R;

/**
 * Created by ManJav on 1/23/2019.
 */

public class InjectionFragment extends BaseFragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_injection, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        Button prepButton = view.findViewById(R.id.prep_button);
        prepButton.setOnClickListener(this);

        CardView medicationButton = view.findViewById(R.id.medication_button);
        medicationButton.setOnClickListener(this);

        CardView alarmButton = view.findViewById(R.id.alarm_button);
        alarmButton.setOnClickListener(this);
    }
}