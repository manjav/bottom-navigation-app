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

            int title = 0;
            Bundle bundle = null;
            String url = null;
            int fragmentPosition = R.dimen.position_docs;
            switch (position)
            {
                case 0:
                    title = R.string.home_alarm;
                    fragmentPosition = R.dimen.position_medication_alarms;
                    break;
                case 2:
                    url = "file:///android_asset/docs/safety_info.html";
                    title = R.string.more_safety;
                    break;
                case 3:
                    url = "file:///android_asset/docs/faq.html";
                    title = R.string.more_faq;
                    break;
                case 4:
                    url = "file:///android_asset/docs/drug_info.html";
                    title = R.string.more_drug;
                    break;
                case 5:
                    url = "file:///android_asset/docs/terms_conditions.html";
                    title = R.string.more_terms;
                    break;
            }

            if( url  != null )
            {
                bundle = new Bundle();
                bundle.putString("url", url);
            }
            Fragments.getInstance().loadFragment(activity, fragmentPosition, title, bundle);

        });
    }
}