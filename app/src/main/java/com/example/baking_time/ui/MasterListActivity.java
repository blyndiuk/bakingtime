package com.example.baking_time.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.baking_time.Constants;
import com.example.baking_time.R;
import com.example.baking_time.model.Recipe;


public class MasterListActivity extends AppCompatActivity {
    public static boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_list);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if (intent == null) {
                closeOnError();
                return;
            }

            Recipe recipe = intent.getParcelableExtra(Constants.RECIPE_INTENT);
            setTitle(recipe.getName());

            MasterListFragment masterListFragment = new MasterListFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("i", recipe);
            masterListFragment.setArguments(bundle);
            FragmentManager fragmentManagerMl = getSupportFragmentManager();
            fragmentManagerMl.beginTransaction()
                    .add(R.id.master_list_container, masterListFragment)
                    .commit();


            if (findViewById(R.id.baking_time_linear_layout) != null) {
                mTwoPane = true;
                String videoUrl = recipe.getSteps().get(0).getVideoURL();

                RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
                recipeStepFragment.setDescription("");
                recipeStepFragment.setVideoUrl(videoUrl);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.video_and_instructions_container, recipeStepFragment)
                        .commit();

            } else {
                mTwoPane = false;
            }
        }
    }

    private void closeOnError() {
        Log.i("LOG", "Recipe details not found");
        finish();
    }
}
