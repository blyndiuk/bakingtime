package com.example.baking_time.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.baking_time.Constants;
import com.example.baking_time.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class RecipeStepFragment extends Fragment {

    private String videoUrl;
    private String description;
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private Uri uri;
    private long positionInPlayer = -1;
    boolean playWhenReady = true;

    public RecipeStepFragment() {
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_step, container, false);
        TextView textView = rootView.findViewById(R.id.tv_step_description);

        if (savedInstanceState != null) {
            videoUrl = savedInstanceState.getString(Constants.KEY_VIDEO_URL);
            description = savedInstanceState.getString(Constants.KEY_DESCRIPTION);
            positionInPlayer = savedInstanceState.getLong(Constants.PLAYBACK_POSITION);
            playWhenReady = savedInstanceState.getBoolean(Constants.PLAY_WHEN_READY);
        }

        if (description != null)
            textView.setText(description);
        else
            textView.setText(getString(R.string.no_description));


        if (videoUrl != null)
            uri = createUri(videoUrl);


        playerView = rootView.findViewById(R.id.pv_player_view);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            if (uri != null)
                initializePlayer(uri);
            else
                playerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
            if (uri != null)
                initializePlayer(uri);
            else
                playerView.setVisibility(View.GONE);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            if (player != null) {
                releasePlayer();
                player = null;
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            Log.i("FRAGMENT", "in onStop");
            if (player != null) {
                releasePlayer();
                player = null;
            }
        }
    }


    private Uri createUri(String urlString) {
        if (urlString.equals("")) {
            return null;
        }

        Uri createdUri;
        URL url;
        try {
            url = new URL(urlString);
            createdUri = Uri.parse(url.toURI().toString());
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
            return null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
        return createdUri;
    }


    private void initializePlayer(Uri mediaUri) {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(getActivity(),
                    new DefaultTrackSelector());
            playerView.setPlayer(player);

            if (getActivity() != null) {
                DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getActivity(),
                        Util.getUserAgent(getActivity(), "baking_time"));
                ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(mediaUri);

                player.prepare(mediaSource);
                if (positionInPlayer != -1) {
                    player.seekTo(positionInPlayer);
                }
                player.setPlayWhenReady(playWhenReady);
            }
        }
    }

    private void releasePlayer() {
        positionInPlayer = player.getCurrentPosition();
        playWhenReady = player.getPlayWhenReady();
        player.stop();
        player.release();
        player = null;
    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle currentState) {
        currentState.putString(Constants.KEY_DESCRIPTION, description);
        currentState.putString(Constants.KEY_VIDEO_URL, videoUrl);
        currentState.putLong(Constants.PLAYBACK_POSITION, positionInPlayer);
        currentState.putBoolean(Constants.PLAY_WHEN_READY, playWhenReady);
    }
}
