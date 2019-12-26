package com.grantech.cinnagen.solife.activities;

import android.os.Bundle;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.utils.Fragments;

public class FragmentsActivity extends BaseActivity
{
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_injection);

        Bundle b = getIntent().getExtras();
        int position = -1; // or other values
        if( b != null )
            position= b.getInt("position");
        Fragments.getInstance().loadFragment(this, position);
    }
}