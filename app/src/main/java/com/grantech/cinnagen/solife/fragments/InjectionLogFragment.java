package com.grantech.cinnagen.solife.fragments;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.controls.InjectionBoard;
import com.grantech.cinnagen.solife.controls.PickerInput;
import com.grantech.cinnagen.solife.utils.FontsOverride;
import com.grantech.cinnagen.solife.utils.Fragments;
import com.grantech.cinnagen.solife.utils.PersianCalendar;
import com.grantech.cinnagen.solife.utils.Prefs;

import java.util.Objects;

/**
 * Created by ManJav on 9/28/2019.
 */

public class InjectionLogFragment extends InjectionBaseFragment
{
    private InjectionBoard board;

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
        board = view.findViewById(R.id.injection_log_view);
        board.setPoint(selectedRegion.x, selectedRegion.y);
        board.setOnClickListener(this);

        PersianCalendar maintainDate = new PersianCalendar(Prefs.getInstance().getLong(Prefs.KEY_DOSE_MAINTAIN, 0));
        ((PickerInput)view.findViewById(R.id.inject_log_pickerInput)).setText(FontsOverride.convertToPersianDigits(maintainDate.getPersianLongDateAndTime()));
    }

    @Override
    public void onClick(View view)
    {
        if( view.getId() == R.id.submit_button )
        {
            if( !board.isPointVisibility() )
            {
                Toast.makeText(getContext(), "اول شما دوست عزیز!", Toast.LENGTH_LONG).show();
                return;
            }

            activity.finish();
            Fragments.getInstance().clearStack(activity);
            return;
        }

        board.setPointVisibility(true);
        board.invalidate();
        submitButton.setAlpha(1);
    }
}