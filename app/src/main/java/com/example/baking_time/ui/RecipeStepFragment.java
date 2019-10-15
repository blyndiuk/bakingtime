package com.example.baking_time.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        textView.setText(description);

        uri = createUri(videoUrl);
        if (uri == null)
            Log.i("FLAG", "uri is NULL");
        playerView = rootView.findViewById(R.id.pv_player_view);

        // if it's a tablet screen, make the playerView occupy a bigger proportion of the screen
        if (MasterListActivity.mTwoPane) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) playerView.getLayoutParams();
            params.weight = 2.0f;
            playerView.setLayoutParams(params);
        }
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (uri != null) {
            initializePlayer(uri);
        } else
            playerView.setVisibility(View.GONE);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (player != null) {
            playerView.setPlayer(null);
            player.release();
            player = null;
        }
    }

    private Uri createUri(String urlString) {
        if (urlString.equals(""))
            return null;

        Uri createdUri;
        URL url;
        try {
            url = new URL(urlString);
            Log.i("FLAG", "new url");
            createdUri = Uri.parse(url.toURI().toString());
            Log.i("FLAG", "uri");
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

            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getActivity(),
                    Util.getUserAgent(getActivity(), "baking_time"));
            ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(mediaUri);

            player.prepare(mediaSource);
            player.setPlayWhenReady(true);
        }
    }
}
