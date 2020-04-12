package com.grantech.cinnagen.solife.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.adapters.TileAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ManJav on 9/26/2019.
 */

public class InjectionSettingsFragment extends BaseFragment
{
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_injection_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.buttons_recycler);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        List<Integer> captions = new ArrayList<>();
        captions.add(R.string.injection_start_title);
        captions.add(R.string.injection_slide_title);
        captions.add(R.string.injection_timer_title);
        captions.add(R.string.injection_preps_title);
        captions.add(R.string.injection_tips_title);
        captions.add(R.string.injection_steps_title);
        captions.add(R.string.injection_logs_title);

        List<Boolean> pages = new ArrayList<>();
        for(int i=0; i<7; i++)
            pages.add(true);
        recyclerView.setAdapter(new TileAdapter(getContext(), this, pages, captions));
    }

    @Override
    public void onClick(View view)
    {
//        Fragments.getInstance().loadFragment(activity, R.dimen.position_injection_steps);
    }
}