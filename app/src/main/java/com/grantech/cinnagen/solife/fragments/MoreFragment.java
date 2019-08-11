package com.grantech.cinnagen.solife.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.utils.Fragments;

/**
 * Created by ManJav on 1/23/2018.
 */

public class MoreFragment extends Fragment
{
    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_more_layout, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = view.findViewById(R.id.about_list);
        listView.setOnItemClickListener((parent, view1, position, id) -> {

            String url = null;
            int title = 0;
            Fragment fragment = null;
            switch (position)
            {
                case 2:
                    url = "file:///android_asset/docs/safety_info.html";
                    title = R.string.title_navi_42;
                    fragment  = new DocsFragment();
                    break;
                case 3:
                    url = "file:///android_asset/docs/faq.html";
                    title = R.string.title_navi_43;
                    fragment  = new DocsFragment();
                    break;
                case 4:
                    url = "file:///android_asset/docs/drug_info.html";
                    title = R.string.title_navi_44;
                    fragment  = new DocsFragment();
                    break;
                case 5:
                    url = "file:///android_asset/docs/terms_conditions.html";
                    title = R.string.title_navi_45;
                    fragment  = new DocsFragment();
                    break;
            }

            if( fragment  != null )
            {

                Bundle bundle = new Bundle();
                bundle.putString("url", url);
                fragment.setArguments(bundle);

                Fragments.getInstance().loadFragment((AppCompatActivity) getActivity(), fragment, R.dimen.fragment_docs, title);
            }

        });
    }
}