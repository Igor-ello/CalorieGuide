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

import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.databinding.FragmentFoodLibraryBinding;
import com.obsessed.calorieguide.retrofit.food.Food;
import com.obsessed.calorieguide.retrofit.food.FoodCallAndCallback;
import com.obsessed.calorieguide.retrofit.food.CallbackGetAllFood;

import java.util.List;

public class LibraryFoodFragment extends Fragment implements CallbackGetAllFood {
    FragmentFoodLibraryBinding binding;

    public LibraryFoodFragment() {
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
        return inflater.inflate(R.layout.fragment_food_library, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentFoodLibraryBinding.bind(view);

        //Подгрузка данных
        requireActivity().runOnUiThread(() -> {
            FoodCallAndCallback foodCallAndCallback = new FoodCallAndCallback(this);
            foodCallAndCallback.getAllFood();
        });

        //Кнопка для добавления нового фрукта
        view.findViewById(R.id.buttonAdd).setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireView());
            navController.navigate(R.id.action_libraryFoodFragment_to_addFoodFragment);
        });

        //Кнопка для перехода в meal library
        view.findViewById(R.id.btToMealLib).setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_libraryFoodFragment_to_libraryMealFragment);
        });
    }

    @Override
    public void onAllFoodReceived(List<Food> foodList) {
        if (isAdded()) { // Проверяем, привязан ли фрагмент к активности
            AllFoodReceived.onAllFoodReceived(requireContext(), requireView(), binding, foodList);
        }
    }
}