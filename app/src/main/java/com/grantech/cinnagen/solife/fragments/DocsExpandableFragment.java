package com.grantech.cinnagen.solife.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.utils.Fragments;

/**
 * Created by ManJav on 1/23/2019.
 */

public class DocsExpandableFragment extends BaseFragment
{
    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_docs_expandable, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        WebView webView = view.findViewById(R.id.webView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(false);
        settings.setDomStorageEnabled(false);
        settings.setBuiltInZoomControls(false);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        assert getArguments() != null;
        webView.loadUrl(getArguments().getString("url"));

        ((ImageView)view.findViewById(R.id.docs_expandable_image)).setImageResource(getArguments().getInt("icon"));

        view.findViewById(R.id.docs_expandable_finish).setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch( getArguments().getInt("icon") )
        {
            case R.drawable.ic_injection_start: Fragments.getInstance().loadFragment(activity, R.dimen.position_injection_prep);  return;
            case R.drawable.ic_injection_prep:  Fragments.getInstance().loadFragment(activity, R.dimen.position_injection_tips);  return;
            case R.drawable.ic_injection_tips:  Fragments.getInstance().loadFragment(activity, R.dimen.position_injection_steps);  return;
            case R.drawable.ic_injection_steps: activity.finish();Fragments.getInstance().clearStack(activity);          return;
        }
    }
}