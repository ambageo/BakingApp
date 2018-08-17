package com.georgeampartzidis.bakie;

import android.arch.lifecycle.ViewModel;
import android.util.Log;


import com.georgeampartzidis.bakie.model.Recipe;
import com.georgeampartzidis.bakie.model.Step;

public class RecipeDetailsViewModel extends ViewModel {
    private static final String TAG = RecipeDetailsViewModel.class.getSimpleName();
    private Recipe mRecipe;
    private int mRecipeStep;

    public Recipe getRecipe() {
        return mRecipe;
    }

    public void setRecipe(Recipe recipe) {
        this.mRecipe = recipe;
        Log.d(TAG, "Setting the recipe: " + mRecipe.getName());
    }


    public int getmRecipeStep() {
        return mRecipeStep;
    }

    public void setmRecipeStep(int recipeStep){
        this.mRecipeStep= recipeStep;
    }
}
