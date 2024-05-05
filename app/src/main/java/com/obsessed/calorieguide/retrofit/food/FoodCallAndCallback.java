package com.obsessed.calorieguide.retrofit.food;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.retrofit.MainApi;
import com.obsessed.calorieguide.retrofit.user.User;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodCallAndCallback {
    private CallbackGetAllFood callbackGetAllFood; //интерфейс для возврата результата запроса getAllFood
    private CallbackGetFoodById callbackGetFoodById; //интерфейс для возврата результата запроса getFoodById
    private Retrofit retrofit;
    private MainApi mainApi;

    // Перегрузка для запроса getAllFood
    public FoodCallAndCallback(CallbackGetAllFood callbackGetAllFood) {
        this.callbackGetAllFood = callbackGetAllFood;

        retrofit = new Retrofit.Builder()
                .baseUrl(Data.getInstance().getBaseUrl()) //ссылка на сервер
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mainApi = retrofit.create(MainApi.class);
    }

    // Перегрузка для запроса getFoodById
    public FoodCallAndCallback(CallbackGetFoodById callbackGetFoodById) {
        this.callbackGetFoodById = callbackGetFoodById;

        retrofit = new Retrofit.Builder()
                .baseUrl(Data.getInstance().getBaseUrl()) //ссылка на сервер
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mainApi = retrofit.create(MainApi.class);
    }

    public void getFoodById(int foodId) {
        Call<Food> call = mainApi.getFoodById(foodId);
        // Запуск асинхронного запроса в другом потоке
        call.enqueue(new Callback<Food>() {
            @Override
            public void onResponse(Call<Food> call, Response<Food> response) {
                if (response.isSuccessful()) {
                    Food food = response.body();
                    if (food != null) {
                        callbackGetFoodById.onFoodByIdReceived(food);
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

    public void getAllFood() {
        Call<JsonObject> call = mainApi.getAllFood(); // JsonObject из библиотеки Gson
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    if (jsonObject != null && jsonObject.has("products")) {
                        JsonArray productsArray = jsonObject.getAsJsonArray("products");
                        List<Food> allFood = new Gson().fromJson(productsArray, new TypeToken<List<Food>>() {}.getType());
                        callbackGetAllFood.onAllFoodReceived(allFood);

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

    public void getAllFood(int userId) {
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
                        List<Food> allFood = new Gson().fromJson(productsArray, new TypeToken<List<Food>>() {}.getType());
                        callbackGetAllFood.onAllFoodReceived(allFood);

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
