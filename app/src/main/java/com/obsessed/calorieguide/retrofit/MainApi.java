package com.obsessed.calorieguide.retrofit;

import com.google.gson.JsonObject;
import com.obsessed.calorieguide.retrofit.food.Food;
import com.obsessed.calorieguide.retrofit.meal.Meal;
import com.obsessed.calorieguide.retrofit.user.AuthRequest;
import com.obsessed.calorieguide.retrofit.user.RegistrationRequest;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    @POST("/product")
    Call<JsonObject> postFood(@Body RequestBody requestBody);

    @PUT("/products/{product_id}")
    Call<JsonObject> updateFood(@Path("product_id") int productId, @Body RequestBody requestBody);

    @DELETE("/products/{product_id}")
    Call<JsonObject> deleteFood(@Path("product_id") int productId);

    @POST("/products/like")
    Call<JsonObject> likeFood(@Body RequestBody requestBody);

    //Meals
    @GET("/meals")
    Call<JsonObject> getAllMeals();
    @GET("/meals/{meal_id}")
    Call<Meal> getMealById(@Path("meal_id") int mealId);
    @POST("/meal")
    Call<JsonObject> postMeal(@Body RequestBody requestBody);
    @PUT("/meal/{meal_id}")
    Call<JsonObject> updateMeal(@Path("meal_id") int mealId, @Body RequestBody requestBody);
    @POST("/meal/like")
    Call<JsonObject> likeMeal(@Body RequestBody requestBody);
    @DELETE("/meal/{meal_id}")
    Call<JsonObject> deleteMeal(@Path("meal_id") int mealId);




    //User
    @POST("/login")
    Call<JsonObject> auth(@Body AuthRequest authRequest);

    @POST("/user")
    Call<JsonObject> registerUser(@Body RegistrationRequest registrationRequest);

    @PUT("/user/{user_id}")
    Call<JsonObject> updateUser(@Path("user_id") int userId, @Body RegistrationRequest registrationRequest);

    @DELETE("/user/{user_id}")
    Call<JsonObject> deleteUser(@Path("user_id") int userId);
}
