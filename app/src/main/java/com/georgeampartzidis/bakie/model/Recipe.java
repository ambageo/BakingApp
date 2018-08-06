package com.georgeampartzidis.bakie.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


public class Recipe implements Parcelable {

    private int id;
    private String name;
    private int servings;
    private String image;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;

    public Recipe() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName() {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage() {
        this.image = image;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients() {
        this.ingredients = ingredients;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public void setSteps() {
        this.steps = steps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(servings);
        dest.writeString(image);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
    }

    private Recipe(Parcel parcel) {
        id = parcel.readInt();
        name = parcel.readString();
        servings = parcel.readInt();
        image = parcel.readString();
        ingredients = parcel.createTypedArrayList(Ingredient.CREATOR);
        steps = parcel.createTypedArrayList(Step.CREATOR);
    }

    public static final Parcelable.Creator<Recipe> CREATOR
            = new Parcelable.Creator<Recipe>() {

        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
