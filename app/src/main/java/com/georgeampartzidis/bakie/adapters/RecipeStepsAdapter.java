package com.georgeampartzidis.bakie.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.georgeampartzidis.bakie.model.Recipe;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.RecipeStepsViewHolder> {

    private Context mContext;
    private Recipe mRecipe;
    @Override
    public RecipeStepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder( RecipeStepsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class RecipeStepsViewHolder extends RecyclerView.ViewHolder {
        public RecipeStepsViewHolder(View itemView) {
            super(itemView);
        }
    }
}
