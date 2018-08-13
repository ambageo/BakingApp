package com.georgeampartzidis.bakie.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


public class StepDetailsFragment extends Fragment {
    private final static String TAG = StepDetailsFragment.class.getSimpleName();
    private TextView descriptionTextVIew;
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private BandwidthMeter bandwidthMeter;
    /* private OnFragmentInteractionListener mListener;*/

    public StepDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* RecipeDetailsViewModel model= ViewModelProviders
                .of(getActivity()).get(RecipeDetailsViewModel.class);
        Step step= model.getStep();
        Log.d(TAG, "Received clicked step: "+ step.getShortDescription());
        final String detailedDescription= step.getDetailedDescription();
        descriptionTextVIew.setText(detailedDescription);*/

        if (getArguments() != null) {

        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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



    /* // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    private void initializePlayer(Uri uri) {
        if (player == null) {
            TrackSelection.Factory factory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            DefaultTrackSelector trackSelector = new DefaultTrackSelector(factory);
            LoadControl loadControl= new DefaultLoadControl();
            player = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector,loadControl);
            playerView.setPlayer(player);
            MediaSource mediaSource= new ExtractorMediaSource(uri,
                    new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "BakingApp")),
                    new DefaultExtractorsFactory(), null, null);
            player.prepare(mediaSource);
            player.setPlayWhenReady(true);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (player!=null) {
            player.stop();
            player.release();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (player!=null) {
            player.stop();
            player.release();
            player=null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (player!=null) {
            player.stop();
            player.release();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player!=null) {
            player.stop();
            player.release();
        }
    }
}
