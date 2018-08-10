package com.georgeampartzidis.bakie.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.georgeampartzidis.bakie.R;
import com.georgeampartzidis.bakie.adapters.RecipeAdapter;
import com.georgeampartzidis.bakie.model.Recipe;
import com.georgeampartzidis.bakie.model.Ingredient;
import com.georgeampartzidis.bakie.model.Step;

import org.json.JSONArray;
import org.json.JSONException;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.rv_recipes);
        mRecipeArrayList = new ArrayList<>();

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
                                    //Ingredient ingredient= new Ingredient(quantity, measure, ingredientString);
                                    Ingredient ingredient= new Ingredient();
                                    ingredient.setQuantity(quantity);
                                    ingredient.setMeasure(measure);
                                    ingredient.setIngredient(ingredientString);
                                    ingredientsArrayList.add(ingredient);

                                    Log.d(LOG_TAG, "Ingredient added: "
                                    + String.valueOf(quantity) + " "
                                    + measure + " "
                                    + ingredientString);
                                }

                                ArrayList<Step> stepsArrayList= new ArrayList<>();
                                for(int k=0; k<stepsJsonArray.length(); k++){
                                    JSONObject jsonStep= stepsJsonArray.getJSONObject(k);
                                    Step step= new Step(jsonStep);
                                    stepsArrayList.add(step);

                                }
                                Recipe recipe = new Recipe(jsonRecipe);
                                recipe.setIngredients(ingredientsArrayList);
                                recipe.setSteps(stepsArrayList);
                                mRecipeArrayList.add(recipe);
                                Log.i(LOG_TAG, "Recipe added: " + recipe.getName()
                                + " ingredients: " + String.valueOf(recipe.getIngredients().size())
                                + " steps: " + String.valueOf(recipe.getSteps().size()));
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
      Log.d(LOG_TAG, "Clicked on recipe: " + recipe.getName());
        Intent recipeIntent= new Intent(this, RecipeActivity.class);
        recipeIntent.putExtra(RECIPE_KEY, recipe);
        startActivity(recipeIntent);

    }
}
