package com.grantech.cinnagen.solife.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.utils.Fragments;
import com.grantech.cinnagen.solife.utils.Prefs;

import java.util.Locale;

public class SplashScreenActivity extends AppCompatActivity
{
    protected void onCreate(Bundle savedInstanceState)
    {
        // default localization is farsi
        Fragments.getInstance().locale = new Locale("fa");
        Locale.setDefault(Fragments.getInstance().locale);
        Configuration config = new Configuration();
        config.locale = Fragments.getInstance().locale;
        Resources res = getApplicationContext().getResources();
        res.updateConfiguration(config, res.getDisplayMetrics());
        Prefs.setInstance(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler mWaitHandler = new Handler();
        mWaitHandler.postDelayed(() -> {
            //The following code will execute after the 5 seconds.
            try {
                //Go to next page i.e, start the next activity.
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            } catch (Exception e) { e.printStackTrace(); }
        }, 2000);  // Give a 2 seconds delay.
    }
}
