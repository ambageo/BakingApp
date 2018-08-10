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

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static String TAG = RecipeStepsAdapter.class.getSimpleName();
    private static final int TYPE_INGREDIENTS_LIST = 0;
    private static final int TYPE_RECIPE_STEP = 1;
    Recipe mRecipe;


    public RecipeStepsAdapter(Recipe recipe) {
        this.mRecipe = recipe;
        Log.d(TAG, "Creating the Adapter...");
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_INGREDIENTS_LIST) {
            //return new IngredientsViewHolder(null);
            View view= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_ingredients, parent,false);
            Log.d(TAG, "Creating an Ingredients ViewHolder");
            return new IngredientsViewHolder(view);
        } else if (viewType == TYPE_RECIPE_STEP) {
            View view= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_recipe_step, parent, false);
            Log.d(TAG, "Creating a Steps ViewHolder");
            return new RecipeStepsViewHolder(view);
        }
        throw new RuntimeException("No type of holder matching type " + String.valueOf(viewType));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof IngredientsViewHolder) {
            ArrayList<Ingredient> ingredientList = mRecipe.getIngredients();
            StringBuilder builder = new StringBuilder();
            for (Ingredient ingredient : ingredientList) {
                builder.append(ingredient.getIngredient() + "\n");
            }
            // Cast holder to IngredientsViewHolder to set the text in tv_ingredients_list
            ((IngredientsViewHolder) holder).getIngredientsListTextView().setText(builder.toString());
        } else if (holder instanceof RecipeStepsViewHolder) {
            ArrayList<Step> stepsList = mRecipe.getSteps();
            if(position<stepsList.size() +1){
                Step step = stepsList.get(position -1);
                String shortDescription = step.getShortDescription();
                Log.d(TAG, "Adding step in position: " + String.valueOf(position) + ", " + shortDescription);
                ((RecipeStepsViewHolder) holder).recipeStepTextView.setText(shortDescription);
            }

        }

    }


    /**
     * getItemViewType is used to map the view type to the position in the adapter
     */
    @Override
    public int getItemViewType(int position) {
        switch(position){
            case 0:
                return TYPE_INGREDIENTS_LIST;
            default:
                return TYPE_RECIPE_STEP;
        }

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "Size of Items: " + mRecipe.getSteps().size());
        return mRecipe.getSteps().size() + 1;
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientTextView;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            ingredientTextView = itemView.findViewById(R.id.tv_ingredients_list);
        }
    }

    public class RecipeStepsViewHolder extends RecyclerView.ViewHolder {
        TextView recipeStepTextView;

        public RecipeStepsViewHolder(View itemView) {
            super(itemView);
            recipeStepTextView = itemView.findViewById(R.id.tv_recipe_step);
        }
    }
}
