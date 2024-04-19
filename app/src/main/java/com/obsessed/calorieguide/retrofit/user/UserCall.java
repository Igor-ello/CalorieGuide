package com.obsessed.calorieguide.retrofit.user;

import com.google.gson.JsonObject;
import com.obsessed.calorieguide.retrofit.MainApi;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserCall {
    private String baseUrl = "http://95.174.92.190:8088/";
    private Retrofit retrofit;
    private MainApi mainApi;

    public UserCall() {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
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



}
