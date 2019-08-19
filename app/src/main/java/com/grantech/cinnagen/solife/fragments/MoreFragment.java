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

            int fragmentPosition = R.dimen.position_home_injection;
            switch (position)
            {
                case 0:
                    fragmentPosition = R.dimen.position_medication_alarms;
                    break;
                case 2:
                    fragmentPosition = R.dimen.position_misc_safety;
                    break;
                case 3:
                    fragmentPosition = R.dimen.position_misc_faq;
                    break;
                case 4:
                    fragmentPosition = R.dimen.position_misc_prescribing;
                    break;
                case 5:
                    fragmentPosition = R.dimen.position_misc_terms;
                    break;
            }

            Fragments.getInstance().loadFragment(activity, fragmentPosition);

        });
    }
}