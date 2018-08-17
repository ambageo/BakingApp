package com.georgeampartzidis.bakie.ui;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.georgeampartzidis.bakie.R;
import com.georgeampartzidis.bakie.RecipeDetailsViewModel;
import com.georgeampartzidis.bakie.adapters.RecipeAdapter;
import com.georgeampartzidis.bakie.model.Recipe;
import com.georgeampartzidis.bakie.model.Ingredient;
import com.georgeampartzidis.bakie.model.Step;
import com.georgeampartzidis.bakie.utils.Preferences;
import com.georgeampartzidis.bakie.widget.RecipeWidgetProvider;
import com.georgeampartzidis.bakie.widget.RecipeWidgetService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeClickListener{

    private final static String LOG_TAG = MainActivity.class.getSimpleName();
    public static final String RECIPE_KEY= "recipe-key";
    public static final String URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    private RecyclerView mRecyclerView;
    private RecipeAdapter mRecipeAdapter;
    private ArrayList<Recipe> mRecipeArrayList;
    private RequestQueue requestQueue;
    private RecipeDetailsViewModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = findViewById(R.id.rv_recipes);
        mRecipeArrayList = new ArrayList<>();
        model= ViewModelProviders.of(this).get(RecipeDetailsViewModel.class);

        sendRecipeRequest();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecipeAdapter = new RecipeAdapter(this, mRecipeArrayList, this);
        mRecyclerView.setAdapter(mRecipeAdapter);


    }

    private void sendRecipeRequest() {

        JsonArrayRequest request = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++)
                            try {
                                JSONObject jsonRecipe = response.getJSONObject(i);
                                JSONArray ingredientsJsonArray= jsonRecipe.getJSONArray("ingredients");
                                JSONArray stepsJsonArray= jsonRecipe.getJSONArray("steps");

                                ArrayList<Ingredient> ingredientsArrayList= new ArrayList<>();
                                for(int j=0; j<ingredientsJsonArray.length(); j++){
                                    JSONObject jsonIngredient= ingredientsJsonArray.getJSONObject(j);
                                    long quantity= jsonIngredient.optInt("quantity");
                                    String measure= jsonIngredient.optString("measure");
                                    String ingredientString= jsonIngredient.optString("ingredient");

                                    Ingredient ingredient= new Ingredient();
                                    ingredient.setQuantity(quantity);
                                    ingredient.setMeasure(measure);
                                    ingredient.setIngredient(ingredientString);
                                    ingredientsArrayList.add(ingredient);

                                }

                                ArrayList<Step> stepsArrayList= new ArrayList<>();
                                for(int k=0; k<stepsJsonArray.length(); k++){
                                    JSONObject jsonStep= stepsJsonArray.getJSONObject(k);
                                    int id= jsonStep.getInt("id");
                                    String shortDescription= jsonStep.optString("shortDescription");
                                    String detailedDescription= jsonStep.optString("description");
                                    String videoUrl= jsonStep.optString("videoURL");
                                    String thumbnailUrl= jsonStep.optString("thumbnailURL");

                                    Step step= new Step(jsonStep);
                                    step.setId(id);
                                    step.setShortDescription(shortDescription);
                                    step.setDetailedDescription(detailedDescription);
                                    step.setVideoUrl(videoUrl);
                                    step.setThumbnailUrl(thumbnailUrl);
                                    stepsArrayList.add(step);

                                }
                                Recipe recipe = new Recipe(jsonRecipe);
                                recipe.setIngredients(ingredientsArrayList);
                                recipe.setSteps(stepsArrayList);
                                mRecipeArrayList.add(recipe);
                                mRecipeAdapter.notifyDataSetChanged();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e(LOG_TAG, "Error: " + e.getMessage());
                            }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(LOG_TAG, "Error: " + error.toString());
            }
        });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


    @Override
    public void onRecipeClick(int clickedRecipePosition) {
        Recipe recipe= mRecipeArrayList.get(clickedRecipePosition);
        Preferences.saveRecipe(this, recipe);

        if(isTablet(this)){
            Log.d(LOG_TAG, "We are on a tablet");
        }
        // Broadcast the changes to the WidgetProvider
        Intent notifyWidgetIntent= new Intent(this, RecipeWidgetProvider.class);
        notifyWidgetIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids=AppWidgetManager.getInstance(getApplication())
                .getAppWidgetIds(new ComponentName(getApplication(), RecipeWidgetProvider.class));
        notifyWidgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(notifyWidgetIntent);

        Intent recipeIntent= new Intent(this, RecipeActivity.class);
        recipeIntent.putExtra(RECIPE_KEY, recipe);
        startActivity(recipeIntent);

    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
