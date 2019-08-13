package com.grantech.cinnagen.solife.utils;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.grantech.cinnagen.solife.R;

import java.util.Locale;

public class Fragments
{
    public static String TAG = "solife";
    private static final Fragments ourInstance = new Fragments();

    public Locale locale;
    public int oldPosition = -1;
    static public Fragments getInstance() {
        return ourInstance;
    }

    private Fragments() {
    }

    public boolean loadFragment(AppCompatActivity activity, Fragment fragment, int newPosition, int title)
    {
        if( fragment == null || oldPosition == newPosition )
            return false;

        // switching fragment
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        if( oldPosition > -1 )
            transaction.setCustomAnimations(getAnimationIn(oldPosition > newPosition), getAnimationOut(oldPosition > newPosition), getAnimationIn(oldPosition < newPosition), getAnimationOut(oldPosition < newPosition));

        transaction.replace(R.id.fragment_container, fragment);
        if( newPosition > 3 )
            transaction.addToBackStack(oldPosition + "");
        transaction.commit();
        oldPosition = newPosition;

        // hide action-bar in main page
        ActionBar actionBar = activity.getSupportActionBar();
        if( newPosition != 0 )
        {
            actionBar.show();
            actionBar.setDisplayHomeAsUpEnabled(newPosition > 3);
//            actionBar.setDisplayShowHomeEnabled(newPosition > 3);
            ((AppCompatTextView) activity.findViewById(R.id.action_bar_text_view)).setText(title);
        }
        else
        {
            actionBar.hide();
        }

        return true;
    }

    public int getAnimationIn(boolean isBack)
    {
        if( isBack )
            return locale.getLanguage().equals("fa") ? R.anim.slide_in_right : R.anim.slide_in_left;
        return locale.getLanguage().equals("fa") ? R.anim.slide_in_left : R.anim.slide_in_right;
    }
    public int getAnimationOut(boolean isBack)
    {
        if( isBack )
            return locale.getLanguage().equals("fa") ? R.anim.slide_out_left : R.anim.slide_out_right;
        return locale.getLanguage().equals("fa") ? R.anim.slide_out_right : R.anim.slide_out_left;
    }
}
