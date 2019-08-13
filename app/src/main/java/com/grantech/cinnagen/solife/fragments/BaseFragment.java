package com.grantech.cinnagen.solife.fragments;

import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.utils.Fragments;

/**
 * Created by ManJav on 1/23/2019.
 */

public class BaseFragment extends Fragment implements View.OnClickListener
{

   @Override
    public void onClick(View view)
    {
        Log.i(Fragments.TAG, "InjectionFragment id: " + view.getId());

        Fragment fragment = null;
        int title = 0;
        int position  = 0;

        switch( view.getId() )
        {
            case R.id.prep_button:
                position = R.dimen.position_injection_start;
                title = R.string.welcome_title;
                fragment = new WelcomeFragment();
                break;

            case R.id.medication_button:
            case R.id.welcome_accept_button:
                position = R.dimen.position_medication_dose;
                title = R.string.medication_settings;
                fragment = new MedicationDoseFragment();
                break;

            case R.id.alarm_button:
                position = R.dimen.position_injection_start;
                title = R.string.welcome_title;
                fragment = new WelcomeFragment();
                break;

        }

        if( fragment != null )
            Fragments.getInstance().loadFragment((AppCompatActivity) getActivity(), fragment, position, title);
    }
}