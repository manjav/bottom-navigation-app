package com.grantech.cinnagen.solife.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.activities.FragmentsActivity;
import com.grantech.cinnagen.solife.activities.MainActivity;
import com.grantech.cinnagen.solife.utils.Fragments;
import com.grantech.cinnagen.solife.utils.Prefs;

import java.util.Objects;

/**
 * Created by ManJav on 1/23/2019.
 */

public class BaseFragment extends Fragment implements View.OnClickListener
{

    AppCompatActivity activity;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        Fragments.getInstance().setLocale(Objects.requireNonNull(getContext()));
        activity = (AppCompatActivity) getActivity();
    }

    @Override
    public void onClick(View view)
    {
        switch( view.getId() )
        {
            case R.id.prep_button:
                // prevent double injection in day
                if (System.currentTimeMillis() - Prefs.getInstance().getLong(Prefs.KEY_PREV, 0) < 86400000)
                {
                    Toast.makeText(getContext(), R.string.injection_log_double, Toast.LENGTH_LONG).show();
                    return;
                }

                // early injection warning
                if (System.currentTimeMillis() < Prefs.getInstance().getLong(Prefs.KEY_NEXT, 0))
                    Toast.makeText(getContext(), R.string.injection_log_early, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(activity.getApplicationContext(), FragmentsActivity.class);
                Bundle b = new Bundle();
                b.putInt("position", R.dimen.position_injection_start);
                intent.putExtras(b);
                startActivity(intent);
                return;

            case R.id.medication_button:
            case R.id.welcome_accept_button:
                Fragments.getInstance().loadFragment(activity, R.dimen.position_medication_dose);
                return;

            case R.id.alarm_button:
                Bundle args = new Bundle();
                args.putBoolean("s", true);
                Fragments.getInstance().loadFragment(activity, R.dimen.position_medication_alarms, 0, args);
                return;

            case R.id.dose_alarm_finish:
                if( !Prefs.getInstance().contains(Prefs.KEY_NUM_RUN) )
                {
                    Prefs.getInstance().setInt(Prefs.KEY_NUM_RUN, 1);
                    Toast.makeText(getContext(), R.string.medication_alarm_fine, Toast.LENGTH_LONG).show();
                    intent = new Intent(activity.getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    activity.finish();
                    return;
                }
                Fragments.getInstance().clearStack(activity);
                return;
        }
    }
}