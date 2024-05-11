package com.obsessed.calorieguide.fragments.library;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.adapters.meal.MealAdapter;
import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.databinding.FragmentMealLibraryBinding;
import com.obsessed.calorieguide.retrofit.meal.callbacks.CallbackLikeMeal;
import com.obsessed.calorieguide.retrofit.meal.Meal;
import com.obsessed.calorieguide.retrofit.meal.MealCall;

import java.util.ArrayList;
import java.util.List;

public class AllMealReceived {
    Context context;
    View view;
    FragmentMealLibraryBinding binding;
    ArrayList<Meal> mealList;
    CallbackLikeMeal callback;

    public AllMealReceived(Context context, View view, FragmentMealLibraryBinding binding, ArrayList<Meal> mealList, CallbackLikeMeal callback) {
        this.context = context;
        this.view = view;
        this.binding = binding;
        this.mealList = mealList;
        this.callback = callback;
    }

    public void onAllMealReceived() {
        MealAdapter mealAdapter = new MealAdapter(mealList);
        binding.rcView.setLayoutManager(new GridLayoutManager(context, 1));
        binding.rcView.setAdapter(mealAdapter);

        // Установка слушателя в адаптере
        mealAdapter.setOnMealClickListener(meal -> {
            Log.d("IntakeAdapter", "Clicked on meal in IntakeAdapter: " + meal.getMealName());
            Bundle args = new Bundle();
            args.putInt("meal_id", meal.getId());
            Navigation.findNavController(view).navigate(R.id.action_libraryMealFragment_to_editMealFragment, args);
        });

        mealAdapter.setOnLikeMealClickListener((meal, imageView) -> {
            Log.d("IntakeAdapter", "Clicked on like for meal in IntakeAdapter: " + meal.getMealName());
            MealCall mealCall = new MealCall(Data.getInstance().getUser().getBearerToken());
            mealCall.likeMeal(Data.getInstance().getUser().getId(), meal, imageView, callback);
        });
    }
}
