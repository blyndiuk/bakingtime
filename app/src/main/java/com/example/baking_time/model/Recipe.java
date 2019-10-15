package com.example.baking_time.model;
/*import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

public class Recipe implements Parcelable {

    public final static String RECIPE_INTENT = "recipe";
    private int id;
    private String name;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> recipeSteps;
    private int servings;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<Step> getRecipeSteps() {
        return recipeSteps;
    }

    public void setRecipeSteps(ArrayList<Step> recipeSteps) {
        this.recipeSteps = recipeSteps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(name);
        dest.writeInt(servings);
        dest.writeInt(ingredients.size());

        for(int i = 0; i<ingredients.size(); i++){
            dest.writeString(ingredients.get(i).getIngredientName());
            dest.writeString(ingredients.get(i).getMeasure());
            dest.writeInt(ingredients.get(i).getQuantity());
        }

        dest.writeInt(recipeSteps.size());

        for(int i = 0; i< recipeSteps.size(); i++){
            dest.writeString(recipeSteps.get(i).getShortDescription());
            dest.writeString(recipeSteps.get(i).getDescription());
            dest.writeString(recipeSteps.get(i).getVideoUrl());
        }
    }

    //used to decode what's in the parcel
    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel parcel) {
            Recipe recipe = new Recipe();
            recipe.setName(parcel.readString());
            recipe.setServings(parcel.readInt());

            int sizeOfIngredientsArray = parcel.readInt();
            Log.i("# of ingredients", sizeOfIngredientsArray+"");

            ArrayList<Ingredient> ingredients = new ArrayList<>();
            for(int i = 0; i<sizeOfIngredientsArray ; i++){
                Log.i("# ", i+"");
                Ingredient ingredient = new Ingredient();
                ingredient.setIngredientName(parcel.readString());
                Log.i("Name", ingredient.getIngredientName());
                ingredient.setMeasure(parcel.readString());
                ingredient.setQuantity(parcel.readInt());
                ingredients.add(ingredient);
            }
            recipe.setIngredients(ingredients);


            int sizeOfStepsArray = parcel.readInt();
            ArrayList<Step> recipeSteps = new ArrayList<>();
            for(int i = 0; i<sizeOfStepsArray; i++){
                Step recipeStep = new Step();
                recipeStep.setShortDescription(parcel.readString());
                recipeStep.setDescription(parcel.readString());
                recipeStep.setVideoUrl(parcel.readString());
                recipeSteps.add(recipeStep);
            }
            recipe.setRecipeSteps(recipeSteps);


            return recipe;
        }
        @Override
        public Recipe[] newArray(int i) { return new Recipe[i];}
    };
}*/

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Recipe implements Parcelable {
    public final static String RECIPE_INTENT = "recipe";


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ingredients")
    @Expose
    private List<Ingredient> ingredients = null;
    @SerializedName("steps")
    @Expose
    private List<Step> steps = null;
    @SerializedName("servings")
    @Expose
    private Integer servings;
    @SerializedName("image")
    @Expose
    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(servings);
        dest.writeInt(ingredients.size());

        for(int i = 0; i<ingredients.size(); i++){
            dest.writeString(ingredients.get(i).getIngredient());
            dest.writeString(ingredients.get(i).getMeasure());
            dest.writeDouble(ingredients.get(i).getQuantity());
        }

        dest.writeInt(steps.size());

        for(int i = 0; i< steps.size(); i++){
            dest.writeString(steps.get(i).getShortDescription());
            dest.writeString(steps.get(i).getDescription());
            dest.writeString(steps.get(i).getVideoURL());
        }
    }



    //used to decode what's in the parcel
    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel parcel) {
            Recipe recipe = new Recipe();
            recipe.setName(parcel.readString());
            recipe.setServings(parcel.readInt());

            int sizeOfIngredientsArray = parcel.readInt();
       //     Log.i("# of ingredients", sizeOfIngredientsArray+"");

            ArrayList<Ingredient> ingredients = new ArrayList<>();
            for(int i = 0; i<sizeOfIngredientsArray ; i++){
           //     Log.i("# ", i+"");
                Ingredient ingredient = new Ingredient();
                ingredient.setIngredient(parcel.readString());
         //       Log.i("Name", ingredient.getIngredient());
                ingredient.setMeasure(parcel.readString());
                ingredient.setQuantity(parcel.readDouble());
                ingredients.add(ingredient);
            }
            recipe.setIngredients(ingredients);


            int sizeOfStepsArray = parcel.readInt();
            ArrayList<Step> recipeSteps = new ArrayList<>();
            for(int i = 0; i<sizeOfStepsArray; i++){
                Step recipeStep = new Step();
                recipeStep.setShortDescription(parcel.readString());
                recipeStep.setDescription(parcel.readString());
                recipeStep.setVideoURL(parcel.readString());
                recipeSteps.add(recipeStep);
            }
            recipe.setSteps(recipeSteps);


            return recipe;
        }
        @Override
        public Recipe[] newArray(int i) { return new Recipe[i];}
    };
}
