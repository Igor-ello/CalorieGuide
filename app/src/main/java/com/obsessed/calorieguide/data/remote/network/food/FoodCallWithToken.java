package com.obsessed.calorieguide.data.remote.network.food;

import android.util.Log;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.obsessed.calorieguide.tools.Data;
import com.obsessed.calorieguide.data.models.Food;
import com.obsessed.calorieguide.data.remote.api.FoodApi;
import com.obsessed.calorieguide.data.remote.network.food.callbacks.CallbackLikeFood;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodCallWithToken {
    private String baseUrl = Data.getInstance().getBaseUrl();
    private Retrofit retrofit;
    private FoodApi foodApi;


    public FoodCallWithToken(String ACCESS_TOKEN) {
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
                    Log.d("Call", "Response postFood is successful!");
                } else {
                    Log.e("Call", "ERROR response postFood is not successful; Response: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("Call", "ERROR in postFood call: " + t.getMessage());
            }
        });
    }

    public void updateFood(int foodId, Food food) {
        Gson gson = new Gson();
        String json = gson.toJson(food);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        Call<JsonObject> call = foodApi.updateFood(foodId, requestBody);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.d("Call", "Request updateFood successful.");
                } else {
                    Log.e("Call", "Request updateFood failed. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("Call", "ERROR in updateFood call: " + t.getMessage());
            }
        });
    }

    public void deleteFood(int foodId) {
        Call<JsonObject> call = foodApi.deleteFood(foodId);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.d("Call", "Request deleteFood successful.");
                } else {
                    Log.e("Call", "Request deleteFood failed. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("Call", "ERROR in deleteFood call: " + t.getMessage());
            }
        });
    }

    public void likeFood(int userId, Food food, ImageView imageView, CallbackLikeFood callback) {
        JsonObject requestObject = new JsonObject();
        requestObject.addProperty("user_id", userId);
        requestObject.addProperty("product_id", food.getId());

        // Преобразование объекта запроса в JSON-строку
        Gson gson = new Gson();
        String json = gson.toJson(requestObject);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        // Выполняем POST-запрос на сервер
        Call<JsonObject> call = foodApi.likeFood(requestBody);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {

                    food.setIsLiked(!food.getIsLiked());
                    if (callback != null) {
                        callback.onLikeFoodSuccess(imageView, food.getIsLiked());
                    }

                    Log.d("Call", "Response likeFood is successful!");
                } else {
                    Log.e("Call", "ERROR response likeFood is not successful; Response: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("Call", "ERROR in likeFood call: " + t.getMessage());
            }
        });
    }

}
