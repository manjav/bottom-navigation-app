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
        view.setOnClickListener(this);

        // set region
        Point selectedRegion = new Point(180, 40);
        switch ( Objects.requireNonNull(getArguments()).getInt("pos") )
        {
            case R.id.inject_body_leg_right_button: selectedRegion = new Point(100, 300); break;
            case R.id.inject_body_leg_left_button: selectedRegion = new Point(260, 300); break;
        }
        board = view.findViewById(R.id.injection_log_view);
        board.setPoint(selectedRegion.x, selectedRegion.y);

        ((PickerInput)view.findViewById(R.id.inject_log_pickerInput)).setText(FontsOverride.convertToPersianDigits(new PersianCalendar().getPersianLongDateAndTime()));
    }

    @Override
    public void onClick(View view)
    {
        if( view.getId() == R.id.submit_button )
        {
            if( !board.isPointVisibility() )
            {
                Toast.makeText(getContext(), R.string.injection_log_warn, Toast.LENGTH_LONG).show();
                return;
            }

            Prefs.getInstance().setLong(Prefs.KEY_PREV, System.currentTimeMillis());
            Prefs.getInstance().setLong(Prefs.KEY_NEXT, System.currentTimeMillis() + Prefs.getInstance().getInt(Prefs.KEY_DOSE_GAP, 14) * 24 * 3600000);
            Prefs.getInstance().setInt(Prefs.KEY_PREV_X, (int) (board.getPoint().x/getResources().getDisplayMetrics().density));
            Prefs.getInstance().setInt(Prefs.KEY_PREV_Y, (int) (board.getPoint().y/getResources().getDisplayMetrics().density));
//            Log.i(Fragments.TAG, board.getPoint().x + " === " + board.getPoint().y);

            activity.finish();
            Fragments.getInstance().clearStack(activity);
            return;
        }

        board.setPointVisibility(true);
        submitButton.setAlpha(1);
    }
}