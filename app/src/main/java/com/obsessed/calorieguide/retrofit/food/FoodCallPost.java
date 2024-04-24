package com.obsessed.calorieguide.retrofit.food;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.retrofit.MainApi;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodCallPost {
    private String baseUrl = Data.getInstance().getBaseUrl();
    private Retrofit retrofit;
    private MainApi mainApi;

    public FoodCallPost(String ACCESS_TOKEN) {
//        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                //.addInterceptor(loggingInterceptor) // Добавление Interceptor для логирования
                .addInterceptor(chain -> {
                    Request originalRequest = chain.request();

                    // Добавляем заголовок к исходному запросу
                    Request newRequest = originalRequest.newBuilder()
                            .addHeader("Authorization", "Bearer " + ACCESS_TOKEN)
                            .build();

                    // Продолжаем выполнение запроса с добавленным заголовком
                    return chain.proceed(newRequest);
                })
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl).client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mainApi = retrofit.create(MainApi.class);
    }

    public void postFood(Food food) {
        Gson gson = new Gson();
        String json = gson.toJson(food); // Преобразуем объект Food в JSON-строку

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        // Выполняем POST-запрос на сервер
        Call<JsonObject> call = mainApi.postFood(requestBody);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.d("Call", "Response postFood is successful!");
                } else {
                    Log.d("Call", "ERROR response postFood is not successful!!!");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("Call", "ERROR in postFood Call!!! \n" + call + "\n" + t);
            }
        });
    }
}
