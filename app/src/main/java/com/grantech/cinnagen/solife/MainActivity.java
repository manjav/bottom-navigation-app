package com.grantech.cinnagen.solife;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.grantech.cinnagen.solife.utils.Fragments;

import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity
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

        // custom action-bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_medication_settings);

        super.onCreate(savedInstanceState);

//         custom fonts
//        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O )
//            FontsOverride.setFont("normal", getResources().getFont(R.font.iransansmobile_light));

        setContentView(R.layout.activity_main);
        Fragments.getInstance().loadFragment(this, R.dimen.position_home_injection, R.string.home_injection);
        ((BottomNavigationView) findViewById(R.id.navigation)).setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item -> {

        switch (item.getItemId())
        {
            case R.id.navi_0:   return Fragments.getInstance().loadFragment(this, R.dimen.position_home_injection);
            case R.id.navi_1:   return Fragments.getInstance().loadFragment(this, R.dimen.position_home_date);
            case R.id.navi_2:   return Fragments.getInstance().loadFragment(this, R.dimen.position_home_tips);
            case R.id.navi_3:   return Fragments.getInstance().loadFragment(this, R.dimen.position_home_more);
        }

        return false;
    };

    @Override
    public void onBackPressed()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }*/

        int stackCount = fragmentManager.getBackStackEntryCount();
        if( stackCount > 0 )
        {
            String lastFragmentName = fragmentManager.getBackStackEntryAt(stackCount - 1).getName();
            Fragments.getInstance().oldPosition = Integer.parseInt(lastFragmentName);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(stackCount > 1);
            if( Fragments.getInstance().oldPosition == 0 ) // if home back
                getSupportActionBar().hide();
            fragmentManager.popBackStack();
            transaction.commit();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }
}

