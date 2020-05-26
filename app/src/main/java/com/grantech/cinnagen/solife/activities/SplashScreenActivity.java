package com.grantech.cinnagen.solife.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.iid.FirebaseInstanceId;
import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.utils.AlarmReceiver;
import com.grantech.cinnagen.solife.utils.Alarms;
import com.grantech.cinnagen.solife.utils.Fragments;
import com.grantech.cinnagen.solife.utils.Prefs;

import java.util.Objects;

public class SplashScreenActivity extends AppCompatActivity
{
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // initialize localization
        Prefs.setInstance(getApplicationContext());
        Alarms.cancel(getApplicationContext(), AlarmReceiver.class, -1);

        setContentView(R.layout.activity_splash);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(Fragments.TAG, "getInstanceId failed", task.getException());
                        return;
                    }

                    // Get new Instance ID token
                    String token = task.getResult().getToken();

                    // Log and toast
                    Log.d(Fragments.TAG, " token ===> " + token);
                });

        Handler mWaitHandler = new Handler();
        mWaitHandler.postDelayed(() -> {
//            Fragments.getInstance().organizeURL("ftp://dim-27", this);
            Bundle bundle = getIntent().getExtras();
            if (bundle != null && bundle.containsKey("data")){
                Fragments.getInstance().organizeURL(Objects.requireNonNull(bundle.getString("data")), this);
                return;
            }

            //The following code will execute after the 5 seconds.
            try {
                //Go to next page i.e, start the next activity.
                Intent intent;
                if( Prefs.getInstance().contains(Prefs.KEY_NUM_RUN) )
                {
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                    if(Prefs.getInstance().getLong("alarm", 0) > System.currentTimeMillis()){
                        Bundle b = new Bundle();
                        b.putInt("position", R.dimen.position_injection_timer);
                        intent.putExtras(b);
                    }
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