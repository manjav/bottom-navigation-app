package com.grantech.cinnagen.solife.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.utils.Fragments;

public class WelcomeFragment extends BaseFragment
{
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        TextView termsText = view.findViewById(R.id.welcome_terms_text);
//        termsText.setPaintFlags(termsText.getPaintFlags() |  Paint.UNDERLINE_TEXT_FLAG);

        termsText.setOnClickListener(v -> Fragments.getInstance().loadFragment((AppCompatActivity) getActivity(), R.dimen.position_misc_terms));
        view.findViewById(R.id.welcome_accept_button).setOnClickListener(this);
    }
}
