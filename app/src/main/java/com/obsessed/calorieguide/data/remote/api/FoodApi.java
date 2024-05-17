package com.obsessed.calorieguide.data.remote.api;

import com.google.gson.JsonObject;
import com.obsessed.calorieguide.data.models.Food;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FoodApi {
    //Food (product)
    @GET("products/{id}")
    Call<Food> getFoodById(@Path("id") int foodId);

    @POST("products")
    Call<JsonObject> getAllFood();

    @POST("products")
    Call<JsonObject> getAllFood(@Body RequestBody requestBody);

    @POST("/product")
    Call<JsonObject> postFood(@Body RequestBody requestBody);

    @PUT("/products/{product_id}")
    Call<JsonObject> updateFood(@Path("product_id") int productId, @Body RequestBody requestBody);

    @DELETE("/products/{product_id}")
    Call<JsonObject> deleteFood(@Path("product_id") int productId);

    @POST("/products/like")
    Call<JsonObject> likeFood(@Body RequestBody requestBody);

    @POST("/products/search")
    Call<JsonObject> searchFood(@Body RequestBody requestBody);

    //For repo
    @POST("products")
    Call<JsonObject> getAllFoodLive(Callback<List<Food>> callback);

}
