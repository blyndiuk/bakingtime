package com.example.baking_time.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.baking_time.R;
import com.example.baking_time.model.Ingredient;
import com.example.baking_time.model.Recipe;
import com.example.baking_time.model.Step;

import java.util.List;


public class RecipeActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private GestureDetectorCompat gestureObject;
    Recipe recipe;
    String text;
    String videoUrl;
    int stepNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);


        if (recipe == null) {
            Intent intent = getIntent();
            if (intent == null) {
                closeOnError();
                return;
            }
            recipe = intent.getParcelableExtra(Recipe.RECIPE_INTENT);
            stepNumber = intent.getIntExtra("position", 0);
        }


        if (stepNumber == -1) {
            setTitle("Ingredients");
            videoUrl = "";
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
            text = stringBuilder.toString();
        } else {
            Step step = recipe.getSteps().get(stepNumber);
            if (stepNumber == 0) {
                setTitle(step.getShortDescription());
                text = "";
            } else {
                setTitle(step.getShortDescription());
                text = step.getDescription();
            }
            videoUrl = step.getVideoURL();
        }


        RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
        recipeStepFragment.setDescription(text);
        recipeStepFragment.setVideoUrl(videoUrl);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.video_and_instructions_container, recipeStepFragment)
                .commit();

        setActionBar();

        gestureObject = new GestureDetectorCompat(this, this);

    }

    private void closeOnError() {
        Log.i("LOG", "Recipe details not found");
        finish();
    }

    private void setActionBar() {
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE && !videoUrl.equals("")) {
            this.getSupportActionBar().hide();
        } else {
            this.getSupportActionBar().show();
        }
    }



    final int SWIPE_MIN_DISTANCE = 80;
    final int SWIPE_THRESHOLD_VELOCITY = 50;

    @Override
    public boolean onFling(MotionEvent downEvent, MotionEvent moveEvent, float velocityX, float velocityY) {

        float diffY = moveEvent.getY() - downEvent.getY();
        Log.i("SWIPE", diffY + "");
        float diffX = moveEvent.getX() - downEvent.getX();
        Log.i("SWIPE", diffX + "");
        if (Math.abs(diffX) > Math.abs(diffY)) {

            if (Math.abs(diffX) > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                if (diffX > 0) {
                    onSwipeRight();
                } else {
                    onSwipeLeft();
                }
                return true;
            }
        }
        return false;
    }

    //other obligatory methods that had to be overridden
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    private void onSwipeRight() {
        Toast.makeText(this, "swipe right", Toast.LENGTH_SHORT).show();
    }

    private void onSwipeLeft() {
        Toast.makeText(this, "swipe left", Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureObject.onTouchEvent(event);
        Log.i("OnTouchEvent", "on touch");
        return super.onTouchEvent(event);
    }
}
