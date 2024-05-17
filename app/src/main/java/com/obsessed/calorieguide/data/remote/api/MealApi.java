package com.obsessed.calorieguide.data.remote.api;

import com.google.gson.JsonObject;
import com.obsessed.calorieguide.data.models.Meal;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MealApi {
    //Meals
    @POST("/meals")
    Call<JsonObject> getAllMeals();
    @POST("/meals")
    Call<JsonObject> getAllMeals(@Body RequestBody requestBody);
    @GET("/meals/{meal_id}")
    Call<Meal> getMealById(@Path("meal_id") int mealId);
    @POST("/meal")
    Call<JsonObject> postMeal(@Body RequestBody requestBody);
    @PUT("/meals/{meal_id}")
    Call<JsonObject> updateMeal(@Path("meal_id") int mealId, @Body RequestBody requestBody);
    @POST("/meals/like")
    Call<JsonObject> likeMeal(@Body RequestBody requestBody);
    @DELETE("/meals/{meal_id}")
    Call<JsonObject> deleteMeal(@Path("meal_id") int mealId);
    @POST("/meals/search")
    Call<JsonObject> searchMeal(@Body RequestBody requestBody);
}
