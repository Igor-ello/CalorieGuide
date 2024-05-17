package com.obsessed.calorieguide.network.food;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.network.MainApi;
import com.obsessed.calorieguide.network.food.callbacks.CallbackGetAllFood;
import com.obsessed.calorieguide.network.food.callbacks.CallbackGetFoodById;
import com.obsessed.calorieguide.network.food.callbacks.CallbackSearchFood;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodCall {
    private String baseUrl = Data.getInstance().getBaseUrl();
    private Retrofit retrofit;
    private MainApi mainApi;

    public FoodCall() {
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


    public void searchFood(String query, int userId, CallbackSearchFood callback) {
        JsonObject requestObject = new JsonObject();
        requestObject.addProperty("word", query);
        requestObject.addProperty("user", userId);

        // Преобразование объекта запроса в JSON-строку
        Gson gson = new Gson();
        String json = gson.toJson(requestObject);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        Call<JsonObject> call = mainApi.searchFood(requestBody);
        Log.d("Call", call.toString() + " " + query);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    if (jsonObject != null && jsonObject.has("products")) {
                        JsonArray productsArray = jsonObject.getAsJsonArray("products");
                        ArrayList<Food> allFood = new Gson().fromJson(productsArray, new TypeToken<List<Food>>() {}.getType());
                        callback.foodSearchReceived(allFood);

                        Log.d("Call", "Response searchFood is successful!");
                    } else {
                        Log.d("Call", "No products found in response!");
                    }
                } else {
                    Log.e("Call", "Request searchFood failed. Response code: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("Call", "ERROR in searchFood call: " + t.getMessage());
            }
        });
    }

    public void getFoodById(int foodId, CallbackGetFoodById callback) {
        Call<Food> call = mainApi.getFoodById(foodId);
        // Запуск асинхронного запроса в другом потоке
        call.enqueue(new Callback<Food>() {
            @Override
            public void onResponse(Call<Food> call, Response<Food> response) {
                if (response.isSuccessful()) {

                    Food food = response.body();
                    if (food != null) {
                        callback.onFoodByIdReceived(food);
                        Log.d("Call", "Response getFoodById is successful!");
                    } else {
                        Log.d("Call", "Food is null!");
                    }
                } else {
                    Log.e("Call", "Request getAllFood failed; Response: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Food> call, Throwable t) {
                Log.e("Call", "ERROR in getAllFood call: " + t.getMessage());
            }
        });
    }

    public void getAllFood(CallbackGetAllFood callback) {
        Call<JsonObject> call = mainApi.getAllFood(); // JsonObject из библиотеки Gson
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {

                    JsonObject jsonObject = response.body();
                    if (jsonObject != null && jsonObject.has("products")) {
                        JsonArray productsArray = jsonObject.getAsJsonArray("products");
                        ArrayList<Food> allFood = new Gson().fromJson(productsArray, new TypeToken<List<Food>>() {}.getType());
                        callback.onAllFoodReceived(allFood);

                        Log.d("Call", "Response getAllFood is successful!");
                    } else {
                        Log.d("Call", "No products found in response!");
                    }
                } else {
                    Log.e("Call", "Request getAllFood failed; Response: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("Call", "ERROR in getAllFood call: " + t.getMessage());
            }
        });
    }

    public void getAllFood(int userId, CallbackGetAllFood callback) {
        JsonObject requestObject = new JsonObject();
        requestObject.addProperty("sort", "likesDesc");
        requestObject.addProperty("two-decade", 1);
        requestObject.addProperty("user_id", userId);

        Gson gson = new Gson();
        String json = gson.toJson(requestObject);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        Call<JsonObject> call = mainApi.getAllFood(requestBody);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {

                    JsonObject jsonObject = response.body();
                    if (jsonObject != null && jsonObject.has("products")) {
                        JsonArray productsArray = jsonObject.getAsJsonArray("products");
                        ArrayList<Food> allFood = new Gson().fromJson(productsArray, new TypeToken<List<Food>>() {}.getType());
                        callback.onAllFoodReceived(allFood);

                        Log.d("Call", "Response getAllFood is successful!");
                    } else {
                        Log.d("Call", "No products found in response!");
                    }
                } else {
                    Log.e("Call", "Request getAllFood failed; Response: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("Call", "ERROR in getAllFood call: " + t.getMessage());
            }
        });
    }

}
