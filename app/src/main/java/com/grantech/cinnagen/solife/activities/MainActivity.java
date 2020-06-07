package com.grantech.cinnagen.solife.activities;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.utils.Fragments;

public class MainActivity extends BaseActivity
{
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();
        Fragments.getInstance().loadFragment(this, R.dimen.position_home_injection);
        ((BottomNavigationView) findViewById(R.id.navigation)).setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if( bundle != null && bundle.containsKey("position") )
            Fragments.getInstance().loadFragment(this, bundle.getInt("position"));
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item -> {
        switch (item.getItemId()) {
            case R.id.navi_0:   return Fragments.getInstance().loadFragment(this, R.dimen.position_home_injection);
            case R.id.navi_1:   return Fragments.getInstance().loadFragment(this, R.dimen.position_home_date);
            case R.id.navi_2:   return Fragments.getInstance().loadFragment(this, R.dimen.position_home_tips);
            case R.id.navi_4:   return Fragments.getInstance().loadFragment(this, R.dimen.position_home_more);
        }
        return false;
    };
}

