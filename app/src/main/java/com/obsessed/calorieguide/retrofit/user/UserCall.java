package com.obsessed.calorieguide.retrofit.user;

import android.util.Log;

import com.google.gson.JsonObject;
import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.retrofit.MainApi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserCall {
    private String baseUrl = Data.getInstance().getBaseUrl();
    private Retrofit retrofit;
    private MainApi mainApi;

    public UserCall() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Data.getInstance().getBaseUrl()) // сылка на сервер
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mainApi = retrofit.create(MainApi.class);
    }

    public UserCall(String ACCESS_TOKEN) {
        OkHttpClient client = new OkHttpClient.Builder()
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

    public Call<JsonObject> auth(AuthRequest authRequest) {
        return mainApi.auth(authRequest);
    }

    public Call<JsonObject> registerUser(RegistrationRequest registrationRequest) {
        return mainApi.registerUser(registrationRequest);
    }

    public Call<JsonObject> updateUser(int userId, RegistrationRequest registrationRequest) {
        return mainApi.updateUser(userId, registrationRequest);
    }



}
