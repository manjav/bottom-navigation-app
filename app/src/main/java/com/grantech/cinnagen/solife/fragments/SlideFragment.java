package com.grantech.cinnagen.solife.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.grantech.cinnagen.solife.R;

import java.util.Objects;

/**
 * Created by ManJav on 1/23/2019.
 */

public class SlideFragment extends Fragment
{
    private final int position;
    private TextView slideText;
    private ImageView slideImage;
    private PlayerView playerView;
    private SimpleExoPlayer exoPlayer;
    private int[] captions = new int[]{R.string.injection_slides_0, R.string.injection_slides_1, R.string.injection_slides_2, R.string.injection_slides_3, R.string.injection_slides_4};

    public SlideFragment(int position)
    {
        this.position = position;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_slide, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        if (position < 1){
            playerView = view.findViewById(R.id.video_view);
            playerView.setVisibility(View.VISIBLE);
            exoPlayer = new SimpleExoPlayer.Builder(Objects.requireNonNull(getContext())).build();
            playerView.setPlayer(exoPlayer);

            // Produces DataSource instances through which media data is loaded.
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(Objects.requireNonNull(getContext()), Util.getUserAgent(getContext(), "yourApplicationName"));
            // This is the MediaSource representing the media to be played.
            MediaSource videoSource =new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse("http://smartadmin.orchidpharmed.ir/Cinnora-360p.mp4"));
            // Prepare the player with the source.
            exoPlayer.prepare(videoSource);
            return;
        }

        int id = getResources().getIdentifier("slide_" + position, "mipmap", Objects.requireNonNull(getContext()).getPackageName());
        slideImage = view.findViewById(R.id.slide_image);
        slideImage.setVisibility(View.VISIBLE);
        slideImage.setImageResource(id);

        slideText = view.findViewById(R.id.slide_text);
        slideText.setVisibility(View.VISIBLE);
        slideText.setText(captions[captions.length - position - 1]);
    }

    public void onHidden() {
        if (exoPlayer != null)
            exoPlayer.stop();
    }
}