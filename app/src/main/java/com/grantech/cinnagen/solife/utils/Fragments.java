package com.grantech.cinnagen.solife.utils;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.fragments.BaseFragment;
import com.grantech.cinnagen.solife.fragments.DateFragment;
import com.grantech.cinnagen.solife.fragments.DocsFragment;
import com.grantech.cinnagen.solife.fragments.InjectionBodyFragment;
import com.grantech.cinnagen.solife.fragments.InjectionDocsFragment;
import com.grantech.cinnagen.solife.fragments.InjectionFragment;
import com.grantech.cinnagen.solife.fragments.InjectionLogFragment;
import com.grantech.cinnagen.solife.fragments.InjectionPrevFragment;
import com.grantech.cinnagen.solife.fragments.InjectionSlidesFragment;
import com.grantech.cinnagen.solife.fragments.InjectionStepsFragment;
import com.grantech.cinnagen.solife.fragments.InjectionTimerFragment;
import com.grantech.cinnagen.solife.fragments.MedicationAlarmFragment;
import com.grantech.cinnagen.solife.fragments.MedicationDoseFragment;
import com.grantech.cinnagen.solife.fragments.MedicationTimeFragment;
import com.grantech.cinnagen.solife.fragments.MoreFragment;
import com.grantech.cinnagen.solife.fragments.TipsFragment;
import com.grantech.cinnagen.solife.fragments.WelcomeFragment;

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


    public boolean loadFragment(AppCompatActivity activity, int position) {
        return this.loadFragment(activity, position, 0, null);
    }
    public boolean loadFragment(AppCompatActivity activity, int position, int title) {
        return this.loadFragment(activity, position, title, null);
    }
    public boolean loadFragment(AppCompatActivity activity, int position, int title, Bundle arguments)
    {
        BaseFragment fragment = this.getFragment(position);
        if( fragment == null )
            return false;
        int newPosition = (int) activity.getResources().getDimension(position);
        Log.i(TAG, "old " + oldPosition + " new " +  newPosition );//+ " d " +  (int) activity.getResources().getDimension(newPosition) );
        if( oldPosition == newPosition )
            return false;

        if( title == 0 )
            title = this.getTitle(position);

        if( arguments == null )
            arguments = this.getArguments(position);
        fragment.setArguments(arguments);

        // switching fragment
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        if( oldPosition > -1 )
            transaction.setCustomAnimations(getAnimationIn(oldPosition > newPosition), getAnimationOut(oldPosition > newPosition), getAnimationIn(oldPosition < newPosition), getAnimationOut(oldPosition < newPosition));

        transaction.replace(R.id.fragment_container, fragment);
        if( newPosition > 3 )
            transaction.addToBackStack(oldPosition + "");
        transaction.commit();
        oldPosition = newPosition;

        updateActionbar(activity, newPosition, title);

        return true;
    }

    /**
     * hide action-bar in main page, change title and buttons visibility
     * @param activity
     * @param newPosition
     * @param title
     */
    @SuppressLint("RestrictedApi")
    public void updateActionbar(AppCompatActivity activity, int newPosition, int title) {

        ActionBar actionBar = activity.getSupportActionBar();
        if( actionBar == null )
            return;
        actionBar.setShowHideAnimationEnabled(false);
        activity.findViewById(R.id.toolbar_action_button).setVisibility(newPosition > 20 && newPosition < 30 ? View.VISIBLE : View.INVISIBLE);
        if( newPosition != 0 ) {
            actionBar.show();
            actionBar.setDisplayHomeAsUpEnabled(newPosition % 10 > 0 );
            ((AppCompatTextView) activity.findViewById(R.id.toolbar_title)).setText(title);
        } else {
            actionBar.hide();
        }
    }

    public int getTitle(int position)
    {
        if( position >= 20 && position < 30 )
            return R.string.home_injection;
        switch( position )
        {
            case R.dimen.position_home_injection:    return R.string.home_injection;
            case R.dimen.position_home_date:    return R.string.home_calendar;
            case R.dimen.position_home_tips:    return R.string.home_tips;
            case R.dimen.position_home_more:    return R.string.home_more;

            case R.dimen.position_welcome:   return R.string.welcome_action;
            case R.dimen.position_medication_dose:
            case R.dimen.position_medication_time:   return R.string.home_medication;
            case R.dimen.position_medication_alarms:   return R.string.home_alarm;

            case R.dimen.position_misc_safety:   return R.string.more_safety;
            case R.dimen.position_misc_faq:   return R.string.more_faq;
            case R.dimen.position_misc_prescribing:   return R.string.more_prescribing;
            case R.dimen.position_misc_terms:   return R.string.more_terms;
        }
        return R.string.app_name;
    }

    private Bundle getArguments(int position)
    {
        Bundle bundle = new Bundle();
        switch( position )
        {
            case R.dimen.position_injection_start:  bundle.putString("url", "file:///android_asset/docs/injection_start.html");    bundle.putInt("icon", R.drawable.ic_injection_start);   break;
            case R.dimen.position_injection_prep:  bundle.putString("url", "file:///android_asset/docs/injection_prep.html");    bundle.putInt("icon", R.drawable.ic_injection_prep);   break;
            case R.dimen.position_injection_tips:  bundle.putString("url", "file:///android_asset/docs/injection_tips.html");    bundle.putInt("icon", R.drawable.ic_injection_tips);   break;
            case R.dimen.position_injection_steps:  bundle.putString("url", "file:///android_asset/docs/injection_steps.html");    bundle.putInt("icon", R.drawable.ic_injection_steps);   break;

            case R.dimen.position_misc_safety:  bundle.putString("url", "file:///android_asset/docs/safety_info.html");        break;
            case R.dimen.position_misc_faq:  bundle.putString("url", "file:///android_asset/docs/faq.html");                break;
            case R.dimen.position_misc_prescribing:  bundle.putString("url", "file:///android_asset/docs/prescribing_info.html");   break;
            case R.dimen.position_misc_terms:  bundle.putString("url", "file:///android_asset/docs/terms_conditions.html");   break;
        }
        return bundle;
    }

    private BaseFragment getFragment(int position)
    {
        switch( position )
        {
            case R.dimen.position_home_injection:       return new InjectionFragment();
            case R.dimen.position_home_date:       return new DateFragment();
            case R.dimen.position_home_tips:       return new TipsFragment();
            case R.dimen.position_home_more:       return new MoreFragment();

            case R.dimen.position_welcome:      return new WelcomeFragment();
            case R.dimen.position_medication_dose:      return new MedicationDoseFragment();
            case R.dimen.position_medication_time:      return new MedicationTimeFragment();
            case R.dimen.position_medication_alarms:      return new MedicationAlarmFragment();

            case R.dimen.position_injection_slides:      return new InjectionSlidesFragment();
            case R.dimen.position_injection_timer:      return new InjectionTimerFragment();
            case R.dimen.position_injection_start:
            case R.dimen.position_injection_prep:
            case R.dimen.position_injection_tips:      return new InjectionDocsFragment();
            case R.dimen.position_injection_prev:      return new InjectionPrevFragment();
            case R.dimen.position_injection_steps:      return new InjectionStepsFragment();
            case R.dimen.position_injection_body:      return new InjectionBodyFragment();
            case R.dimen.position_injection_log:      return new InjectionLogFragment();

            case R.dimen.position_misc_faq:
            case R.dimen.position_misc_terms:
            case R.dimen.position_misc_safety:
            case R.dimen.position_misc_prescribing:       return new DocsFragment();
        }
        return null;
    }

    public int getDimId(int position)
    {
        switch( position )
        {
            case 0: return R.dimen.position_home_injection;
            case 1: return R.dimen.position_home_date;
            case 2: return R.dimen.position_home_tips;
            case 3: return R.dimen.position_home_more;

            case 10: return R.dimen.position_welcome;
            case 11: return R.dimen.position_medication_dose;
            case 12: return R.dimen.position_medication_time;
            case 13: return R.dimen.position_medication_alarms;

            case 20: return R.dimen.position_injection_start;
            case 21: return R.dimen.position_injection_slides;
            case 22: return R.dimen.position_injection_timer;
            case 23: return R.dimen.position_injection_prep;
            case 24: return R.dimen.position_injection_tips;
            case 25: return R.dimen.position_injection_prev;
            case 26: return R.dimen.position_injection_steps;
            case 27: return R.dimen.position_injection_body;
            case 28: return R.dimen.position_injection_log;

            case 31: return R.dimen.position_misc_safety;
            case 32: return R.dimen.position_misc_faq;
            case 33: return R.dimen.position_misc_prescribing;
            case 34: return R.dimen.position_misc_terms;
        }
        return 0;
    }


    private int getAnimationIn(boolean isBack)
    {
        if( isBack )
            return locale.getLanguage().equals("fa") ? R.anim.slide_in_right : R.anim.slide_in_left;
        return locale.getLanguage().equals("fa") ? R.anim.slide_in_left : R.anim.slide_in_right;
    }
    private int getAnimationOut(boolean isBack)
    {
        if( isBack )
            return locale.getLanguage().equals("fa") ? R.anim.slide_out_left : R.anim.slide_out_right;
        return locale.getLanguage().equals("fa") ? R.anim.slide_out_right : R.anim.slide_out_left;
    }

    public void clearStack(AppCompatActivity activity)
    {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        oldPosition = (int) activity.getResources().getDimension(R.dimen.position_home_injection);
        //noinspection ConstantConditions
        activity.getSupportActionBar().hide();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        transaction.commit();
    }
}