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
import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.data.remote.network.user.RegistrationRequest;
import com.obsessed.calorieguide.data.remote.network.user.UserCall;

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
        EditText etCalories = view.findViewById(R.id.etCalories);
        EditText etCarbs = view.findViewById(R.id.etCarbs);
        EditText etProteins = view.findViewById(R.id.etProteins);
        EditText etFats = view.findViewById(R.id.etFats);

        view.findViewById(R.id.btSend).setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String surname = etSurname.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
			int calories, carbs, proteins, fats;
			try {
				calories = Integer.parseInt(etCalories.getText().toString().trim());
				carbs = Integer.parseInt(etCarbs.getText().toString().trim());
				proteins = Integer.parseInt(etProteins.getText().toString().trim());
				fats = Integer.parseInt(etFats.getText().toString().trim());
			} catch (NumberFormatException e) {
				Toast.makeText(getContext(), "Please, enter correct numbers", Toast.LENGTH_SHORT).show();
				return;
			}
            if (password.length() < 6) {
                Toast.makeText(getContext(), "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
                return;
            }
            if (name.isEmpty() || email.isEmpty() || surname.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
            } else {
                registerRequest(view, name, surname, email, password, calories, carbs, proteins, fats);
            }
        });
    }

    private void registerRequest(View view, String name, String surname, String email, String password, int calories, int carbs, int proteins, int fats) {
        RegistrationRequest registerRequest = new RegistrationRequest(name, surname, email, password,
                calories, carbs, proteins, fats);
        UserCall userCall = new UserCall();
        userCall.registerUser(registerRequest);
        Toast.makeText(requireContext(), "Successful!", Toast.LENGTH_SHORT).show();
        Navigation.findNavController(view).popBackStack();
    }
}
