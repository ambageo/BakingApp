package com.georgeampartzidis.bakie.ui;

import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.georgeampartzidis.bakie.R;
import com.georgeampartzidis.bakie.model.Ingredient;
import com.georgeampartzidis.bakie.model.Recipe;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {

    public static final String LOG_TAG= RecipeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        if(savedInstanceState== null){
           // RecipeStepsFragment recipeSteps= new RecipeStepsFragment();
           Recipe recipe= getIntent().getExtras().getParcelable(MainActivity.RECIPE_KEY);
           int recipeNumber= recipe.getId();
           String recipeString= recipe.getName();
            ArrayList<Ingredient> ingredientsList= recipe.getIngredients();
            Log.d(LOG_TAG, "Recipe clicked is: " + recipeString);
            for(Ingredient ingredient: ingredientsList){
                Log.d(LOG_TAG, "Ingredient: " + ingredient.getIngredient() + "\n");
            }
           Bundle args= new Bundle();
           args.putParcelable(MainActivity.RECIPE_KEY, recipe);
            /*if (bundle.containsKey(MainActivity.RECIPE_KEY)){
                Recipe recipe= bundle.getParcelable(MainActivity.RECIPE_KEY);
                String recipeString= recipe.getName();
                int recipeNoOfIngredients= recipe.getIngredients().size();
                int recipeNoOfSteps= recipe.getSteps().size();

               *//* Toast.makeText(this, "Recipe: " + recipeString +
                        ", No. of ingredients: " + String.valueOf(recipeNoOfIngredients)
                + ", No. of steps: " + String.valueOf(recipeNoOfSteps), Toast.LENGTH_LONG).show();*//*
            }*/


           /* recipeSteps.setArguments(args);
            FragmentManager fragmentManager= getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.recipe_container, recipeSteps)
                    .commit();*/
        }

    }

}
