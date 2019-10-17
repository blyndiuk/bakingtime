package com.example.baking_time.ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.baking_time.R;
import com.example.baking_time.RecipeWidget;
import com.example.baking_time.model.Ingredient;
import com.example.baking_time.model.Recipe;
import com.example.baking_time.model.Step;

import java.util.List;


public class RecipeActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private GestureDetectorCompat gestureObject;
    Recipe recipe;
    String text;
    String videoUrl;
    String widgetText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        if (savedInstanceState != null) {
            text = savedInstanceState.getString("text");
            videoUrl = savedInstanceState.getString("video");
        }

        if (savedInstanceState == null) {
            if (recipe == null) {
                Intent intent = getIntent();
                if (intent == null) {
                    closeOnError();
                    return;
                }
                recipe = intent.getParcelableExtra(Recipe.RECIPE_INTENT);
                int stepNumber = intent.getIntExtra("position", 0);


                if (stepNumber == -1) {
                    setTitle("Ingredients");
                    videoUrl = "";
                    text = createIngredientsText(recipe);
                    widgetText = createWidgetText(recipe);
                    setWidgetUpdateButton();

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

            }
        }
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

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putString("text", text);
        currentState.putString("video", videoUrl);
    }

    private String createWidgetText(Recipe recipe){
        String widgetText;
        List<Ingredient> ingredients = recipe.getIngredients();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(recipe.getName());
        stringBuilder.append("\n\n");
        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient ingredient = ingredients.get(i);
            stringBuilder.append(ingredient.getQuantity());
            stringBuilder.append(" ");
            stringBuilder.append(ingredient.getMeasure());
            stringBuilder.append(" ");
            stringBuilder.append(ingredient.getIngredient());
            stringBuilder.append("\n");
        }
        widgetText = stringBuilder.toString();
        return  widgetText;
    }

    private String createIngredientsText(Recipe recipe){
        String ingredientsText;
        List<Ingredient> ingredients = recipe.getIngredients();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient ingredient = ingredients.get(i);
            stringBuilder.append(ingredient.getQuantity());
            stringBuilder.append(" ");
            stringBuilder.append(ingredient.getMeasure());
            stringBuilder.append(" ");
            stringBuilder.append(ingredient.getIngredient());
            stringBuilder.append("\n\n");
        }
        ingredientsText = stringBuilder.toString();
        return ingredientsText;
    }

    private void setWidgetUpdateButton(){
        final Context context = getApplicationContext();
        Button button = findViewById(R.id.btn_display_in_widget);
        button.setVisibility(View.VISIBLE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
                ComponentName thisWidget = new ComponentName(context, RecipeWidget.class);
                remoteViews.setTextViewText(R.id.appwidget_text, widgetText);
                appWidgetManager.updateAppWidget(thisWidget, remoteViews);
            }
        });
    }
}
