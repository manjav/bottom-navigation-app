package com.grantech.cinnagen.solife.activities;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.utils.Fragments;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity
{
    protected void onCreate(Bundle savedInstanceState)
    {
        // set layout based on localization
        Configuration configuration = getResources().getConfiguration();
        configuration.setLocale(Fragments.getInstance().locale);
        configuration.setLayoutDirection(Fragments.getInstance().locale);
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());

        super.onCreate(savedInstanceState);
        assert getSupportActionBar() != null;

        // custom action-bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }

    public void clickEvent(View view)
    {
        if( view.getId() == R.id.toolbar_action_button )
        {
            Fragments.getInstance().clearStack(this);
            finish();
        }
    }
}
