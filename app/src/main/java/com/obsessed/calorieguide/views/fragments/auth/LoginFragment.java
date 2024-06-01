package com.obsessed.calorieguide.views.fragments.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.obsessed.calorieguide.MainActivityApp;
import com.obsessed.calorieguide.MainActivityAuth;
import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.data.local.room.AppDatabase;
import com.obsessed.calorieguide.data.callback.user.CallbackUserAuth;
import com.obsessed.calorieguide.data.callback.user.CallbackRefreshUser;
import com.obsessed.calorieguide.data.repository.UserRepo;
import com.obsessed.calorieguide.data.local.Data;
import com.obsessed.calorieguide.data.models.User;
import com.obsessed.calorieguide.data.remote.network.user.UserCall;
import com.obsessed.calorieguide.data.remote.network.user.AuthRequest;
import com.obsessed.calorieguide.data.local.load.ShPrefs;


public class LoginFragment extends Fragment implements CallbackUserAuth, CallbackRefreshUser {

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
                Toast.makeText(requireContext(), "Please fill all fields!", Toast.LENGTH_SHORT).show();
            } else {
                authRequest(username, password);
            }
        });

        requireView().findViewById(R.id.btRegistration).setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registrationFragment);
        });
    }

    private void authRequest(String username, String password) {
        AuthRequest authRequest = new AuthRequest(username, password);
        UserCall call = new UserCall();
        call.authUser(authRequest, this);
    }

    @Override
    public void onUserAuthSuccess(User user) {
        if(isAdded()) {
            AppDatabase db = AppDatabase.getInstance(requireContext());
            UserRepo userRepo = new UserRepo(db.userDao());
            userRepo.refreshUser(user, this);
            ShPrefs.saveData(user, Data.getInstance().getAdapterType(), requireContext());
        }
    }

    @Override
    public void onUserAuthFailure() {
        Toast.makeText(requireContext(), "Try again!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefreshUser() {
        if (getActivity() != null && getActivity() instanceof MainActivityAuth) {
            startActivity(new Intent(getActivity(), MainActivityApp.class));
            getActivity().finish(); // Закрываем активность
        }
    }
}