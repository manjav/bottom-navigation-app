package com.grantech.cinnagen.solife.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DocsFragment extends BaseFragment
{
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_docs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        WebView webView = view.findViewById(R.id.webView);
        webView.setWebViewClient(new MyWebViewClient());
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(false);
        settings.setDomStorageEnabled(false);
        settings.setBuiltInZoomControls(false);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
//        Log.i(Fragments.TAG, "Opening PDF=>: " + getArguments().getString("url"));
        assert getArguments() != null;
        webView.loadUrl(getArguments().getString("url"));
    }

    private class MyWebViewClient extends WebViewClient
    {
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            Log.i(Fragments.TAG, url + " " + url.startsWith("ftp://") + " " + url.contains("tel") + " " + url.contains("dim"));
            if( url.startsWith("ftp://") )
            {
                // call to method
                if( url.contains("tel") )
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + url.split("tel-")[1])));
                // internal page opening
                else if( url.contains("dim") )
                    Fragments.getInstance().loadFragment(activity, Fragments.getInstance().getDimId(getInt(url)));
                return true;
            }

            view.loadUrl(url);
            return true;
        }
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
