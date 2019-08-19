package com.grantech.cinnagen.solife.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_more, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = view.findViewById(R.id.about_list);
        listView.setOnItemClickListener((parent, view1, position, id) -> {

            switch (position) {
                case 0:
                    Fragments.getInstance().loadFragment(activity, R.dimen.position_medication_alarms);
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
}