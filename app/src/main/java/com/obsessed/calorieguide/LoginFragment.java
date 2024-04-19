package com.obsessed.calorieguide;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.retrofit.JsonToClass;
import com.obsessed.calorieguide.retrofit.user.User;
import com.obsessed.calorieguide.retrofit.user.UserCall;
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
        NavController navController = Navigation.findNavController(view);

        requireView().findViewById(R.id.btSignIn).setOnClickListener(v -> {
            authRequest(view, edEmail, edPassword);
        });

        requireView().findViewById(R.id.btRegistration).setOnClickListener(v -> {
            navController.navigate(R.id.action_loginFragment_to_registrationFragment);
        });
    }

    private void authRequest(View view, EditText edEmail, EditText edPassword) {
        String username = edEmail.getText().toString();
        String password = edPassword.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            return;
        }

        AuthRequest authRequest = new AuthRequest(username, password);
        UserCall userCall = new UserCall();
        Call<JsonObject> call = userCall.auth(authRequest);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    Log.d("MyLog", "Authentication successful: " + response.message());

                    if (jsonObject!= null) {
                        User user = JsonToClass.getUser(jsonObject);
                        Data.getInstance().setUser(user);

                        Toast.makeText(getContext(), "Welcome!", Toast.LENGTH_SHORT).show();
                        NavController navController = Navigation.findNavController(view);
                        navController.navigate(R.id.action_loginFragment_to_mainFragment);
                    }
                } else {
                    Log.d("MyLog", "Authentication failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("MyLog", "Authentication error: " + t.getMessage());
            }
        });
    }
}