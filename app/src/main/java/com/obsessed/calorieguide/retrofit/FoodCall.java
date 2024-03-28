package com.obsessed.calorieguide.retrofit;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodCall {
    private String baseUrl = "https://dummyjson.com/"; // TODO поменять адрес
    private FoodCallback foodCallback; //интерфейс для возврата результата запроса

    public FoodCall(FoodCallback foodCallback) {
        this.foodCallback = foodCallback;
    }

    public void getFoodById(int foodId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FoodApi foodApi = retrofit.create(FoodApi.class);

        Call<Food> call = foodApi.getFoodById(foodId);
        // Запуск асинхронного запроса в другом потоке
        call.enqueue(new Callback<Food>() {
            @Override
            public void onResponse(Call<Food> call, Response<Food> response) {
                if (response.isSuccessful()) {
                    Food food = response.body();
                    if (food != null) { //TODO взять необходимые поля
                        // textByFoodApi = food.food_name;
                        String foodName = food.title;
                        foodCallback.onFoodReceived(foodName);
                        Log.d("MyLog", "Response is successful!");
                    } else Log.d("MyLog", "food is not null!");
                } else Log.d("MyLog", "ERROR response is not successful!!!");
            }

            @Override
            public void onFailure(Call<Food> call, Throwable t) {
                Log.d("MyLog", "ERROR in Call!!!");
            }
        });
    }
}
