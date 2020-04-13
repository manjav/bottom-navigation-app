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
            case R.drawable.ic_injection_start: loadNext(R.dimen.position_injection_start); return;
            case R.drawable.ic_injection_prep:  loadNext(R.dimen.position_injection_prep);  return;
            case R.drawable.ic_injection_tips:  loadNext(R.dimen.position_injection_tips);  return;
            case R.drawable.ic_injection_steps: loadNext(R.dimen.position_injection_steps);
        }
    }

    private void loadNext(int pos) {
        Fragments.getInstance().loadFragment(activity, Fragments.getInstance().getNextPosition(pos));
    }
}