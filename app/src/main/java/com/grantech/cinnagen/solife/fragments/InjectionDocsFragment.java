package com.grantech.cinnagen.solife.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.grantech.cinnagen.solife.R;
import com.grantech.cinnagen.solife.utils.Fragments;

/**
 * Created by ManJav on 1/23/2019.
 */

public class InjectionDocsFragment extends InjectionIconFragment
{
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_injection_docs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        submitButton.setText(R.string.app_next);

        WebView webView = view.findViewById(R.id.webView);
        webView.setWebViewClient(new MyWebViewClient());
        webView.setBackgroundColor(0x0000000);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(false);
        settings.setDomStorageEnabled(false);
        settings.setBuiltInZoomControls(false);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url)
            {
                iconDisplay.setVisibility(View.VISIBLE);
                iconDisplay.animate().alpha(1f).setDuration(600);

                webView.animate().alpha(1f).setDuration(500).setStartDelay(200);

                submitButton.setVisibility(View.VISIBLE);
                submitButton.animate().alpha(1f).setDuration(500).setStartDelay(300);
            }
        });

        assert getArguments() != null;
        webView.loadUrl(getArguments().getString("url"));
    }

    private class MyWebViewClient extends WebViewClient
    {
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            if (Fragments.getInstance().organizeURL(url, activity))
                return true;
            view.loadUrl(url);
            return true;
        }
    }
}