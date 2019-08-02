package com.grantech.cinnagen.cinnora;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.grantech.cinnagen.cinnora.fragments.CalendarFragment;
import com.grantech.cinnagen.cinnora.fragments.InjectionFragment;
import com.grantech.cinnagen.cinnora.fragments.MoreFragment;
import com.grantech.cinnagen.cinnora.fragments.TipsFragment;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
{
    private int startingPosition = -1;
    private Locale locale;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        locale = new Locale("fa");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        Resources res = getApplicationContext().getResources();
        res.updateConfiguration(config, res.getDisplayMetrics());

        super.onCreate(savedInstanceState);

        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/IRANSansMobile_Light.ttf");

        setContentView(R.layout.activity_main);
        loadFragment(new InjectionFragment(), 0);
        ((BottomNavigationView) findViewById(R.id.navigation)).setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private boolean loadFragment(Fragment fragment, int newPosition)
    {
        if( fragment == null )
            return false;

        //switching fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if( startingPosition > -1 )
        {
            if( startingPosition > newPosition )
                transaction.setCustomAnimations(locale.getLanguage().equals("fa") ? R.anim.slide_in_right : R.anim.slide_in_left, locale.getLanguage().equals("fa") ? R.anim.slide_out_left : R.anim.slide_out_right );
            else
                transaction.setCustomAnimations(locale.getLanguage().equals("fa") ? R.anim.slide_in_left : R.anim.slide_in_right, locale.getLanguage().equals("fa") ? R.anim.slide_out_right : R.anim.slide_out_left);
        }

        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
        startingPosition = newPosition;
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener()
    {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            Fragment fragment = null;
            int newPosition = 0;

            switch (item.getItemId()) {
                case R.id.navi_0:
                    newPosition = 0;
                    fragment = new InjectionFragment();
                    break;

                case R.id.navi_1:
                    newPosition = 1;
                    fragment = new CalendarFragment();
                    break;

                case R.id.navi_2:
                    newPosition = 2;
                    fragment = new TipsFragment();
                    break;

                case R.id.navi_3:
                    newPosition = 3;
                    fragment = new MoreFragment();
                    break;
            }

            return loadFragment(fragment, newPosition);
        }
    };

}
