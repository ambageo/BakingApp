package com.georgeampartzidis.bakie.ui;

import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.georgeampartzidis.bakie.R;
import com.georgeampartzidis.bakie.model.Ingredient;
import com.georgeampartzidis.bakie.model.Recipe;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {

    public static final String LOG_TAG= RecipeActivity.class.getSimpleName();
    static final String STACK_RECIPE_DETAILS= "stack-recipe-details";
    static final String STACK_RECIPE_STEPS= "stack-recipe-steps";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        if(savedInstanceState== null){

           Recipe recipe= getIntent().getExtras().getParcelable(MainActivity.RECIPE_KEY);
           String recipeString= recipe.getName();

            Toolbar toolbar= findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(recipeString);

            Bundle recipeBundle= new Bundle();
            recipeBundle.putParcelable(MainActivity.RECIPE_KEY, recipe);

            RecipeStepsFragment recipeSteps= new RecipeStepsFragment();
            recipeSteps.setArguments(recipeBundle);
            FragmentManager fragmentManager= getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container1, recipeSteps)
                    .commit();

            // Using the tag attribute to determine whether we are in tablet landscape mode
            if(findViewById(R.id.recipe_container).getTag()!=null
                    && findViewById(R.id.recipe_container).getTag().equals("tablet-landscape")){
                Log.d(LOG_TAG, "tablet-mode");
                StepDetailsFragment stepDetailsFragment= new StepDetailsFragment();
                stepDetailsFragment.setArguments(recipeBundle);
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_container2, stepDetailsFragment)
                        .commit();
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
