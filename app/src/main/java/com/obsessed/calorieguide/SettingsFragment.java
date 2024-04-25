package com.obsessed.calorieguide;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.obsessed.calorieguide.convert.JsonToClass;
import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.retrofit.user.AuthRequest;
import com.obsessed.calorieguide.retrofit.user.User;
import com.obsessed.calorieguide.retrofit.user.UserCall;
import com.obsessed.calorieguide.save.ShPrefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    public SettingsFragment() {
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
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);

        view.findViewById(R.id.arrow_back).setOnClickListener(v -> {
            navController.popBackStack();
        });

        view.findViewById(R.id.btLogout).setOnClickListener(v -> {
            // При выходе из аккаунта сбрасываем данные
            ShPrefs.dropData(requireContext());
            Navigation.findNavController(view).popBackStack();
            Navigation.findNavController(view).navigate(R.id.loginFragment);
        });

        view.findViewById(R.id.btDelete).setOnClickListener(v -> {
            deleteRequest(view);
        });

        //Switch
        setSwitch(view);
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    void setSwitch(View view) {
        Switch swAdapterType = view.findViewById(R.id.swAdapterType);
        if (swAdapterType != null) {
            if (Data.getInstance().getAdapterType() == 1) {
                swAdapterType.setChecked(false);
            } else {
                swAdapterType.setChecked(true);
            }
            swAdapterType.setOnCheckedChangeListener(this);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) { // Переключатель включен
            Data.getInstance().setAdapterType(2);
            Log.d("Switch", "Switch is checked");
        } else { // Переключатель выключен
            Data.getInstance().setAdapterType(1);
            Log.d("Switch", "Switch is not checked");
        }
    }

    private void deleteRequest(View view) {
        UserCall userCall = new UserCall(Data.getInstance().getUser().getBearerToken());
        Call<JsonObject> call = userCall.deleteUser(Data.getInstance().getUser().getId());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.d("Call", "Authentication successful!");
                    ShPrefs.dropData(requireContext());
                    Navigation.findNavController(view).popBackStack();
                    Navigation.findNavController(view).navigate(R.id.loginFragment);
                } else {
                    Log.e("Call", "Delete user failed; Response: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("Call", "Delete user error: " + t.getMessage());
            }
        });
    }
}