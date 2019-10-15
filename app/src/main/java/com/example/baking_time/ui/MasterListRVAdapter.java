package com.example.baking_time.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.baking_time.R;
import com.example.baking_time.model.Ingredient;
import com.example.baking_time.model.Recipe;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MasterListRVAdapter extends RecyclerView.Adapter<MasterListRVAdapter.MyViewHolder> {

    private Recipe recipe;
    private Context context;

    MasterListRVAdapter(Recipe recipe, Context context) {
        this.context = context;
        this.recipe = recipe;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        Button button;

        MyViewHolder(View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.bt_step);
        }
    }

    @NonNull
    @Override
    public MasterListRVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.master_list_row_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {

        if (position == 0) {
            myViewHolder.button.setText("Ingredients");
            myViewHolder.button.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            String shortDesc = recipe.getSteps().get(position - 1).getShortDescription();
            if (position == 1) {
                myViewHolder.button.setText(shortDesc);
                myViewHolder.button.setTypeface(Typeface.DEFAULT_BOLD);
            } else {
                myViewHolder.button.setText("Step " + (position - 1) + ": " + shortDesc);
            }
        }


        myViewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MasterListActivity.mTwoPane) {

                    if (position == 0) {
                        List<Ingredient> ingredients = recipe.getIngredients();
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < ingredients.size(); i++) {
                            Ingredient ingredient = ingredients.get(i);
                            stringBuilder.append(ingredient.getQuantity());
                            stringBuilder.append(" ");
                            stringBuilder.append(ingredient.getMeasure());
                            Log.i("LOG", "Measure : " + ingredient.getQuantity());
                            stringBuilder.append(" ");
                            stringBuilder.append(ingredient.getIngredient());
                            stringBuilder.append("\n\n");
                        }
                        RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
                        recipeStepFragment.setDescription(stringBuilder.toString());
                        recipeStepFragment.setVideoUrl("");
                        FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.video_and_instructions_container, recipeStepFragment)
                                .commit();
                    } else {
                        int position = myViewHolder.getAdapterPosition();
                        String description = recipe.getSteps().get(position - 1).getDescription();
                        String url = recipe.getSteps().get(position - 1).getVideoURL();
                        RecipeStepFragment recipeStepFragment = new RecipeStepFragment();

                        if (position == 1)
                            recipeStepFragment.setDescription("");
                        else
                            recipeStepFragment.setDescription(description);

                        recipeStepFragment.setVideoUrl(url);
                        FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.video_and_instructions_container, recipeStepFragment)
                                .commit();
                    }

                } else {
                    final Intent intent = new Intent(context, RecipeActivity.class);
                    intent.putExtra(Recipe.RECIPE_INTENT, recipe);
                    intent.putExtra("position", (position - 1));
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        //adding one because we need to desplay all the steps + ingredients
        return recipe.getSteps().size() + 1;
    }
}
