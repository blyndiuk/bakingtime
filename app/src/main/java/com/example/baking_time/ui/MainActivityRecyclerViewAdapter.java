package com.example.baking_time.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.baking_time.R;
import com.example.baking_time.model.Recipe;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.List;

public class MainActivityRecyclerViewAdapter extends RecyclerView.Adapter<MainActivityRecyclerViewAdapter.MyViewHolder> {

    private final Context context;
    private List<Recipe> recipes;

    MainActivityRecyclerViewAdapter(List<Recipe> recipes, Context context) {

        this.recipes = recipes;
        this.context = context;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        Button button;

        MyViewHolder(View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.bt_recipe_card);
        }
    }

    @NonNull
    @Override
    public MainActivityRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                           int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {


        final Recipe recipe = recipes.get(position);
        myViewHolder.button.setText(recipe.getName());


        myViewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recipe recipe = recipes.get(position);
                Intent intent = new Intent(context, MasterListActivity.class);
                intent.putExtra(Recipe.RECIPE_INTENT, recipe);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Log.i("LOG", "starting MasterActivity");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

}
