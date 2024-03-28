package com.obsessed.calorieguide.retrofit;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FoodApi {
//    @GET("food/{id}") //TODO берём нужный Call
//    Call<Food> getFoodById(@Path("id") int foodId);
    @GET("products/{id}")
    Call<Food> getFoodById(@Path("id") int foodId);

    @GET("products")
    Call<JsonObject> getAllFood();
}
