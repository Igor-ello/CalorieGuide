package com.obsessed.calorieguide.fragments.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.obsessed.calorieguide.MainActivityApp;
import com.obsessed.calorieguide.MainActivityAuth;
import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.databinding.FragmentMainBinding;
import com.obsessed.calorieguide.retrofit.meal.callbacks.CallbackGetMealById;
import com.obsessed.calorieguide.retrofit.meal.Meal;
import com.obsessed.calorieguide.retrofit.meal.MealCall;
import com.obsessed.calorieguide.tools.save.ShPrefs;


public class MainFragment extends Fragment implements CallbackGetMealById {
    FragmentMainBinding binding;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        BottomNavigationView botNavView = ((BottomNavigationView)((MainActivityApp) getActivity()).findViewById(R.id.bottomNV));
        if (botNavView != null) {
            botNavView.setVisibility(view.VISIBLE);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentMainBinding.bind(view);

        // Загрузка данных из хранилища
        ShPrefs.loadData(requireContext());

        // Проверка пользователя на наличие авторизации
        checkUserLogin(view);

        // Инициализация для отображения данных
        Stats.getInstance().init(binding, requireActivity());

        //Подгрузка данных
        requireActivity().runOnUiThread(() -> {
            int mealId = 2;
            MealCall mealCall = new MealCall();
            mealCall.getMealById(mealId, this);
        });
    }

    private boolean checkUserLogin(View view) {
        Log.d("MainFragment", "Checking user login status");
        if (Data.getInstance().getUser() == null) {
            Log.d("MainFragment", "User not logged in, navigating to login fragment");
            if (getActivity() != null && getActivity() instanceof MainActivityApp) {
                startActivity(new Intent(getActivity(), MainActivityAuth.class));
            }
            return false;
        } else {
            Log.d("MainFragment", "User logged in");
            return true;
        }
    }

    @Override
    public void onMealByIdReceived(Meal meal) {
        Intakes.getInstance().init(binding, requireContext());
        Stats.getInstance().update();
    }
}