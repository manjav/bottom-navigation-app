package com.grantech.cinnagen.solife.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.utils.Fragments;

import java.util.Objects;

/**
 * Created by ManJav on 1/23/2019.
 */

public class InjectionBaseFragment extends BaseFragment
{
    ImageView iconDisplay;
    Button submitButton;

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_injection_docs, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        iconDisplay = view.findViewById(R.id.icon_image);
        iconDisplay.setImageResource(Objects.requireNonNull(getArguments()).getInt("icon"));

        submitButton = view.findViewById(R.id.submit_button);
        submitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if( getArguments() == null )
            return;

        switch( getArguments().getInt("icon") )
        {
            case R.drawable.ic_injection_start: Fragments.getInstance().loadFragment(activity, R.dimen.position_injection_prep);  return;
            case R.drawable.ic_injection_prep:  Fragments.getInstance().loadFragment(activity, R.dimen.position_injection_tips);  return;
            case R.drawable.ic_injection_tips:  Fragments.getInstance().loadFragment(activity, R.dimen.position_injection_steps);  return;
            case R.drawable.ic_injection_steps: activity.finish();Fragments.getInstance().clearStack(activity);
        }
    }
}