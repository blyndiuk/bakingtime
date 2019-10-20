package com.example.baking_time.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.baking_time.Constants;
import com.example.baking_time.R;
import com.example.baking_time.model.Recipe;
import com.example.baking_time.model.Step;


public class RecipeActivity extends AppCompatActivity {

    Recipe recipe;
    String text;
    String videoUrl;
    int stepPositionInList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        if (savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable(Constants.KEY_RECIPE);
            stepPositionInList = savedInstanceState.getInt(Constants.KEY_POSITION);
        }

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if (intent == null) {
                closeOnError();
                return;
            }
            recipe = intent.getParcelableExtra(Constants.RECIPE_INTENT);
            stepPositionInList = intent.getIntExtra(Constants.POSITION_INTENT, 0);

            Step step = recipe.getSteps().get(stepPositionInList);
            if (stepPositionInList == 0) {
                setTitle(step.getShortDescription());
                text = "";
            } else {
                setTitle(step.getShortDescription());
                text = step.getDescription();
            }
            videoUrl = step.getVideoURL();


            RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
            recipeStepFragment.setDescription(text);
            recipeStepFragment.setVideoUrl(videoUrl);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.video_and_instructions_container, recipeStepFragment)
                    .commit();
        }
        Button previous = findViewById(R.id.btn_previous);
        Button next = findViewById(R.id.btn_next);
        setPreviousAndNextButtons(previous, next);

        setActionBar();

    }

    private void closeOnError() {
        Log.i("LOG", "Recipe details not found");
        finish();
    }


    private void setActionBar() {
        videoUrl = recipe.getSteps().get(stepPositionInList).getVideoURL();
        int orientation = this.getResources().getConfiguration().orientation;
        if (this.getSupportActionBar() != null) {
            if (orientation == Configuration.ORIENTATION_LANDSCAPE && videoUrl != null && !videoUrl.equals("")) {
                this.getSupportActionBar().hide();
            } else {
                this.getSupportActionBar().show();
            }
        }
    }


    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putParcelable(Constants.KEY_RECIPE, recipe);
        currentState.putInt(Constants.KEY_POSITION, stepPositionInList);
    }


    private void setPreviousAndNextButtons(final Button previous, final Button next) {
        int listSize = recipe.getSteps().size();

        if (stepPositionInList == 0) {
            previous.setVisibility(View.INVISIBLE);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stepPositionInList = stepPositionInList + 1;
                    Step step = recipe.getSteps().get(stepPositionInList);
                    replaceRecipeStepFragment(step);
                    setTitle(step.getShortDescription());
                    setPreviousAndNextButtons(previous, next);
                }
            });

        } else if (stepPositionInList == (listSize - 1)) {
            next.setVisibility(View.INVISIBLE);
            previous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stepPositionInList = stepPositionInList - 1;
                    Step step = recipe.getSteps().get(stepPositionInList);
                    replaceRecipeStepFragment(step);
                    setTitle(step.getShortDescription());
                    setPreviousAndNextButtons(previous, next);
                }
            });

        } else {
            previous.setVisibility(View.VISIBLE);
            next.setVisibility(View.VISIBLE);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stepPositionInList = stepPositionInList + 1;
                    Step step = recipe.getSteps().get(stepPositionInList);
                    replaceRecipeStepFragment(step);
                    setTitle(step.getShortDescription());
                    setPreviousAndNextButtons(previous, next);
                }
            });
            previous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stepPositionInList = stepPositionInList - 1;
                    Step step = recipe.getSteps().get(stepPositionInList);
                    replaceRecipeStepFragment(step);
                    setTitle(step.getShortDescription());
                    setPreviousAndNextButtons(previous, next);
                }
            });

        }
    }

    private void replaceRecipeStepFragment(Step step) {
        text = step.getDescription();
        videoUrl = step.getVideoURL();
        RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
        if (stepPositionInList == 0)
            text = "";
        recipeStepFragment.setDescription(text);
        recipeStepFragment.setVideoUrl(videoUrl);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.video_and_instructions_container, recipeStepFragment)
                .commit();
    }
}
