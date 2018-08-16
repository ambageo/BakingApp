package com.georgeampartzidis.bakie.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.georgeampartzidis.bakie.R;
import com.georgeampartzidis.bakie.model.Ingredient;
import com.georgeampartzidis.bakie.model.Recipe;
import com.georgeampartzidis.bakie.utils.Preferences;

import java.util.ArrayList;

public class IngredientsListFactory implements RemoteViewsService.RemoteViewsFactory {

    public static final String TAG = IngredientsListFactory.class.getSimpleName();
    /**
     * The RemoteViewsFactory acts as the adapter providing the data to the widget
     * Explanation for most of the methods in:
     * https://www.sitepoint.com/killer-way-to-show-a-list-of-items-in-android-collection-widget/
     */
    private Context mContext;
    private Recipe recipe;
    private ArrayList<Ingredient> ingredientsList;

    public IngredientsListFactory(Context context) {
        this.mContext = context;
        ingredientsList= new ArrayList<>();

    }

    @Override
    public void onCreate() {
        recipe= Preferences.loadRecipe(mContext);
        ingredientsList= recipe.getIngredients();

    }

    /**
     * onDataSetChanged is called whenever the widget is updated
     */
    @Override
    public void onDataSetChanged() {
        recipe = Preferences.loadRecipe(mContext);
        Log.d(TAG, "Data is loaded from onDataSetChanged");
        ingredientsList= recipe.getIngredients();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        Log.d(TAG, "Number of ingredients for "
                + recipe.getName() + " :" + String.valueOf(ingredientsList.size()));
        return recipe.getIngredients().size();

    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget_provider);
        String ingredient= ingredientsList.get(position).getIngredient();
        rv.setTextViewText(R.id.widget_ingredients, ingredient);
        Log.d(TAG, "position: " + position
                + ", Ingredient: " + ingredient);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
