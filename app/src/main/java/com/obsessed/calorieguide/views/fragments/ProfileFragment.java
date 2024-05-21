package com.obsessed.calorieguide.views.fragments;

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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonObject;
import com.obsessed.calorieguide.MainActivityApp;
import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.data.local.room.AppDatabase;
import com.obsessed.calorieguide.data.models.Food;
import com.obsessed.calorieguide.data.repository.FoodRepo;
import com.obsessed.calorieguide.data.repository.UserRepo;
import com.obsessed.calorieguide.tools.convert.FillClass;
import com.obsessed.calorieguide.tools.Data;
import com.obsessed.calorieguide.data.models.User;
import com.obsessed.calorieguide.data.remote.network.user.UserCall;
import com.obsessed.calorieguide.tools.save.ShPrefs;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    ArrayList<EditText> userParams;
    EditText etName, etSurname, etEmail, etPassword;
    EditText etCalories, etCarbs, etProteins, etFats;
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ((BottomNavigationView)((MainActivityApp) getActivity()).findViewById(R.id.bottomNV)).setVisibility(view.VISIBLE);
        return view;
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
            ShPrefs.saveData(user, Data.getInstance().getAdapterType(), requireContext());
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

        etCalories = view.findViewById(R.id.etCalories);
        etCarbs = view.findViewById(R.id.etCarbs);
        etProteins = view.findViewById(R.id.etProteins);
        etFats = view.findViewById(R.id.etFats);
    }

    private void initParams() {
        userParams = new ArrayList<>();
        userParams.add(etName);
        userParams.add(etSurname);
        userParams.add(etEmail);
        userParams.add(etPassword);

        userParams.add(etCalories);
        userParams.add(etCarbs);
        userParams.add(etProteins);
        userParams.add(etFats);
    }

    private void fillParams() {
        for(EditText et : userParams) {
            et.setFocusable(false); // Запретить редактирование
            et.setFocusableInTouchMode(false); // Запретить редактирование по клику
        }
        etName.setText(user.getUser_name());
        etSurname.setText(user.getSurname());
        etEmail.setText(user.getEmail());
        etPassword.setText(user.getPassword());

        etCalories.setText(String.valueOf(user.getCalories_goal()));
        etCarbs.setText(String.valueOf(user.getCarbohydrates_goal()));
        etProteins.setText(String.valueOf(user.getProteins_goal()));
        etFats.setText(String.valueOf(user.getFats_goal()));
    }

    private void setUserParams() {
        user.setUser_name(etName.getText().toString().trim());
        user.setSurname(etSurname.getText().toString().trim());
        user.setEmail(etEmail.getText().toString().trim());
        user.setPassword(etPassword.getText().toString().trim());

        user.setCalories_goal(Integer.parseInt(etCalories.getText().toString().trim()));
        user.setCarbohydrates_goal(Integer.parseInt(etCarbs.getText().toString().trim()));
        user.setProteins_goal(Integer.parseInt(etProteins.getText().toString().trim()));
        user.setFats_goal(Integer.parseInt(etFats.getText().toString().trim()));
    }


    private void updateUserRequest(User user) {
        AppDatabase db = AppDatabase.getInstance(requireContext());
        UserRepo userRepo = new UserRepo(db.userDao());
        userRepo.refreshUser(user);
    }
}