package com.grantech.cinnagen.solife.activities;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.utils.Fragments;

import java.util.Objects;

public class InjectionActivity extends BaseActivity
{
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_injection);
        Fragments.getInstance().loadFragment(this, R.dimen.position_injection_start);
    }

    @Override
    public void onBackPressed()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        int stackCount = fragmentManager.getBackStackEntryCount();
        if( stackCount == 1 )
        {
            Fragments.getInstance().clearStack(this);
            finish();
            return;
        }
        if( stackCount > 1 )
        {
            String lastFragmentName = fragmentManager.getBackStackEntryAt(stackCount - 1).getName();
            Fragments.getInstance().oldPosition = Integer.parseInt(lastFragmentName);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            fragmentManager.popBackStack();
            transaction.commit();
            return;
        }
        super.onBackPressed();
    }
}
