package com.georgeampartzidis.bakie.widget;

import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

public class IngredientsListFactory implements RemoteViewsService.RemoteViewsFactory{
    /**
     * The RemoteViewsFactory acts as the adapter providing the data to the widget
     */
    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        return null;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
