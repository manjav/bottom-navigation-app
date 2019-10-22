package com.grantech.cinnagen.solife.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.activities.FragmentsActivity;
import com.grantech.cinnagen.solife.utils.Fragments;

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
        activity = (AppCompatActivity) getActivity();
    }

    @Override
    public void onClick(View view)
    {
        switch( view.getId() )
        {
            case R.id.prep_button:
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
                Fragments.getInstance().loadFragment(activity, R.dimen.position_medication_alarms);
                return;

            case R.id.dose_alarm_finish:
                Fragments.getInstance().clearStack(activity);
                return;
        }
    }
}