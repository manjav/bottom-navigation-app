package com.grantech.cinnagen.solife.activities;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.utils.Fragments;

import java.util.Objects;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity
{
    private boolean doubleBack;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Fragments.getInstance().setLocale(getBaseContext());

        // custom action-bar
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
    }

    @Override
    protected void onResume() {
//        Fragments.getInstance().setLocale(getBaseContext());
        super.onResume();
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }

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
        boolean isMain = getClass().getName().equals("com.grantech.cinnagen.solife.activities.MainActivity");
        if( stackCount <= 0 )
        {
            Fragments.getInstance().clearStack(this);
            if( isMain ){
                if( doubleBack )
                    System.exit(0);
                Toast.makeText(getBaseContext(), R.string.app_exit_alert, Toast.LENGTH_SHORT).show();
                doubleBack = true;
                Handler mWaitHandler = new Handler();
                mWaitHandler.postDelayed(() -> {
                    doubleBack = false;
                }, 2000);  // Give a second delay.
                return;
            }
            finish();
            return;
        }
        if( stackCount == 1 && !isMain )
        {
            Fragments.getInstance().clearStack(this);
            finish();
            return;
        }
        Fragments.getInstance().oldPosition = Integer.parseInt(Objects.requireNonNull(fragmentManager.getBackStackEntryAt(stackCount - 1).getName()));
        if (stackCount >= 1)
        {
            Fragments.getInstance().updateActionbar(this, Fragments.getInstance().oldPosition, Fragments.getInstance().getTitle(Fragments.getInstance().getDimId(Fragments.getInstance().oldPosition)));
            fragmentManager.popBackStack();
            transaction.commit();
            return;
        }
        super.onBackPressed();
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