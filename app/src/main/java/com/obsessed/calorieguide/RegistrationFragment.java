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
import com.obsessed.calorieguide.retrofit.user.RegistrationRequest;
import com.obsessed.calorieguide.retrofit.user.UserCall;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegistrationFragment extends Fragment {

    public RegistrationFragment() {
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
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText etName = view.findViewById(R.id.etName);
        EditText etSurname = view.findViewById(R.id.etSurname);
        EditText etEmail = view.findViewById(R.id.etEmail);
        EditText etPassword = view.findViewById(R.id.etPassword);

        view.findViewById(R.id.btSend).setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String surname = etSurname.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || surname.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
            } else {
                registerRequest(view, name, surname, email, password);
            }
        });
    }

    private void registerRequest(View view, String name, String surname, String email, String password) {
        RegistrationRequest registerRequest = new RegistrationRequest(name, surname, email, password);
        UserCall userCall = new UserCall();
        Call<JsonObject> call = userCall.registerUser(registerRequest);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.d("Call", "Authentication successful: " + response.message());
                    Toast.makeText(requireContext(), "Successful!", Toast.LENGTH_SHORT).show();

                    Navigation.findNavController(view).popBackStack();
                } else {
                    Log.d("Call", "Authentication failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("Call", "Authentication error: " + t.getMessage());
            }
        });
    }
}
