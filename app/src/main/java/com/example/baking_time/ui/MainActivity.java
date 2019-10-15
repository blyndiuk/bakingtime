package com.example.baking_time.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.baking_time.R;
import com.example.baking_time.model.Recipe;
import com.example.baking_time.utils.JsonPlaceHolderApi;

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
               // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
               // mRecyclerView.setLayoutManager(linearLayoutManager);
                mRecyclerView.setAdapter(reviewsAdapter);

            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.i("LOG failure", t.getMessage());
            }
        });
    }
}
