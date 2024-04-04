package com.obsessed.calorieguide.retrofit;

import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FoodApi {
    @GET("products/{id}")
    Call<Food> getFoodById(@Path("id") int foodId);

    @GET("products")
    Call<JsonObject> getAllFood();

    @POST("/product")
    Call<JsonObject> postFood(@Body RequestBody requestBody);
}
