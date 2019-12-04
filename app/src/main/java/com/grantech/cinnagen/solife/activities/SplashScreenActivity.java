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
        Prefs.setInstance(getApplicationContext());

        // change localization
        Fragments.getInstance().locale = new Locale(Prefs.getInstance().getString(Prefs.KEY_LOC, "fa"));
        Locale.setDefault(Fragments.getInstance().locale);
        Configuration config = new Configuration();
        config.locale = Fragments.getInstance().locale;
        Resources res = getApplicationContext().getResources();
        res.updateConfiguration(config, res.getDisplayMetrics());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler mWaitHandler = new Handler();
        mWaitHandler.postDelayed(() -> {
            //The following code will execute after the 5 seconds.
            try {
                //Go to next page i.e, start the next activity.

                Intent intent = null;
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
