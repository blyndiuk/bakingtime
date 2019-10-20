package com.example.baking_time.utils;
import com.example.baking_time.Constants;
import com.example.baking_time.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    @GET(Constants.URL_PATH)
    Call<List<Recipe>> getRecipes();
}
