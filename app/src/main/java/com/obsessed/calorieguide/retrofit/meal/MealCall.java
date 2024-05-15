package com.obsessed.calorieguide.retrofit.meal;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.retrofit.MainApi;
import com.obsessed.calorieguide.retrofit.meal.callbacks.CallbackGetAllMeal;
import com.obsessed.calorieguide.retrofit.meal.callbacks.CallbackGetMealById;
import com.obsessed.calorieguide.retrofit.meal.callbacks.CallbackSearchMeal;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealCall {
    private String baseUrl = Data.getInstance().getBaseUrl();
    private Retrofit retrofit;
    private MainApi mainApi;

    public MealCall() {
//        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                //.addInterceptor(loggingInterceptor) // Добавление Interceptor для логирования
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl).client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mainApi = retrofit.create(MainApi.class);
    }

    public void searchMeal(String query, int userId, CallbackSearchMeal callback) {
        JsonObject requestObject = new JsonObject();
        requestObject.addProperty("word", query);
        requestObject.addProperty("user", userId);

        // Преобразование объекта запроса в JSON-строку
        Gson gson = new Gson();
        String json = gson.toJson(requestObject);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        Call<JsonObject> call = mainApi.searchMeal(requestBody);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {

                    JsonObject jsonObject = response.body();
                    if (jsonObject != null && jsonObject.has("meals")) {
                        JsonArray mealsArray = jsonObject.getAsJsonArray("meals");
                        ArrayList<Meal> allMeals = new Gson().fromJson(mealsArray, new TypeToken<List<Meal>>() {}.getType());
                        callback.mealSearchReceived(allMeals);

                        Log.d("Call", "Response searchMeal is successful!");
                    } else {
                        Log.d("Call", "No meals found in response!");
                    }
                } else {
                    Log.e("Call", "Request searchMeal failed. Response code: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("Call", "ERROR in searchMeal call: " + t.getMessage());
            }
        });
    }

    public void getMealById(int mealId, CallbackGetMealById callback) {
        Call<Meal> call = mainApi.getMealById(mealId);
        call.enqueue(new Callback<Meal>() {
            @Override
            public void onResponse(Call<Meal> call, Response<Meal> response) {
                if (response.isSuccessful()) {

                    Meal meal = response.body();
                    if (meal != null) {
                        callback.onMealByIdReceived(meal);
                        Log.d("Call", "Response getMealById is successful!");
                    } else {
                        Log.d("Call", "Meal is null!");
                    }
                } else {
                    Log.e("Call", "Request getMealById failed; Response: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Meal> call, Throwable t) {
                Log.e("Call", "ERROR in getMealById call: " + t.getMessage());
            }
        });
    }


    public void getAllMeal(CallbackGetAllMeal callback) {
        Call<JsonObject> call = mainApi.getAllMeals();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {

                    JsonObject jsonObject = response.body();
                    if (jsonObject != null && jsonObject.has("meals")) {
                        JsonArray mealsArray = jsonObject.getAsJsonArray("meals");
                        ArrayList<Meal> allMeals = new Gson().fromJson(mealsArray, new TypeToken<List<Meal>>() {}.getType());
                        callback.onAllMealReceived(allMeals);

                        Log.d("Call", "Response getAllMeal is successful!");
                    } else {
                        Log.d("Call", "No meals found in response!");
                    }
                } else {
                    Log.e("Call", "Request getAllMeal failed. Response code: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("Call", "ERROR in getAllMeal call: " + t.getMessage());
            }
        });
    }

    public void getAllMeal(int userId, CallbackGetAllMeal callback) {
        JsonObject requestObject = new JsonObject();
        requestObject.addProperty("sort", "likesDesc");
        requestObject.addProperty("two-decade", 1);
        requestObject.addProperty("user_id", userId);

        Gson gson = new Gson();
        String json = gson.toJson(requestObject);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        Call<JsonObject> call = mainApi.getAllMeals(requestBody);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {

                    JsonObject jsonObject = response.body();
                    if (jsonObject != null && jsonObject.has("meals")) {
                        JsonArray mealsArray = jsonObject.getAsJsonArray("meals");
                        ArrayList<Meal> allMeals = new Gson().fromJson(mealsArray, new TypeToken<List<Meal>>() {}.getType());
                        callback.onAllMealReceived(allMeals);

                        Log.d("Call", "Response getAllMeal is successful!");
                    } else {
                        Log.d("Call", "No meals found in response!");
                    }
                } else {
                    Log.e("Call", "Request getAllMeal failed. Response code: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("Call", "ERROR in getAllMeal call: " + t.getMessage());
            }
        });
    }

}
