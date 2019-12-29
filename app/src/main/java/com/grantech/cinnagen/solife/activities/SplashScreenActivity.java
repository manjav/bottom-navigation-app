package com.grantech.cinnagen.solife.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.utils.Prefs;

public class SplashScreenActivity extends AppCompatActivity
{
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // initialize localization
        Prefs.setInstance(getApplicationContext());

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