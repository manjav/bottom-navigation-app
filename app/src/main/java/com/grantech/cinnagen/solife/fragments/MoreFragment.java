package com.grantech.cinnagen.solife.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.utils.Fragments;

/**
 * Created by ManJav on 1/23/2018.
 */

public class MoreFragment extends BaseFragment
{
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_more, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        boolean isTall = ((float)dm.heightPixels / (float)dm.widthPixels) > 1.8;

        view.findViewById(R.id.about_call_icon).setOnClickListener(this);
        view.findViewById(R.id.about_call_button).setOnClickListener(this);

        if(isTall){
            view.findViewById(R.id.about_web_icon).setVisibility(View.VISIBLE);
            view.findViewById(R.id.about_web_button).setVisibility(View.VISIBLE);
            view.findViewById(R.id.about_web_message).setVisibility(View.VISIBLE);
            view.findViewById(R.id.about_web_icon).setOnClickListener(this);
            view.findViewById(R.id.about_web_button).setOnClickListener(this);
        }


        ListView listView = view.findViewById(R.id.about_list);
        listView.setOnItemClickListener((parent, view1, position, id) -> {

            switch (position) {
                case 0:
                    Bundle args = new Bundle();
                    args.putBoolean("s", true);
                    Fragments.getInstance().loadFragment(activity, R.dimen.position_medication_alarms, 0, args);
                    return;
                case 1:
                    Fragments.getInstance().loadFragment(activity, R.dimen.position_injection_settings);
                    return;
                case 2:
                    Fragments.getInstance().loadFragment(activity, R.dimen.position_misc_safety);
                    return;
                case 3:
                    Fragments.getInstance().loadFragment(activity, R.dimen.position_misc_faq);
                    return;
                case 4:
                    Fragments.getInstance().loadFragment(activity, R.dimen.position_misc_prescribing);
                    return;
                case 5:
                    Fragments.getInstance().loadFragment(activity, R.dimen.position_misc_terms);
                    return;
            }
        });
    }

    @Override
    public void onClick(View view)
    {
        super.onClick(view);
        if( view.getId() == R.id.about_call_icon || view.getId() == R.id.about_call_button )
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getContext().getString(R.string.about_tel_value))));
        else
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getContext().getString(R.string.about_web_value))));
    }
}