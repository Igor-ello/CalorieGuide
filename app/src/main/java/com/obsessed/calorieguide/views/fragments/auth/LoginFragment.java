package com.obsessed.calorieguide.views.fragments.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.obsessed.calorieguide.MainActivityAuth;
import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.tools.convert.JsonToClass;
import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.network.user.User;
import com.obsessed.calorieguide.network.user.UserCall;
import com.obsessed.calorieguide.network.user.AuthRequest;
import com.obsessed.calorieguide.tools.save.ShPrefs;

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

        //Инициализация переменных
        EditText edEmail = requireView().findViewById(R.id.edEmail);
        EditText edPassword = requireView().findViewById(R.id.edPassword);

        requireView().findViewById(R.id.btSignIn).setOnClickListener(v -> {
            String username = edEmail.getText().toString().trim();
            String password = edPassword.getText().toString().trim();

            if(username.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Введите все данные", Toast.LENGTH_SHORT).show();
            } else {
                authRequest(view, username, password);
            }
        });

        requireView().findViewById(R.id.btRegistration).setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registrationFragment);
        });
    }

    private void authRequest(View view, String username, String password) {
        AuthRequest authRequest = new AuthRequest(username, password);
        UserCall userCall = new UserCall();
        Call<JsonObject> call = userCall.authUser(authRequest);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    Log.d("Call", "Authentication successful");

                    if (jsonObject!= null) {
                        User user = JsonToClass.getUser(jsonObject);
                        ShPrefs.saveData(user, Data.getInstance().getAdapterType(), requireContext());

                        Toast.makeText(requireContext(), "Welcome!", Toast.LENGTH_SHORT).show();

                        // Вернуться на главную activity
                        if (getActivity() != null && getActivity() instanceof MainActivityAuth) {
                            getActivity().finish(); // Закрываем активность
                        }
                    }
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
}