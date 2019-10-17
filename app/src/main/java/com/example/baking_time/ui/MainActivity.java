package com.example.baking_time.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baking_time.R;
import com.example.baking_time.model.Recipe;
import com.example.baking_time.utils.JsonPlaceHolderApi;
import com.example.baking_time.utils.NetworkingUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

public class MainActivity extends AppCompatActivity {

    public RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean isConnected = NetworkingUtils.connectedToTheInternet(this);

        if(isConnected)
            setUI(isConnected);
        else {
            setUI(isConnected);
            return;
        }


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Recipe>> call = jsonPlaceHolderApi.getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                List<Recipe> recipes = response.body();

                mRecyclerView = findViewById(R.id.rv_main_activity);
                Context context = getApplicationContext();
                MainActivityRecyclerViewAdapter reviewsAdapter = new MainActivityRecyclerViewAdapter(recipes, context);
                mRecyclerView.setAdapter(reviewsAdapter);

            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.i("LOG failure", t.getMessage());
            }
        });
    }

    private void setUI(boolean isConnected) {
        LinearLayout mainLayout = findViewById(R.id.ll_main_layout);
        RecyclerView recyclerView = findViewById(R.id.rv_main_activity);
        if(!isConnected) {
            recyclerView.setVisibility(View.GONE);
            TextView textView = new TextView(this);
            textView.setText("No Internet Connection");
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            textView.setGravity(Gravity.CENTER);
            mainLayout.addView(textView);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}
