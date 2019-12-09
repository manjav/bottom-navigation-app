package com.grantech.cinnagen.solife.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.utils.Fragments;
import com.grantech.cinnagen.solife.utils.Prefs;

import java.util.Objects;

/**
 * Created by ManJav on 1/23/2019.
 */

public class InjectionIconFragment extends InjectionBaseFragment
{
    ImageView iconDisplay;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        iconDisplay = view.findViewById(R.id.icon_image);
        iconDisplay.setImageResource(Objects.requireNonNull(getArguments()).getInt("icon"));
    }

    @Override
    public void onClick(View view)
    {
        if( getArguments() == null )
            return;

        switch( getArguments().getInt("icon") )
        {
            case R.drawable.ic_injection_start: Fragments.getInstance().loadFragment(activity, R.dimen.position_injection_slides);  return;
            case R.drawable.ic_injection_prep:  Fragments.getInstance().loadFragment(activity, R.dimen.position_injection_tips);  return;
            case R.drawable.ic_injection_tips:  Fragments.getInstance().loadFragment(activity, Prefs.getInstance().getInt(Prefs.KEY_PREV_X, 0) == 0 ? R.dimen.position_injection_steps : R.dimen.position_injection_prev);  return;
            case R.drawable.ic_injection_steps: Fragments.getInstance().loadFragment(activity, R.dimen.position_injection_body);
        }
    }
}