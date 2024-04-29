package com.obsessed.calorieguide.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;
import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.tools.convert.FillClass;
import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.retrofit.user.User;
import com.obsessed.calorieguide.retrofit.user.UserCall;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    ArrayList<EditText> userParams;
    EditText etName, etSurname, etEmail, etPassword;
    User user;

    public ProfileFragment() {
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Объявление параметров
        init(view);
        //Заполнение массива параметров
        initParams();
        //Заполнение параметров
        fillParams();

        view.findViewById(R.id.settingsButton).setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_settingsFragment);
        });

        //Кнопки
        Button btEdit = view.findViewById(R.id.btEdit);
        Button btSave = view.findViewById(R.id.btSave);

        btEdit.setOnClickListener(v -> {
            for (EditText et : userParams) {
                et.setFocusableInTouchMode(true);
                et.setFocusable(true);
            }
            btEdit.setVisibility(View.GONE);
            btSave.setVisibility(View.VISIBLE);
        });

        btSave.setOnClickListener(v -> {
            for (EditText et : userParams) {
                et.setFocusable(false);
                et.setFocusableInTouchMode(false);
            }

            setUserParams();
            Data.getInstance().setUser(user);

            updateUserRequest(user);

            btEdit.setVisibility(View.VISIBLE);
            btSave.setVisibility(View.GONE);
        });
    }

    private void init(View view) {
        user = Data.getInstance().getUser();
        etName = view.findViewById(R.id.etName);
        etSurname = view.findViewById(R.id.etSurname);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
    }

    private void initParams() {
        userParams = new ArrayList<>();
        userParams.add(etName);
        userParams.add(etSurname);
        userParams.add(etEmail);
        userParams.add(etPassword);
    }

    private void fillParams() {
        for(EditText et : userParams) {
            et.setFocusable(false); // Запретить редактирование
            et.setFocusableInTouchMode(false); // Запретить редактирование по клику
        }
        etName.setText(user.getName());
        etSurname.setText(user.getSurname());
        etEmail.setText(user.getEmail());
        etPassword.setText(user.getPassword());
    }

    private void setUserParams() {
        user.setName(etName.getText().toString().trim());
        user.setSurname(etSurname.getText().toString().trim());
        user.setEmail(etEmail.getText().toString().trim());
        user.setPassword(etPassword.getText().toString().trim());
    }


    private void updateUserRequest(User user) {
        UserCall userCall = new UserCall(user.getBearerToken());
        Call<JsonObject> call = userCall.updateUser(user.getId(), FillClass.fillRegistrationRequest(user));

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    // Обработка успешного ответа
                    JsonObject result = response.body();
                    Log.d("Call", "Request updateUser successful");
                    // Дополнительная обработка результата
                } else {
                    // Обработка ошибочного ответа
                    Log.e("Call", "Request updateUser failed; Response: " + response.code());
                    // Возможно, что-то пошло не так, нужно обработать ошибку
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // Обработка ошибки при выполнении запроса
                Log.e("Call", "ERROR in updateUser Call: " + t.getMessage());
                // Например, отсутствие интернет-соединения или другие проблемы
            }
        });
    }
}