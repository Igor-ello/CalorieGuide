package com.obsessed.calorieguide.retrofit.food;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.retrofit.MainApi;

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

public class FoodCallForAdapter {
    private CallbackGetAllFood callbackGetAllFood; //интерфейс для возврата результата запроса getAllFood
    private CallbackGetFoodById callbackGetFoodById; //интерфейс для возврата результата запроса getFoodById
    private Retrofit retrofit;
    private MainApi mainApi;
    private String baseUrl = Data.getInstance().getBaseUrl();

    // Перегрузка для запроса getAllFood
    public FoodCallForAdapter(CallbackGetAllFood callbackGetAllFood) {
        this.callbackGetAllFood = callbackGetAllFood;

        retrofit = new Retrofit.Builder()
                .baseUrl(Data.getInstance().getBaseUrl()) //ссылка на сервер
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mainApi = retrofit.create(MainApi.class);
    }

    // Перегрузка для запроса getFoodById
    public FoodCallForAdapter(CallbackGetFoodById callbackGetFoodById) {
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
                    Log.e("Call", "Request getAllFood failed. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Food> call, Throwable t) {
                Log.e("Call", "ERROR in getAllFood Call!!!");
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
                    Log.e("Call", "Request getAllFood failed. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("Call", "ERROR in getAllFood Call!!! \n" + call + "\n" + t);
            }
        });
    }

    public void updateFood(int foodId, Food food, String ACCESS_TOKEN) {
        Gson gson = new Gson();
        String json = gson.toJson(food);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    Request newRequest = originalRequest.newBuilder()
                            .addHeader("Authorization", "Bearer " + ACCESS_TOKEN)
                            .build();
                    return chain.proceed(newRequest);
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MainApi mainApi = retrofit.create(MainApi.class);

        Call<JsonObject> call = mainApi.updateProduct(foodId, requestBody);
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
                Log.e("Call", "ERROR in updateFood Call!!!", t);
            }
        });
    }
}
