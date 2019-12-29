package com.grantech.cinnagen.solife.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.grantech.cinnagen.solife.R;

/**
 * Created by ManJav on 9/26/2019.
 */

public class InjectionStepsFragment extends InjectionIconFragment
{
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_injection_steps, container, false);
    }
}