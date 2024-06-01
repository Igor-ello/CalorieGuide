package com.obsessed.calorieguide.data.remote.api;

import com.google.gson.JsonObject;
import com.obsessed.calorieguide.data.remote.network.user.AuthRequest;
import com.obsessed.calorieguide.data.remote.network.user.RegistrationRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApi {
    //User
    @POST("/login")
    Call<JsonObject> auth(@Body AuthRequest authRequest);

    @POST("/user")
    Call<JsonObject> registerUser(@Body RegistrationRequest registrationRequest);

    @PUT("/user/{user_id}")
    Call<JsonObject> updateUser(@Path("user_id") int userId, @Body RegistrationRequest registrationRequest);

    @DELETE("/user/{user_id}")
    Call<JsonObject> deleteUser(@Path("user_id") int userId);
}
