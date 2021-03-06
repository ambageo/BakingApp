package com.georgeampartzidis.bakie.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.georgeampartzidis.bakie.R;
import com.georgeampartzidis.bakie.RecipeDetailsViewModel;
import com.georgeampartzidis.bakie.adapters.RecipeStepsAdapter;
import com.georgeampartzidis.bakie.model.Ingredient;
import com.georgeampartzidis.bakie.model.Recipe;
import com.georgeampartzidis.bakie.model.Step;

import java.util.ArrayList;


public class RecipeStepsFragment extends Fragment implements RecipeStepsAdapter.StepClickListener{

    private static final String LOG_TAG = RecipeStepsFragment.class.getSimpleName();
    private Recipe mRecipe;
    private String mRecipeName;
    private ArrayList<Step> mStepsList;
    private RecyclerView mRecyclerView;
    private Toast mToast;
    private RecipeDetailsViewModel model;
    /*private OnFragmentInteractionListener mListener;*/

    public RecipeStepsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model= ViewModelProviders.of(getActivity()).get(RecipeDetailsViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_steps, container, false);
        Bundle recipeBundle = this.getArguments();
        if (recipeBundle != null) {
            mRecipe = recipeBundle.getParcelable(MainActivity.RECIPE_KEY);
            mRecipeName= mRecipe.getName();
            mStepsList= mRecipe.getSteps();
        }

        mRecyclerView = view.findViewById(R.id.rv_recipe_steps);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new RecipeStepsAdapter(mRecipe, this));

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        /*  mListener = null;*/
    }

    @Override
    public void onStepClick(int clickedItemIndex) {
        int stepClickedIndex= clickedItemIndex -1;

        model.setRecipe(mRecipe);
        model.setmRecipeStep(stepClickedIndex);

        StepDetailsFragment detailsFragment= new StepDetailsFragment();
        FragmentManager fragmentManager= getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        if(isTablet(getActivity())){
            fragmentTransaction.replace(R.id.fragment_container2, detailsFragment, null)
                    .addToBackStack(null)
                    .commit();
        } else {
            fragmentTransaction.replace(R.id.recipe_container, detailsFragment, null)
                    .addToBackStack(null)
                    .commit();
        }


    }
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
