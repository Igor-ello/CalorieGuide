package com.obsessed.calorieguide.retrofit;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodCallPost {
    private String baseUrl = "http://95.174.92.190:8088/";
    private Retrofit retrofit;
    private FoodApi foodApi;

    public FoodCallPost() {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        foodApi = retrofit.create(FoodApi.class);
    }

    public void postFood(Food food) {
        Gson gson = new Gson();
        String json = gson.toJson(food); // Преобразуем объект Food в JSON-строку

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        // Выполняем POST-запрос на сервер
        Call<JsonObject> call = foodApi.postFood(requestBody);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.d("MyLog", "Response postFood is successful!");
                } else {
                    Log.d("MyLog", "ERROR response postFood is not successful!!!");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("MyLog", "ERROR in Call!!! \n" + call + "\n" + t);
            }
        });
    }
}
