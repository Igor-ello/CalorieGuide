package com.obsessed.calorieguide.fragments.library;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.databinding.FragmentFoodLibraryBinding;
import com.obsessed.calorieguide.databinding.FragmentMealLibraryBinding;
import com.obsessed.calorieguide.retrofit.food.Food;
import com.obsessed.calorieguide.retrofit.food.FoodCallAndCallback;
import com.obsessed.calorieguide.retrofit.meal.CallbackGetAllMeal;
import com.obsessed.calorieguide.retrofit.meal.CallbackLikeMeal;
import com.obsessed.calorieguide.retrofit.meal.Meal;
import com.obsessed.calorieguide.retrofit.meal.MealCallAndCallback;

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_library, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentMealLibraryBinding.bind(view);

        //Подгрузка данных
        requireActivity().runOnUiThread(() -> {
            MealCallAndCallback mealCallAndCallback = new MealCallAndCallback(this, Data.getInstance().getUser().getBearerToken());
            mealCallAndCallback.getAllMeal();
        });

        //Кнопка для добавления нового питания
        view.findViewById(R.id.buttonAdd).setOnClickListener(v -> {
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
    public void onLikeMealSuccess(ImageView imageView) {
        if (imageView != null) {
            if (imageView.getDrawable().getConstantState().equals(
                    ContextCompat.getDrawable(imageView.getContext(), R.drawable.like_not_active).getConstantState())) {
                imageView.setImageResource(R.drawable.like_active);
            } else {
                imageView.setImageResource(R.drawable.like_not_active);
            }
        }
    }
}