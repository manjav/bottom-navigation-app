package com.grantech.cinnagen.solife.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.utils.Fragments;

/**
 * Created by ManJav on 9/26/2019.
 */

public class InjectionTimerFragment extends InjectionBaseFragment
{
    static private int MIN = 900;
    static private int MAX = 1800;
    static private int STEP = 300;
    private int delay = MAX;
    private TextView timerText;
    private TextView remainingText;
    private Button plusButton;
    private Button minesButton;
    private Button toggleButton;
    private Button restoreButton;
    private CountDownTimer timer;
    private boolean inProgress;

    @Override
    @SuppressLint("InflateParams")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_injection_timer, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        timerText = view.findViewById(R.id.inject_timer_timer);
        remainingText = view.findViewById(R.id.inject_timer_remainig);

        plusButton = view.findViewById(R.id.inject_timer_plus);
        plusButton.setOnClickListener(this);

        minesButton = view.findViewById(R.id.inject_timer_mines);
        minesButton.setOnClickListener(this);

        toggleButton = view.findViewById(R.id.inject_timer_toggle);
        toggleButton.setOnClickListener(this);

        restoreButton = view.findViewById(R.id.inject_timer_restore);
        restoreButton.setOnClickListener(this);

        traceTime();
    }

    @SuppressLint("SetTextI18n")
    private void traceTime() {
        int min  = (int) Math.floor(delay / 60);
        int sec = delay % 60;
        timerText.setText( (min < 10 ? "0"+min : min) + ":" + (sec < 10 ? "0"+sec : sec));
    }

    @Override
    public void onClick(View view)
    {
        switch( view.getId() ) {
            case R.id.submit_button: Fragments.getInstance().loadFragment(activity, R.dimen.position_injection_prep); break;
            case R.id.inject_timer_plus: changeDelay(STEP); break;
            case R.id.inject_timer_mines: changeDelay(-STEP); break;
            case R.id.inject_timer_toggle: toggleCountdown(); break;
            case R.id.inject_timer_restore: resetCountdown(); break;
        }
    }

    private void changeDelay(int time) {
        delay = Math.min(Math.max(MIN, delay + time), MAX);
        plusButton.setBackgroundResource( delay >= MAX ? R.drawable.rect_round_button_gray : R.drawable.rect_round_button_white );
        minesButton.setBackgroundResource( delay <= MIN ? R.drawable.rect_round_button_gray : R.drawable.rect_round_button_white );
        traceTime();
    }

    private void toggleCountdown() {
        plusButton.setVisibility(View.GONE);
        minesButton.setVisibility(View.GONE);
        if( inProgress )
        {
            timer.cancel();
            toggleButton.setText(R.string.app_start);
            inProgress = false;
            remainingText.setVisibility(View.INVISIBLE);
            return;
        }
        if( timer == null )
        timer = new CountDownTimer(delay * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                --delay;
                traceTime();
            }

            @Override
            public void onFinish() {
            }
        };

        inProgress = true;
        timer.start();
        toggleButton.setText(R.string.app_pause);
        remainingText.setVisibility(View.VISIBLE);
        restoreButton.setBackgroundResource(R.drawable.rect_round_button_white);
    }

    private void resetCountdown() {
        delay = MAX;
        inProgress = false;
        timer.cancel();
        timer = null;

        plusButton.setVisibility(View.VISIBLE);
        plusButton.setBackgroundResource(R.drawable.rect_round_button_gray);
        minesButton.setVisibility(View.VISIBLE);
        minesButton.setBackgroundResource(R.drawable.rect_round_button_white);
        toggleButton.setText(R.string.app_start);
        restoreButton.setBackgroundResource(R.drawable.rect_round_button_gray);
        remainingText.setVisibility(View.INVISIBLE);

        traceTime();
    }
}