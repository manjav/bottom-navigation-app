package com.grantech.cinnagen.solife.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.grantech.cinnagen.solife.R;

import java.util.Objects;

/**
 * Created by ManJav on 1/23/2019.
 */

public class SlideFragment extends Fragment
{
    private final int position;
    private TextView slideText;
    private ImageView slideImage;
    private int[] captions = new int[]{R.string.injection_slides_0, R.string.injection_slides_1, R.string.injection_slides_2, R.string.injection_slides_3, R.string.injection_slides_4};
    SlideFragment(int position)
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
        slideImage = view.findViewById(R.id.slide_image);
        slideText = view.findViewById(R.id.slide_text);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        int id = getResources().getIdentifier("slide_" + position, "mipmap", Objects.requireNonNull(getContext()).getPackageName());
        slideImage.setImageResource(id);
        slideText.setText(captions[captions.length - position - 1]);
    }
}