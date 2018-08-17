package com.georgeampartzidis.bakie.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.georgeampartzidis.bakie.R;
import com.georgeampartzidis.bakie.RecipeDetailsViewModel;
import com.georgeampartzidis.bakie.model.Recipe;
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

import java.util.ArrayList;


public class StepDetailsFragment extends Fragment {
    private static final String TAG = StepDetailsFragment.class.getSimpleName();
    private static final String PLAYER_STATE = "player-state";

    private Recipe mRecipe;
    private ArrayList<Step> mStepsList;
    private Step mStep;
    private int stepId;
    private String mDetailedDescription;

    private TextView descriptionTextVIew;
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private long playerPosition;
    private int mStepPosition;
    private boolean hasNoVideoUrl;
    private Button previousButton;
    private Button nextButton;


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
                mStepPosition= savedInstanceState.getInt("Step-Id");
                Log.d(TAG, "The saved instance of the video is :"
                        + String.valueOf(playerPosition) + " for step : "
                + String.valueOf(mStepPosition));
            } else {
                playerPosition = C.TIME_UNSET;
            }

        }
      //  if(isTablet(getContext())){
            Bundle recipeBundle = this.getArguments();
            if (recipeBundle != null) {
                mRecipe = recipeBundle.getParcelable(MainActivity.RECIPE_KEY);

                //stepId= 0;
            }
       // }
        else {
            RecipeDetailsViewModel model = ViewModelProviders
                    .of(getActivity()).get(RecipeDetailsViewModel.class);
            mRecipe = model.getRecipe();
            stepId = model.getmRecipeStep();
        }


        String recipe= mRecipe.getName();
        Log.d(TAG, "The recipe name is: " + recipe);
        mStepsList = mRecipe.getSteps();

        mStep = mStepsList.get(stepId);

        mDetailedDescription = mStepsList.get(stepId).getDetailedDescription();
        descriptionTextVIew.setText(mDetailedDescription);
        descriptionTextVIew.setMovementMethod(new ScrollingMovementMethod());
        if (mStep.getVideoUrl().isEmpty()) {
            hasNoVideoUrl = true;
        }
        initializePlayer(Uri.parse(mStep.getVideoUrl()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step_details, container, false);
        descriptionTextVIew = view.findViewById(R.id.tv_step_details);
        playerView = view.findViewById(R.id.playerView);
        previousButton = view.findViewById(R.id.bt_previous_step);
        nextButton = view.findViewById(R.id.bt_next_step);


        previousButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (stepId > 0) {
                    stepId--;
                    mStep = mStepsList.get(stepId);
                    String videoUrl = mStep.getVideoUrl();
                    playerPosition = C.TIME_UNSET;
                    mDetailedDescription = mStepsList.get(stepId).getDetailedDescription();
                    descriptionTextVIew.setText(mDetailedDescription);
                    checkStepVideoUrl(videoUrl);
                    initializePlayer(Uri.parse(videoUrl));
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stepId < mStepsList.size() - 1) {
                    stepId++;
                    mStep = mStepsList.get(stepId);
                    String videoUrl = mStep.getVideoUrl();
                    playerPosition = C.TIME_UNSET;
                    mDetailedDescription = mStepsList.get(stepId).getDetailedDescription();
                    descriptionTextVIew.setText(mDetailedDescription);
                    checkStepVideoUrl(videoUrl);
                    initializePlayer(Uri.parse(videoUrl));
                }
            }
        });
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

            if (hasNoVideoUrl) {
                Log.d(TAG, "No video available");
                playerView.setForeground(ContextCompat.getDrawable(getContext(),
                        R.drawable.no_video_available));
            } else {
                playerView.setForeground(null);
            }

            player.prepare(mediaSource);

            // If the video was playing before screen rotation, it will continue from where it was
            if (playerPosition != C.TIME_UNSET) {
                player.seekTo(playerPosition);
            }

            player.setPlayWhenReady(true);
        }
    }

    public void releasePlayer() {
        player.stop();
        player.release();
        player = null;
    }

    public void checkStepVideoUrl(String videoUrl) {
        if (videoUrl.isEmpty()) {
            hasNoVideoUrl = true;
        } else {
            hasNoVideoUrl = false;
        }
        if (player != null) {
            releasePlayer();
        } else {
            hasNoVideoUrl = false;
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
            // Get the video position to maintain after screen rotation
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
            outState.putInt("Step-Id", mStep.getId());
            Log.d(TAG, "Saving state of step no : "
                    + String.valueOf(mStep.getId())
            + " with playerPosition: " + String.valueOf(playerPosition));
        }
    }
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
