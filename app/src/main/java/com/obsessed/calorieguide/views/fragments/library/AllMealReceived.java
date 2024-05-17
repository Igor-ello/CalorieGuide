package com.obsessed.calorieguide.views.fragments.library;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.databinding.FragmentMealLibraryBinding;
import com.obsessed.calorieguide.views.adapters.meal.MealAdapter;
import com.obsessed.calorieguide.tools.Data;
import com.obsessed.calorieguide.data.remote.network.meal.callbacks.CallbackLikeMeal;
import com.obsessed.calorieguide.data.models.Meal;
import com.obsessed.calorieguide.data.remote.network.meal.MealCallWithToken;

import java.util.ArrayList;

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
            Log.d("IntakeAdapter", "Clicked on meal in IntakeAdapter: " + meal.getMeal_name());
            Bundle args = new Bundle();
            args.putInt("meal_id", meal.getId());
            Navigation.findNavController(view).navigate(R.id.action_libraryMealFragment_to_editMealFragment, args);
        });

        mealAdapter.setOnLikeMealClickListener((meal, imageView) -> {
            Log.d("IntakeAdapter", "Clicked on like for meal in IntakeAdapter: " + meal.getMeal_name());
            MealCallWithToken mealCallWithToken = new MealCallWithToken(Data.getInstance().getUser().getBearerToken());
            mealCallWithToken.likeMeal(Data.getInstance().getUser().getId(), meal, imageView, callback);
        });
    }
}
