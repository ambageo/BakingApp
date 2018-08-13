package com.georgeampartzidis.bakie.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.georgeampartzidis.bakie.R;
import com.georgeampartzidis.bakie.RecipeDetailsViewModel;
import com.georgeampartzidis.bakie.model.Step;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


public class StepDetailsFragment extends Fragment {
    private static final String TAG= StepDetailsFragment.class.getSimpleName();
    private static final String PLAYER_STATE = "player-state";
    private TextView descriptionTextVIew;
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private long playerPosition;

    public StepDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Here we check if the video was playing
            if (savedInstanceState.containsKey(PLAYER_STATE)) {
                playerPosition = savedInstanceState.getLong(PLAYER_STATE);
                Log.d(TAG, "We have a saved instance..." +
                String.valueOf(playerPosition));
            } else {
                playerPosition = C.TIME_UNSET;
            }

        }
        RecipeDetailsViewModel model = ViewModelProviders
                .of(getActivity()).get(RecipeDetailsViewModel.class);
        Step step = model.getStep();
        String detailedDescription = step.getDetailedDescription();
        descriptionTextVIew.setText(detailedDescription);
        initializePlayer(Uri.parse(step.getVideoUrl()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step_details, container, false);
        descriptionTextVIew = view.findViewById(R.id.tv_step_details);
        playerView = view.findViewById(R.id.playerView);

        return view;
    }


    private void initializePlayer(Uri uri) {
        if (player == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            playerView.setPlayer(player);
            MediaSource mediaSource = new ExtractorMediaSource.Factory(
                    new DefaultHttpDataSourceFactory("BakingApp")).createMediaSource(uri);


            if (playerPosition != C.TIME_UNSET) {
                player.seekTo(playerPosition);
                Log.d(TAG, "Player position is: " + String.valueOf(playerPosition));
            }
            player.prepare(mediaSource);
            player.setPlayWhenReady(true);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (player != null) {
            player.stop();
            player.release();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (player != null) {
            player.stop();
            player.release();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player != null) {
            playerPosition = player.getCurrentPosition();
            player.stop();
            player.release();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (player != null) {

            outState.putLong(PLAYER_STATE, playerPosition);
        }
    }
}
