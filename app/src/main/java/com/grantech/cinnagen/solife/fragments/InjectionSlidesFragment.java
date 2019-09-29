package com.grantech.cinnagen.solife.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.utils.Fragments;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by ManJav on 9/26/2019.
 */

public class InjectionSlidesFragment extends InjectionBaseFragment
{
    private ViewPager viewPager;
    private CircleIndicator indicator;

    @Override
    @SuppressLint("InflateParams")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_injection_slides, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        viewPager = view.findViewById(R.id.inject_slides_slider);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return new SlideFragment(position);
            }
            @Override
            public int getCount() {
                return 5;
            }
        });

        indicator = view.findViewById(R.id.inject_slides_indicator);
        indicator.setScaleX(-1);
        indicator.setViewPager(viewPager);

        viewPager.setCurrentItem(4);
    }

    @Override
    public void onClick(View view)
    {
        Fragments.getInstance().loadFragment(activity, R.dimen.position_injection_timer);
    }

}