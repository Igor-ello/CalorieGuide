package com.obsessed.calorieguide.data.remote.network.meal;

import android.util.Log;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.obsessed.calorieguide.data.callback.meal.CallbackAddMeal;
import com.obsessed.calorieguide.data.callback.meal.CallbackDeleteMealById;
import com.obsessed.calorieguide.data.callback.meal.CallbackUpdateMeal;
import com.obsessed.calorieguide.data.local.Data;
import com.obsessed.calorieguide.data.models.Meal;
import com.obsessed.calorieguide.data.remote.api.MealApi;
import com.obsessed.calorieguide.data.callback.meal.CallbackLikeMeal;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealCallWithToken {
    private String baseUrl = Data.getInstance().getBaseUrl();
    private Retrofit retrofit;
    private MealApi mealApi;

    public MealCallWithToken(String ACCESS_TOKEN) {
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

        mealApi = retrofit.create(MealApi.class);
    }


    public void postMeal(Meal meal, CallbackAddMeal callback) {
        Gson gson = new Gson();
        String json = gson.toJson(meal);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        Call<JsonObject> call = mealApi.postMeal(requestBody);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    callback.onAddMealRemote(gson.fromJson(response.body(), Meal.class));
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

    public void updateMeal(int mealId, Meal meal, CallbackUpdateMeal callback) {
        Gson gson = new Gson();
        String json = gson.toJson(meal);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        Log.d("Call", meal.toString());

        Call<JsonObject> call = mealApi.updateMeal(mealId, requestBody);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    callback.onMealUpdatedRemote(gson.fromJson(response.body(), Meal.class));
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

    public void likeMeal(int userId, Meal meal, ImageView imageView, CallbackLikeMeal callback) {
        JsonObject requestObject = new JsonObject();
        requestObject.addProperty("meal_id", meal.getId());
        requestObject.addProperty("user_id", userId);

        // Преобразование объекта запроса в JSON-строку
        Gson gson = new Gson();
        String json = gson.toJson(requestObject);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        // Выполняем POST-запрос на сервер
        Call<JsonObject> call = mealApi.likeMeal(requestBody);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {

                    meal.setIsLiked(!meal.getIsLiked());
                    if(callback!= null) {
                        int likes;
                        if (meal.getIsLiked())
                            likes = meal.getLikes() + 1;
                        else
                            likes = meal.getLikes()  -  1;
                        callback.onLikeMealSuccess(imageView, meal.getIsLiked(), meal.getId(), likes);
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

    public void deleteMealById(int mealId, CallbackDeleteMealById callback) {
        Call<JsonObject> call = mealApi.deleteMeal(mealId);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (callback != null) {
                        callback.onRemoteDeleteMealById();
                    }
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
}
