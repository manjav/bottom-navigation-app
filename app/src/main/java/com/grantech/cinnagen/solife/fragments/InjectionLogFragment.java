package com.grantech.cinnagen.solife.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.controls.InjectionBoard;
import com.grantech.cinnagen.solife.controls.PickerInput;
import com.grantech.cinnagen.solife.utils.AlarmReceiver;
import com.grantech.cinnagen.solife.utils.Alarms;
import com.grantech.cinnagen.solife.utils.FontsOverride;
import com.grantech.cinnagen.solife.utils.Fragments;
import com.grantech.cinnagen.solife.utils.PersianCalendar;
import com.grantech.cinnagen.solife.utils.Prefs;

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

        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        boolean isTall = ((float)dm.heightPixels / (float)dm.widthPixels) > 1.8;

        view.findViewById(R.id.inject_log_pickerInput).setVisibility(isTall ? View.VISIBLE : View.INVISIBLE);

        board = view.findViewById(R.id.injection_log_view);

        ConstraintLayout.LayoutParams boardLayout = (ConstraintLayout.LayoutParams) board.getLayoutParams();
        boardLayout.matchConstraintPercentHeight = isTall ? 0.59f : 0.7f;
        board.setLayoutParams(boardLayout);

        ConstraintLayout.LayoutParams buttonLayout = (ConstraintLayout.LayoutParams) submitButton.getLayoutParams();
        if( isTall ){
            buttonLayout.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
            buttonLayout.bottomMargin = (int) (32 * getContext().getResources().getDisplayMetrics().density);
        }
        else {
            buttonLayout.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
            buttonLayout.topMargin = (int) (132 * getContext().getResources().getDisplayMetrics().density);
        }
        submitButton.setLayoutParams(buttonLayout);

        ((PickerInput)view.findViewById(R.id.inject_log_pickerInput)).setText(FontsOverride.convertToPersianDigits(new PersianCalendar().getPersianLongDateAndTime()));

        board.autoRegion = false;
        Handler mWaitHandler = new Handler();
        board.setPrevPoint(Prefs.getInstance().getInt(Prefs.KEY_PREV_X, 0), Prefs.getInstance().getInt(Prefs.KEY_PREV_Y, 0));
        board.invalidate();
        mWaitHandler.postDelayed(() -> {
        }, 100);  // Give 100 milliseconds delay.
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