package com.example.baking_time.ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;


import com.example.baking_time.R;
import com.example.baking_time.RecipeWidget;
import com.example.baking_time.model.Ingredient;
import com.example.baking_time.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class MasterListFragment extends Fragment {

    //   OnTextClickListener mCallBack;
    ArrayList<String> mShortDescriptions;
    Recipe recipe;

    // Mandatory empty constructor
    public MasterListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            recipe = bundle.getParcelable("i");
        } else
            Log.e("Bundle is null", "null");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);


        if (recipe == null) {
            return rootView;
        }

        mShortDescriptions = new ArrayList<>();
        for (int i = 0; i < recipe.getSteps().size(); i++) {
            String stepDesc = "Step " + (i + 1) + ": " + recipe.getSteps().get(i).getShortDescription();
            mShortDescriptions.add(stepDesc);
        }

        RecyclerView recyclerView = rootView.findViewById(R.id.rv_master_list);
        TextView textView = rootView.findViewById(R.id.tv_ingredients);
        String text = createIngredientsText(recipe);
        textView.setText(text);

        MasterListRVAdapter reviewsAdapter = new MasterListRVAdapter(recipe, getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(reviewsAdapter);

        String widgetText = createWidgetText(recipe);
        Button button = rootView.findViewById(R.id.btn_display_in_widget);
        setWidgetUpdateButton(button, widgetText);

        return rootView;
    }

    private void setWidgetUpdateButton(Button button, final String widgetText){
        final Context context = getActivity();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                if(context != null) {
                    RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
                    ComponentName thisWidget = new ComponentName(context, RecipeWidget.class);
                    remoteViews.setTextViewText(R.id.appwidget_text, widgetText);
                    appWidgetManager.updateAppWidget(thisWidget, remoteViews);
                }
            }
        });
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
        stringBuilder.append("  Ingredients List");
        stringBuilder.append("\n");
        stringBuilder.append("  Servings: ");
        int servings = recipe.getServings();
        stringBuilder.append(servings);
        stringBuilder.append("\n\n");
        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient ingredient = ingredients.get(i);
            stringBuilder.append("• ");
            stringBuilder.append(ingredient.getQuantity());
            stringBuilder.append(" ");
            stringBuilder.append(ingredient.getMeasure());
            stringBuilder.append(" ");
            stringBuilder.append(ingredient.getIngredient());
            stringBuilder.append("\n");
        }
        ingredientsText = stringBuilder.toString();
        return ingredientsText;
    }
}
