package com.georgeampartzidis.bakie.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.georgeampartzidis.bakie.R;
import com.georgeampartzidis.bakie.model.Recipe;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    ArrayList<Recipe> mRecipesList;
    Context mContext;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipesList){
        this.mContext= context;
        this.mRecipesList= recipesList;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.bindTo(mRecipesList.get(position));
    }

    @Override
    public int getItemCount() {
        if(mRecipesList.size()!=0){
            return mRecipesList.size();
        } else
        return 0;
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        ImageView recipeImageView;
        TextView recipeTitleTextVIew;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeImageView = itemView.findViewById(R.id.iv_recipe_image);
            recipeTitleTextVIew = itemView.findViewById(R.id.tv_recipe_name);
        }

        public void bindTo(Recipe recipe) {
            recipeTitleTextVIew.setText(recipe.getName());
        }
    }
}
