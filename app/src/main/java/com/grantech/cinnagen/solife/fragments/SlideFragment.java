package com.grantech.cinnagen.solife.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.grantech.cinnagen.solife.R;

/**
 * Created by ManJav on 1/23/2019.
 */

public class SlideFragment extends Fragment
{
    private final int position;
    public SlideFragment(int position)
    {
        this.position = position;
    }

    @Override
    @SuppressLint("InflateParams")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_slide, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        int id = getResources().getIdentifier("slide_" + (position+1), "mipmap", getContext().getPackageName());
        ((ImageView)view.findViewById(R.id.slide_image)).setImageResource(id);
    }
}