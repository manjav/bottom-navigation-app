package com.grantech.cinnagen.solife.fragments;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.controls.InjectionBoard;
import com.grantech.cinnagen.solife.utils.Fragments;

import java.util.Objects;

/**
 * Created by ManJav on 9/28/2019.
 */

public class InjectionLogFragment extends InjectionBaseFragment
{
    @Override
    @SuppressLint("InflateParams")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_injection_log, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        // set region
        Point selectedRegion = new Point(180, 40);
        switch ( Objects.requireNonNull(getArguments()).getInt("pos") )
        {
            case R.id.inject_body_leg_right_button: selectedRegion = new Point(100, 300); break;
            case R.id.inject_body_leg_left_button: selectedRegion = new Point(260, 300); break;
        }
        InjectionBoard board = view.findViewById(R.id.injection_log_view);
        board.setPoint(selectedRegion.x, selectedRegion.y);
    }

    @Override
    public void onClick(View view)
    {
        activity.finish();
        Fragments.getInstance().clearStack(activity);
    }
}