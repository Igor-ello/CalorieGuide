package com.obsessed.calorieguide.retrofit.food;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.retrofit.MainApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodCall {
    private FoodCallback foodCallback; //интерфейс для возврата результата запроса
    private Retrofit retrofit;
    private MainApi mainApi;

    public FoodCall(FoodCallback foodCallback) {
        this.foodCallback = foodCallback;

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
                        foodCallback.onFoodByIdReceived(food);
                        Log.d("MyLog", "Response getFoodById is successful!");
                    } else Log.d("MyLog", "food is not null!");
                } else Log.d("MyLog", "ERROR response getFoodById is not successful!!!");
            }

            @Override
            public void onFailure(Call<Food> call, Throwable t) {
                Log.d("MyLog", "ERROR in Call!!!");
            }
        });
    }

    public void getAllFood(){
        Call<JsonObject> call = mainApi.getAllFood(); // JsonObject из библиотеки Gson
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    if (jsonObject != null && jsonObject.has("products")) {
                        JsonArray productsArray = jsonObject.getAsJsonArray("products");
                        List<Food> allFood = new Gson().fromJson(productsArray, new TypeToken<List<Food>>() {}.getType());
                        foodCallback.onAllFoodReceived(allFood);

                        Log.d("MyLog", "Response getAllFood is successful!");
                    } else {
                        Log.d("MyLog", "No products found in response!");
                    }
                } else {
                    Log.d("MyLog", "ERROR response getAllFood is not successful!!!");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("MyLog", "ERROR in Call!!! \n" + call + "\n" + t);
            }
        });
    }
}