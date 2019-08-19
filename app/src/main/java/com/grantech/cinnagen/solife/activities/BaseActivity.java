package com.grantech.cinnagen.solife.activities;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.grantech.cinnagen.solife.R;

public class BaseActivity extends AppCompatActivity
{
    protected void onCreate(Bundle savedInstanceState)
    {
        // custom action-bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_medication_settings);

        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }
}
