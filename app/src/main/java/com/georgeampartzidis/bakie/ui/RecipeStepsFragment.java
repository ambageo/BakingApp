package com.georgeampartzidis.bakie.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.georgeampartzidis.bakie.R;
import com.georgeampartzidis.bakie.model.Ingredient;
import com.georgeampartzidis.bakie.model.Recipe;
import com.georgeampartzidis.bakie.model.Step;

import java.util.ArrayList;


public class RecipeStepsFragment extends Fragment {

    private static final String LOG_TAG = RecipeStepsFragment.class.getSimpleName();
    private TextView recipesTextView;
    private Recipe mRecipe;

    /*private OnFragmentInteractionListener mListener;*/

    public RecipeStepsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle recipeBundle= this.getArguments();
        if(recipeBundle != null){
            mRecipe= recipeBundle.getParcelable(MainActivity.RECIPE_KEY);
        }

       /* Log.i(LOG_TAG, "Recipe passed: " + mRecipe.getName()
        + ", no. of steps: " + String.valueOf(mRecipe.getSteps().size())
        + ", no. of ingredients " + String.valueOf(mRecipe.getIngredients().size()));
        ArrayList<Ingredient> ingredientList= mRecipe.getIngredients();
        for (int i=0; i<ingredientList.size(); i++){

            Log.d(LOG_TAG, "" + ingredientList.get(i).getIngredient());
        }*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_recipe_steps, container, false);

        recipesTextView= view.findViewById(R.id.tv_recipe_contents);

        /*StringBuilder builder= new StringBuilder();
        for( Ingredient ingredient: mRecipe.getIngredients()){
            builder.append(ingredient.getIngredient()+ "\n");
        }
        recipesTextView.setText(builder.toString());*/


                return view;
    }

   /* // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

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
