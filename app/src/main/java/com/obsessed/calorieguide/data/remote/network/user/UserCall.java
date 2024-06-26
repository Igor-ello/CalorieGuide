package com.obsessed.calorieguide.data.remote.network.user;

import android.util.Log;

import com.google.gson.JsonObject;
import com.obsessed.calorieguide.data.callback.user.CallbackUserAuth;
import com.obsessed.calorieguide.data.local.Data;
import com.obsessed.calorieguide.data.remote.api.UserApi;
import com.obsessed.calorieguide.tools.convert.JsonToClass;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserCall {
    private String baseUrl = Data.getInstance().getBaseUrl();
    private Retrofit retrofit;
    private UserApi userApi;
    private OkHttpClient client;

    public UserCall() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Data.getInstance().getBaseUrl()) // сылка на сервер
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userApi = retrofit.create(UserApi.class);
    }

    public UserCall(String ACCESS_TOKEN) {
        client = new OkHttpClient.Builder()
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

        userApi = retrofit.create(UserApi.class);
    }

    public void authUser(AuthRequest authRequest, CallbackUserAuth callback) {
        Call<JsonObject> call = userApi.auth(authRequest);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    Log.d("Call", "Authentication successful");

                    if (jsonObject != null) {
                        Log.d("ClInfo", "Response:" + jsonObject.toString());
                        Log.d("ClInfo", "Response before conversion:" + JsonToClass.getUser(jsonObject).toString());
                        callback.onUserAuthSuccess(JsonToClass.getUser(jsonObject));
                    }
                } else {
                    callback.onUserAuthFailure();
                    Log.e("Call", "Authentication failed; Response: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("Call", "Authentication error: " + t.getMessage());
            }
        });
    }

    public void registerUser(RegistrationRequest registrationRequest) {
        Call<JsonObject> call = userApi.registerUser(registrationRequest);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.d("Call", "Authentication successful");
                } else {
                    Log.e("Call", "Authentication failed; Response: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("Call", "Authentication error: " + t.getMessage());
            }
        });
    }

    public void updateUser(int userId, RegistrationRequest registrationRequest) {
        Call<JsonObject> call = userApi.updateUser(userId, registrationRequest);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.d("Call", "Request updateUser successful");
                } else {
                    Log.e("Call", "Request updateUser failed; Response: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("Call", "ERROR in updateUser Call: " + t.getMessage());
            }
        });
    }

    public Call<JsonObject> deleteUser(int userId) {
        Log.d("UserCall", "deleteUser: " + userId);
        return userApi.deleteUser(userId);
    }
}
