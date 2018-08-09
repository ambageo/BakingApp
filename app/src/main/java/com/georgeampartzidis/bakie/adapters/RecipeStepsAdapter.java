package com.georgeampartzidis.bakie.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.RecipeViewHolder> {

    private final static String TAG= RecipeStepsAdapter.class.getSimpleName();
    Recipe mRecipe;


    public RecipeStepsAdapter(Recipe recipe) {
        this.mRecipe = recipe;
        Log.d(TAG, "Creating the Adapter...");
    }


    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "Inside onCreateViewHolder...");
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_recipe_step, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        ArrayList<Step>  stepsList= mRecipe.getSteps();
        Step step= stepsList.get(position);
        String shortDescription= step.getShortDescription();
        holder.recipeStepTextView.setText(shortDescription);
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "Size of ArrayList steps: "+ mRecipe.getSteps().size());
        return mRecipe.getSteps().size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView recipeStepTextView;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeStepTextView = itemView.findViewById(R.id.tv_recipe_step);
        }
    }
}
