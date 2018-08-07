package com.georgeampartzidis.bakie.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.georgeampartzidis.bakie.R;
import com.georgeampartzidis.bakie.model.Recipe;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    ArrayList<Recipe> mRecipesList;
    Context mContext;
    final private RecipeClickListener mOnClickListener;

    public interface RecipeClickListener {
        void onRecipeClick(int clickedRecipeIndex);
    }

    public RecipeAdapter(Context context, ArrayList<Recipe> recipesList, RecipeClickListener listener){
        this.mContext= context;
        this.mRecipesList= recipesList;
        this.mOnClickListener= listener;
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

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView recipeImageView;
        TextView recipeTitleTextVIew;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeImageView = itemView.findViewById(R.id.iv_recipe_image);
            recipeTitleTextVIew = itemView.findViewById(R.id.tv_recipe_name);
            itemView.setOnClickListener(this);
        }

        public void bindTo(Recipe recipe) {
            recipeTitleTextVIew.setText(recipe.getName());
        }

        @Override
        public void onClick(View v) {
            int clickedPosition= getAdapterPosition();
            mOnClickListener.onRecipeClick(clickedPosition);
        }
    }
}