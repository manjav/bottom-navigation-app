package com.grantech.cinnagen.solife.utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.activities.FragmentsActivity;
import com.grantech.cinnagen.solife.activities.MainActivity;
import com.grantech.cinnagen.solife.fragments.BaseFragment;
import com.grantech.cinnagen.solife.fragments.DateFragment;
import com.grantech.cinnagen.solife.fragments.DocsFragment;
import com.grantech.cinnagen.solife.fragments.InboxFragment;
import com.grantech.cinnagen.solife.fragments.InjectionBodyFragment;
import com.grantech.cinnagen.solife.fragments.InjectionDocsFragment;
import com.grantech.cinnagen.solife.fragments.InjectionFragment;
import com.grantech.cinnagen.solife.fragments.InjectionLogFragment;
import com.grantech.cinnagen.solife.fragments.InjectionPrevFragment;
import com.grantech.cinnagen.solife.fragments.InjectionSettingsFragment;
import com.grantech.cinnagen.solife.fragments.InjectionSlidesFragment;
import com.grantech.cinnagen.solife.fragments.InjectionStepsFragment;
import com.grantech.cinnagen.solife.fragments.InjectionTimerFragment;
import com.grantech.cinnagen.solife.fragments.MedicationAlarmFragment;
import com.grantech.cinnagen.solife.fragments.MedicationDoseFragment;
import com.grantech.cinnagen.solife.fragments.MedicationTimeFragment;
import com.grantech.cinnagen.solife.fragments.MoreFragment;
import com.grantech.cinnagen.solife.fragments.TipsFragment;
import com.grantech.cinnagen.solife.fragments.WelcomeFragment;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fragments
{
    public static String TAG = "solife";
    private static final Fragments ourInstance = new Fragments();
    private final List<Integer> positions;

    public int oldPosition = -1;

    static public Fragments getInstance() {
        return ourInstance;
    }

    private Fragments() {
        positions = Arrays.asList(R.dimen.position_injection_start, R.dimen.position_injection_slides, R.dimen.position_injection_timer, R.dimen.position_injection_prep, R.dimen.position_injection_tips, R.dimen.position_injection_steps, R.dimen.position_injection_log);
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
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        if( oldPosition > newPosition )
        {
            int p = newPosition < 10 ? 0 : newPosition;
            for(int i = fragmentManager.getBackStackEntryCount()-1; i >=0; --i) {
                if( Integer.parseInt(Objects.requireNonNull(fragmentManager.getBackStackEntryAt(i).getName())) < p )
                    break;
                fragmentManager.popBackStackImmediate();
            }
        }

        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        if( oldPosition > -1 )
            transaction.setCustomAnimations(getAnimationIn(oldPosition > newPosition), getAnimationOut(oldPosition > newPosition), getAnimationIn(oldPosition < newPosition), getAnimationOut(oldPosition < newPosition));

        transaction.replace(R.id.fragment_container, fragment);
        if( newPosition > 9 || oldPosition == 0 )
            transaction.addToBackStack(oldPosition + "");
        transaction.commit();
        oldPosition = newPosition;

        updateActionbar(activity, newPosition, title);

        return true;
    }

    /**
     * hide action-bar in main page, change title and buttons visibility
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
            case R.dimen.position_home_inbox:    return R.string.home_inbox;
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
            case R.dimen.position_home_inbox:       return new InboxFragment();
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
            case R.dimen.position_injection_settings:      return new InjectionSettingsFragment();

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
            case 3: return R.dimen.position_home_inbox;
            case 4: return R.dimen.position_home_more;

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
            case 30: return R.dimen.position_injection_settings;

            case 31: return R.dimen.position_misc_safety;
            case 32: return R.dimen.position_misc_faq;
            case 33: return R.dimen.position_misc_prescribing;
            case 34: return R.dimen.position_misc_terms;
        }
        return 0;
    }

    public int getNextPosition(int position)
    {
        int index = positions.indexOf(position) + 1;
        while(true)
        {
            if( index > 5)
                return positions.get(6);
            if( Prefs.getInstance().pages.get(index))
                return positions.get(index);
            index ++;

        }
    }

    private int getAnimationIn(boolean isBack)
    {
        String language = Prefs.getInstance().getString(Prefs.KEY_LOC, "fa");
        if (isBack)
            return language.equals("fa") ? R.anim.slide_in_right : R.anim.slide_in_left;
        return language.equals("fa") ? R.anim.slide_in_left : R.anim.slide_in_right;
    }
    private int getAnimationOut(boolean isBack)
    {
        String language = Prefs.getInstance().getString(Prefs.KEY_LOC, "fa");
        if (isBack)
            return language.equals("fa") ? R.anim.slide_out_left : R.anim.slide_out_right;
        return language.equals("fa") ? R.anim.slide_out_right : R.anim.slide_out_left;
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

    public void setLocale(Context context) {
        Locale locale = new Locale(Prefs.getInstance().getString(Prefs.KEY_LOC, "fa"));
        Locale.setDefault(locale);

        // set layout based on localization
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);
        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
    }

    public boolean organizeURL(String url, AppCompatActivity activity)
    {
//      Log.i(Fragments.TAG, url + " " + url.startsWith("ftp://") + " " + url.contains("tel") + " " + url.contains("dim"));
        if (url.startsWith("ftp://")) {
            // call to method
            if (url.contains("tel"))
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + url.split("tel-")[1])));
                // internal page opening
            else if (url.contains("dim")){
                int dim = getInt(url);
                Class<?> cls = getActivityByDim(dim);
                if (!cls.getName().equals(activity.getClass().getName())){
                    Log.i(Fragments.TAG, dim + " " + cls.getName());

                    Intent intent = new Intent(activity, cls);
                    Bundle b = new Bundle();
                    b.putInt("position", getDimId(dim));
                    intent.putExtras(b);
                    activity.startActivity(intent);
                    activity.finish();
                    return true;
                }
                Fragments.getInstance().loadFragment(activity, getDimId(dim));
            }
            return true;
        }
        return false;
    }

    private Class<?> getActivityByDim(int dim) {
        if( dim >= 20 && dim < 30 )
            return FragmentsActivity.class;
        return MainActivity.class;
    }

    private int getInt(String url)
    {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(url);
        while(m.find())
            return Integer.parseInt(m.group());
        return 0;
    }
}