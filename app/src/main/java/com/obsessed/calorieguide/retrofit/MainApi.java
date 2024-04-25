package com.obsessed.calorieguide.retrofit;

import com.google.gson.JsonObject;
import com.obsessed.calorieguide.retrofit.food.Food;
import com.obsessed.calorieguide.retrofit.user.AuthRequest;
import com.obsessed.calorieguide.retrofit.user.RegistrationRequest;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MainApi {
    //Food (product)
    @GET("products/{id}")
    Call<Food> getFoodById(@Path("id") int foodId);

    @GET("products")
    Call<JsonObject> getAllFood();

    @POST("/products")
    Call<JsonObject> postFood(@Body RequestBody requestBody);

    @PUT("/products/{product_id}")
    Call<JsonObject> updateProduct(@Path("product_id") int productId, @Body RequestBody requestBody);


    //User
    @POST("/login")
    Call<JsonObject> auth(@Body AuthRequest authRequest);

    @POST("/user")
    Call<JsonObject> registerUser(@Body RegistrationRequest registrationRequest);

    @PUT("/user/{user_id}")
    Call<JsonObject> updateUser(@Path("user_id") int userId, @Body RegistrationRequest registrationRequest);
}
