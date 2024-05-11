package com.obsessed.calorieguide.retrofit.meal;

import android.util.Log;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.retrofit.MainApi;
import com.obsessed.calorieguide.retrofit.meal.callbacks.CallbackLikeMeal;
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

    public MealCall(String ACCESS_TOKEN) {
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


    public void postMeal(Meal meal) {
        Gson gson = new Gson();
        String json = gson.toJson(meal);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        Call<JsonObject> call = mainApi.postMeal(requestBody);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.d("Call", "Response postMeal is successful!");
                } else {
                    Log.e("Call", "ERROR response postMeal is not successful; Response: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("Call", "ERROR in postMeal call: " + t.getMessage());
            }
        });
    }

    public void updateMeal(int mealId, Meal meal) {
        Gson gson = new Gson();
        String json = gson.toJson(meal);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        Log.d("Call", meal.toString());

        Call<JsonObject> call = mainApi.updateMeal(mealId, requestBody);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.d("Call", "Request updateMeal successful.");
                } else {
                    Log.e("Call", "Request updateMeal failed. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("Call", "ERROR in updateMeal call: " + t.getMessage());
            }
        });
    }

    public void likeMeal(int userId, int mealId, ImageView imageView, CallbackLikeMeal callback) {
        JsonObject requestObject = new JsonObject();
        requestObject.addProperty("meal_id", mealId);
        requestObject.addProperty("user_id", userId);
        Log.d("Call", requestObject.toString());

        // Преобразование объекта запроса в JSON-строку
        Gson gson = new Gson();
        String json = gson.toJson(requestObject);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        // Выполняем POST-запрос на сервер
        Call<JsonObject> call = mainApi.likeMeal(requestBody);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {

                    if(callback!= null) {
                        callback.onLikeMealSuccess(imageView);
                    }

                    Log.d("Call", "Response likeMeal is successful!");
                } else {
                    Log.e("Call", "ERROR response likeMeal is not successful; Response: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("Call", "ERROR in likeMeal call: " + t.getMessage());
            }
        });
    }

    public void deleteMeal(int mealId) {
        Call<JsonObject> call = mainApi.deleteMeal(mealId);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.d("Call", "Request deleteMeal successful.");
                } else {
                    Log.e("Call", "Request deleteMeal failed. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("Call", "ERROR in deleteMeal call: " + t.getMessage());
            }
        });
    }

    public void searchMeal(String query, CallbackSearchMeal callback) {
        Call<JsonObject> call = mainApi.searchMeal(query);
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
}
