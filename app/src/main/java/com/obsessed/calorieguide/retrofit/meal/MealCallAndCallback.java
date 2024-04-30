package com.obsessed.calorieguide.retrofit.meal;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.retrofit.MainApi;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealCallAndCallback {
    private CallbackGetAllMeal callbackGetAllMeal;
    private CallbackGetMealById callbackGetMealById;
    private String baseUrl = Data.getInstance().getBaseUrl();
    private Retrofit retrofit;
    private MainApi mainApi;

    public void MealCall(String ACCESS_TOKEN) {
        //HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        //loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

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

    public Call<Meal> getMealById(int mealId) {

        Call<Meal> call = mainApi.getMealById(mealId);
        call.enqueue(new Callback<Meal>() {
            @Override
            public void onResponse(Call<Meal> call, Response<Meal> response) {
                if (response.isSuccessful()) {
                    Meal meal = response.body();
                    if (meal != null) {
                        callbackGetMealById.onMealByIdReceived(meal);
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
        return mainApi.getMealById(mealId);
    }


    public Call<JsonObject> getAllMeal() {
        Call<JsonObject> call = mainApi.getAllMeals();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    if (jsonObject != null && jsonObject.has("products")) {
                        JsonArray productsArray = jsonObject.getAsJsonArray("products");
                        List<Meal> allMeals = new Gson().fromJson(productsArray, new TypeToken<List<Meal>>() {}.getType());
                        callbackGetAllMeal.onAllMealReceived(allMeals);

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

        return mainApi.getAllMeals();
    }

}
