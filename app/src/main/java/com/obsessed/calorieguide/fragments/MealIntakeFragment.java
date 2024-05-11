package com.obsessed.calorieguide.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.obsessed.calorieguide.MainActivityApp;
import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.adapters.meal.MealIntakeAdapter;
import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.data.DayFunc;
import com.obsessed.calorieguide.data.Func;
import com.obsessed.calorieguide.databinding.FragmentMealLibraryBinding;
import com.obsessed.calorieguide.retrofit.meal.MealCall;
import com.obsessed.calorieguide.retrofit.meal.callbacks.CallbackLikeMeal;
import com.obsessed.calorieguide.retrofit.meal.callbacks.CallbackSearchMeal;
import com.obsessed.calorieguide.retrofit.meal.Meal;
import com.obsessed.calorieguide.retrofit.meal.MealCallWithToken;

import java.util.ArrayList;


public class MealIntakeFragment extends Fragment implements CallbackSearchMeal, CallbackLikeMeal {
    FragmentMealLibraryBinding binding;
    private static final String ARG_ARRAY_TYPE = "array_type";
    private String arrayType;

    public MealIntakeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!= null) {
            arrayType = getArguments().getString(ARG_ARRAY_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal_library, container, false);
        ((BottomNavigationView)((MainActivityApp) getActivity()).findViewById(R.id.bottomNV)).setVisibility(view.GONE);
        return inflater.inflate(R.layout.fragment_meal_library, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentMealLibraryBinding.bind(view);

        requireActivity().runOnUiThread(() -> {
            //TODO
        });

        binding.btToFoodLib.setVisibility(View.GONE);

        binding.searchAndAdd.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Вызывается при отправке запроса поиска (нажатии Enter или отправке формы)
                MealCall call = new MealCall();
                call.searchMeal(query, MealIntakeFragment.this);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Вызывается при изменении текста в строке поиска
                // Здесь обычно выполняется поиск по мере ввода
                // В этом примере не обрабатываем изменения текста в реальном времени
                return false;
            }
        });
    }

    @Override
    public void mealSearchReceived(ArrayList<Meal> mealList) {
        MealIntakeAdapter adapter = new MealIntakeAdapter(mealList);
        binding.rcView.setLayoutManager(new GridLayoutManager(requireContext(), 1));
        binding.rcView.setAdapter(adapter);

        // Установка слушателя в адаптере
        adapter.setOnMealClickListener(meal -> {
            Log.d("Adapter", "Clicked on meal in MealIntakeAdapter: " + meal.getMealName());
            Bundle args = new Bundle();
            args.putInt("meal_id", meal.getId());
            Navigation.findNavController(requireView()).navigate(R.id.editMealFragment, args);
        });

        adapter.setOnLikeMealClickListener((meal, imageView) -> {
            Log.d("Adapter", "Clicked on like for meal in MealIntakeAdapter: " + meal.getMealName());
            MealCallWithToken call = new MealCallWithToken(Data.getInstance().getUser().getBearerToken());
            call.likeMeal(Data.getInstance().getUser().getId(), meal, imageView, this);
        });

        adapter.setOnAddMealClickListener(meal -> {
            Log.d("Adapter", "Clicked on add for meal in MealIntakeAdapter: " + meal.getMealName());
            DayFunc.addObjectToDay(meal, arrayType);
        });
    }

    @Override
    public void onLikeMealSuccess(ImageView imageView, boolean isLiked) {
        Func.setLikeState(imageView, isLiked);
    }
}