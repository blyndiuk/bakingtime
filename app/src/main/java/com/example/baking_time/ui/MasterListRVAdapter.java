package com.example.baking_time.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.baking_time.Constants;
import com.example.baking_time.R;
import com.example.baking_time.model.Recipe;


public class MasterListRVAdapter extends RecyclerView.Adapter<MasterListRVAdapter.MyViewHolder> {

    private Recipe recipe;
    private Context context;

    MasterListRVAdapter(Recipe recipe, Context context) {
        this.context = context;
        this.recipe = recipe;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_step);
        }
    }

    @NonNull
    @Override
    public MasterListRVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.master_list_row_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int position) {

        String shortDesc = recipe.getSteps().get(position).getShortDescription();
        if (position == 0) {
            myViewHolder.textView.setText(shortDesc);
            myViewHolder.textView.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            myViewHolder.textView.setText("Step " + (position) + ": " + shortDesc);
            myViewHolder.textView.setTypeface(Typeface.DEFAULT);
        }


        myViewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MasterListActivity.mTwoPane) {

                    int position = myViewHolder.getAdapterPosition();
                    String description = recipe.getSteps().get(position).getDescription();
                    String url = recipe.getSteps().get(position).getVideoURL();
                    RecipeStepFragment recipeStepFragment = new RecipeStepFragment();

                    if (position == 0)
                        recipeStepFragment.setDescription("");
                    else
                        recipeStepFragment.setDescription(description);

                    recipeStepFragment.setVideoUrl(url);
                    FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.video_and_instructions_container, recipeStepFragment)
                            .commit();


                } else {
                    final Intent intent = new Intent(context, RecipeActivity.class);
                    intent.putExtra(Constants.RECIPE_INTENT, recipe);
                    intent.putExtra(Constants.POSITION_INTENT, (myViewHolder.getAdapterPosition()));
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        //adding one because we need to desplay all the steps + ingredients
        return recipe.getSteps().size();
    }
}
