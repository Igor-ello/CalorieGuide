package com.obsessed.calorieguide.fragments.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.obsessed.calorieguide.MainActivityApp;
import com.obsessed.calorieguide.MainActivityAuth;
import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.databinding.FragmentMainBinding;
import com.obsessed.calorieguide.retrofit.meal.CallbackGetAllMeal;
import com.obsessed.calorieguide.retrofit.meal.CallbackGetMealById;
import com.obsessed.calorieguide.retrofit.meal.Meal;
import com.obsessed.calorieguide.retrofit.meal.MealCallAndCallback;
import com.obsessed.calorieguide.tools.save.ShPrefs;

import java.util.ArrayList;
import java.util.List;


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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Загрузка данных из хранилища
        ShPrefs.loadData(requireContext());

        // Проверка пользователя на наличие авторизации
        checkUserLogin(view);

        initView(view);
        Stats.init(view, requireActivity());

        //Подгрузка данных
        requireActivity().runOnUiThread(() -> {
            int mealId = 2;
            MealCallAndCallback mealCallAndCallback = new MealCallAndCallback(this, Data.getInstance().getUser().getBearerToken());
            mealCallAndCallback.getMealById(mealId);
        });
    }

    @SuppressLint("SetTextI18n")
    void initView(View view) {
        binding = FragmentMainBinding.bind(view);
        //((TextView)view.findViewById(R.id.tvUserName)).setText(Data.getInstance().getUser().getName() + "!");
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
        ArrayList<Object> objArrayList = new ArrayList<>();
        objArrayList.add(meal);
        objArrayList.add(meal);
        objArrayList.add(meal);
        objArrayList.add(meal);
        objArrayList.add(meal);
        Intakes.intakes(binding, requireContext(), requireView(), objArrayList);
    }
}