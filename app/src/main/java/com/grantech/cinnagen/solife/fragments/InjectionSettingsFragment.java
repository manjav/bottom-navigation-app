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
import com.grantech.cinnagen.solife.utils.Prefs;

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

        RecyclerView recyclerView = view.findViewById(R.id.inject_settings_list);
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

        recyclerView.setAdapter(new TileAdapter(getContext(), this, Prefs.getInstance().pages, captions));

        view.findViewById(R.id.inject_settings_submit).setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        Prefs.getInstance().setObject(Prefs.KEY_SETTINGS_PAGES, Prefs.getInstance().pages);
        activity.onBackPressed();
    }
}