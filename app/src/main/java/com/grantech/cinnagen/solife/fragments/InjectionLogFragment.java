package com.grantech.cinnagen.solife.fragments;

import android.content.Context;
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
import com.grantech.cinnagen.solife.utils.AlarmReceiver;
import com.grantech.cinnagen.solife.utils.Alarms;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_injection_log, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        view.setOnClickListener(this);

        // set region
        Point selectedRegion = new Point(180, 40);
        int pos = Objects.requireNonNull(getArguments()).getInt("pos");
        view.findViewById(R.id.inject_log_inner_shadow_bottom).setVisibility(pos == R.id.inject_body_abdomen_button ? View.VISIBLE : View.INVISIBLE);
        view.findViewById(R.id.inject_log_inner_shadow_right).setVisibility(pos == R.id.inject_body_leg_right_button ? View.VISIBLE : View.INVISIBLE);
        view.findViewById(R.id.inject_log_inner_shadow_left).setVisibility(pos == R.id.inject_body_leg_left_button ? View.VISIBLE : View.INVISIBLE);
        switch ( pos )
        {
            case R.id.inject_body_leg_right_button: selectedRegion = new Point(100, 300); break;
            case R.id.inject_body_leg_left_button: selectedRegion = new Point(260, 300); break;
        }
        board = view.findViewById(R.id.injection_log_view);
        board.setPrevPoint(Prefs.getInstance().getInt(Prefs.KEY_PREV_X, 0), Prefs.getInstance().getInt(Prefs.KEY_PREV_Y, 0));
        ((PickerInput)view.findViewById(R.id.inject_log_pickerInput)).setText(FontsOverride.convertToPersianDigits(new PersianCalendar().getPersianLongDateAndTime()));
    }

    @Override
    public void onClick(View view)
    {
        if( view.getId() == R.id.submit_button )
        {
            if( !board.isNextPointVisibility() )
            {
                Toast.makeText(getContext(), R.string.injection_log_warn, Toast.LENGTH_LONG).show();
                return;
            }

            long next = System.currentTimeMillis() + Prefs.getInstance().getInt(Prefs.KEY_DOSE_GAP, 14) * 86400000;
            Prefs.getInstance().setLong(Prefs.KEY_PREV, System.currentTimeMillis());
            Prefs.getInstance().setLong(Prefs.KEY_NEXT, next);
            Prefs.getInstance().setInt(Prefs.KEY_PREV_X, (int) (board.getNextPoint().x/getResources().getDisplayMetrics().density));
            Prefs.getInstance().setInt(Prefs.KEY_PREV_Y, (int) (board.getNextPoint().y/getResources().getDisplayMetrics().density));

            notifyNextInjection(getContext(), next);
            Toast.makeText(getContext(), R.string.injection_log_fine, Toast.LENGTH_LONG).show();

            activity.finish();
            Fragments.getInstance().clearStack(activity);
            return;
        }

        board.setNextPointVisibility(true);
        submitButton.setAlpha(1);
    }

    static void notifyNextInjection(Context context, long next) {
        Alarms.cancel(context, AlarmReceiver.class, -1);

        String message = context.getResources().getStringArray(R.array.dose_alarm_1)[Prefs.getInstance().getInt(Prefs.KEY_ALARM_1, 0)];
        Alarms.schedule(context, AlarmReceiver.class, next - 86400000, 1, "", context.getResources().getString(R.string.app_name), message, null, "ftp://dim-20", null, null);

        message = context.getResources().getStringArray(R.array.dose_alarm_2)[Prefs.getInstance().getInt(Prefs.KEY_ALARM_2, 0)];
        Alarms.schedule(context, AlarmReceiver.class, next, 1, "", context.getResources().getString(R.string.app_name), message, null, "ftp://dim-20", null, null);

        message = context.getResources().getStringArray(R.array.dose_alarm_3)[Prefs.getInstance().getInt(Prefs.KEY_ALARM_3, 0)];
        Alarms.schedule(context, AlarmReceiver.class, next + 86400000, 1, "", context.getResources().getString(R.string.app_name), message);
    }
}