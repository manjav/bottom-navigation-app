package com.grantech.cinnagen.solife;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.grantech.cinnagen.solife.fragments.CalendarFragment;
import com.grantech.cinnagen.solife.fragments.InjectionFragment;
import com.grantech.cinnagen.solife.fragments.MoreFragment;
import com.grantech.cinnagen.solife.fragments.TipsFragment;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
{
    private Locale locale;
    private int startingPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // default localization is farsi
        locale = new Locale("fa");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        Resources res = getApplicationContext().getResources();
        res.updateConfiguration(config, res.getDisplayMetrics());

        // custom action-bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);

        super.onCreate(savedInstanceState);

//         custom fonts
//        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O )
//            FontsOverride.setFont("MONOSPACE", getResources().getFont(R.font.iransansmobile_bold));

        setContentView(R.layout.activity_main);
        loadFragment(new InjectionFragment(), 0, R.string.title_navi_0);
        ((BottomNavigationView) findViewById(R.id.navigation)).setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private boolean loadFragment(Fragment fragment, int newPosition, int title)
    {
        if( fragment == null )
            return false;

        // switching fragment
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

        // hide action-bar in main page
        if( newPosition != 0 )
        {
            getSupportActionBar().show();
            ((AppCompatTextView) findViewById(R.id.action_bar_text_view)).setText(title);
        }
        else
        {
            getSupportActionBar().hide();
        }

        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener()
    {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            Fragment fragment = null;
            int fragmentTitle = 0;
            int newPosition = 0;

            switch (item.getItemId())
            {
                case R.id.navi_0:
                    newPosition = 0;
                    fragmentTitle = R.string.title_navi_0;
                    fragment = new InjectionFragment();
                    break;

                case R.id.navi_1:
                    newPosition = 1;
                    fragmentTitle = R.string.title_navi_1;
                    fragment = new CalendarFragment();
                    break;

                case R.id.navi_2:
                    newPosition = 2;
                    fragmentTitle = R.string.title_navi_2;
                    fragment = new TipsFragment();
                    break;

                case R.id.navi_3:
                    newPosition = 3;
                    fragmentTitle = R.string.title_navi_3;
                    fragment = new MoreFragment();
                    break;
            }

            return loadFragment(fragment, newPosition, fragmentTitle);
        }
    };
}
