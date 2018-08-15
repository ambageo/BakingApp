package com.georgeampartzidis.bakie.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.georgeampartzidis.bakie.R;
import com.georgeampartzidis.bakie.model.Recipe;
import com.georgeampartzidis.bakie.ui.MainActivity;
import com.georgeampartzidis.bakie.utils.Preferences;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    public static final String TAG= RecipeWidgetProvider.class.getSimpleName();
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Recipe recipe= Preferences.loadRecipe(context);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
        views.setTextViewText(R.id.widget_recipe_name, recipe.getName());
        // Create an intent to launch MainActivity when clicked
        Intent intent= new Intent(context, MainActivity.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(context, 0, intent, 0);
        // Click handler will allow launching the PendingIntent
        views.setOnClickPendingIntent(R.id.widget_listview, pendingIntent);

        Intent ingredientsIntent= new Intent(context, RecipeWidgetService.class);
        ingredientsIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        views.setRemoteAdapter(R.id.widget_listview, ingredientsIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_listview);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Log.d(TAG, "onUpdate is called");
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
}

