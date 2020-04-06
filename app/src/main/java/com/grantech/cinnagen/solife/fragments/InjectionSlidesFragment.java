package com.grantech.cinnagen.solife.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.adapters.SlidesAdapter;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_injection_slides, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        indicator = view.findViewById(R.id.inject_slides_indicator);

        viewPager = view.findViewById(R.id.inject_slides_slider);
        viewPager.setOffscreenPageLimit(-1);
        viewPager.setAdapter(new SlidesAdapter(getFragmentManager(), 5));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            public void onPageSelected(int position) {
                indicator.setVisibility(position > 0 ? View.VISIBLE : View.INVISIBLE);
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();

        indicator.setScaleX(-1);
        indicator.setViewPager(viewPager);

        viewPager.setCurrentItem(4);
    }

    @Override
    public void onClick(View view)
    {
        Fragments.getInstance().loadFragment(activity, R.dimen.position_injection_timer);
    }

    @Override
    public void onPause()
    {
        Log.i(Fragments.TAG, "onPause");
        SlidesAdapter adapter = ((SlidesAdapter) viewPager.getAdapter());
        int last = adapter.getmLastPosition();
        if (last > -1) {
            SlideFragment lastPage = adapter.getFragments().get(last);
            if (lastPage != null)
                lastPage.onHidden();
        }
        super.onPause();
    }


}