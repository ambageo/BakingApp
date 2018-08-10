package com.georgeampartzidis.bakie.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.georgeampartzidis.bakie.R;
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
    /*private OnFragmentInteractionListener mListener;*/

    public RecipeStepsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        /*  mListener = null;*/
    }

    @Override
    public void onStepClick(int clickedItemIndex) {
        int stepClickedIndex= clickedItemIndex -1;
        String stepClicked= mStepsList.get(stepClickedIndex).getShortDescription();
        String toastMessage= "Recipe : "
                + mRecipeName + ", step clicked: " + stepClicked;
        mToast= Toast.makeText(getActivity(), toastMessage, Toast.LENGTH_SHORT);
        mToast.show();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
   /* public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
