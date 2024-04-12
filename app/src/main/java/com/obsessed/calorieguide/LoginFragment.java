package com.obsessed.calorieguide;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.gson.JsonObject;
import com.obsessed.calorieguide.retrofit.user.AuthCall;
import com.obsessed.calorieguide.retrofit.user.AuthRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment {

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText edEmail = requireView().findViewById(R.id.edEmail);
        EditText edPassword = requireView().findViewById(R.id.edPassword);

        requireView().findViewById(R.id.btSignIn).setOnClickListener(v -> {
            String username = edEmail.getText().toString();
            String password = edPassword.getText().toString();

            AuthRequest authRequest = new AuthRequest(username, password);
            AuthCall authCall = new AuthCall();
            Call<JsonObject> call = authCall.auth(authRequest);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        // Обработка успешного ответа
                        JsonObject jsonObject = response.body();
                        Log.e("MyLog", "Authentication successful: " + response.message());
                        // Ваши действия с jsonObject
                    } else {
                        // Обработка ошибки аутентификации
                        Log.e("MyLog", "Authentication failed: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    // Обработка ошибки сети или других ошибок
                    Log.e("MyLog", "Authentication error: " + t.getMessage());
                }
            });

        });
    }
}