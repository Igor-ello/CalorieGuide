package com.obsessed.calorieguide.fragments.library;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.obsessed.calorieguide.MainActivityApp;
import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.data.Func;
import com.obsessed.calorieguide.databinding.FragmentMealLibraryBinding;
import com.obsessed.calorieguide.retrofit.meal.callbacks.CallbackGetAllMeal;
import com.obsessed.calorieguide.retrofit.meal.callbacks.CallbackLikeMeal;
import com.obsessed.calorieguide.retrofit.meal.Meal;
import com.obsessed.calorieguide.retrofit.meal.MealCall;

import java.util.List;

public class LibraryMealFragment extends Fragment implements CallbackGetAllMeal, CallbackLikeMeal {
    FragmentMealLibraryBinding binding;

    public LibraryMealFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal_library, container, false);
        ((BottomNavigationView)((MainActivityApp) getActivity()).findViewById(R.id.bottomNV)).setVisibility(view.VISIBLE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentMealLibraryBinding.bind(view);

        //Подгрузка данных
        requireActivity().runOnUiThread(() -> {
            MealCall mealCall = new MealCall();
            mealCall.getAllMeal(this);
        });

        //Кнопка для добавления нового питания
        view.findViewById(R.id.btAdd).setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireView());
            navController.navigate(R.id.action_libraryMealFragment_to_addMealFragment);
        });

        //Кнопка для перехода в food library
        view.findViewById(R.id.btToFoodLib).setOnClickListener(v -> {
           NavController navController = Navigation.findNavController(requireView());
           navController.popBackStack();
        });
    }

    @Override
    public void onAllMealReceived(List<Meal> mealList) {
        if (isAdded()) { // Проверяем, привязан ли фрагмент к активности
            new AllMealReceived(requireContext(), requireView(), binding, mealList, this)
                    .onAllMealReceived();
        }
    }

    @Override
    public void onLikeMealSuccess(ImageView imageView, boolean isLiked) {
        Func.setLikeState(imageView, isLiked);
    }
}