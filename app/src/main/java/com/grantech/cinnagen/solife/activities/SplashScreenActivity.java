package com.grantech.cinnagen.solife.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.utils.Fragments;
import com.grantech.cinnagen.solife.utils.Prefs;

import java.util.Locale;

public class SplashScreenActivity extends AppCompatActivity
{
    protected void onCreate(Bundle savedInstanceState)
    {
        // initialize localization
        Prefs.setInstance(getApplicationContext());
        Fragments.getInstance().locale = new Locale(Prefs.getInstance().getString(Prefs.KEY_LOC, "fa"));
        Log.i(Fragments.TAG, "loc "+ Fragments.getInstance().locale.getDisplayName());
        // set layout based on localization
        Configuration configuration = getResources().getConfiguration();
        configuration.setLocale(Fragments.getInstance().locale);
        configuration.setLayoutDirection(Fragments.getInstance().locale);
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler mWaitHandler = new Handler();
        mWaitHandler.postDelayed(() -> {
            //The following code will execute after the 5 seconds.
            try {
                //Go to next page i.e, start the next activity.
                Intent intent;
                if( Prefs.getInstance().contains(Prefs.KEY_NUM_RUN) )
                {
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                }
                else
                {
                    intent = new Intent(getApplicationContext(), FragmentsActivity.class);
                    Bundle b = new Bundle();
                    b.putInt("position", R.dimen.position_welcome);
                    intent.putExtras(b);
                }
                startActivity(intent);
                finish();
            } catch (Exception e) { e.printStackTrace(); }
        }, 1000);  // Give a second delay.
    }
}