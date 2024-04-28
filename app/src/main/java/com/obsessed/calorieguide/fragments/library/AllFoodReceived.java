package com.obsessed.calorieguide.fragments.library;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.adapters.FoodAdapterV1;
import com.obsessed.calorieguide.adapters.FoodAdapterV2;
import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.databinding.FragmentLibraryBinding;
import com.obsessed.calorieguide.retrofit.food.Food;
import com.obsessed.calorieguide.retrofit.food.FoodCall;

import java.util.ArrayList;
import java.util.List;

public class AllFoodReceived {
    public static void onAllFoodReceived(Context context, View view, FragmentLibraryBinding binding, List<Food> foodList) {
        if (Data.getInstance().getAdapterType() != 2) {
            isAdapterV1(context, view, binding, foodList);
        } else {
            isAdapterV2(context, view, binding, foodList);
        }
    }

    private static void isAdapterV1(Context context, View view, FragmentLibraryBinding binding, List<Food> foodList) {
        FoodAdapterV1 foodAdapter = new FoodAdapterV1();
        foodAdapter.foodArrayList = (ArrayList<Food>) foodList;
        binding.rcView.setLayoutManager(new GridLayoutManager(context, 2));
        binding.rcView.setAdapter(foodAdapter);

        // Установка слушателя в адаптере
        foodAdapter.setOnFoodClickListener(food -> {
            Log.d("FoodAdapter", "Clicked on food in FoodAdapterV1: " + food.getFoodName());
            Bundle args = new Bundle();
            args.putInt("food_id", food.getId());
            Navigation.findNavController(view).navigate(R.id.action_libraryFragment_to_editFoodFragment, args);
        });

        foodAdapter.setOnLikeFoodClickListener(food -> {
            Log.d("FoodAdapter", "Clicked on like for food in FoodAdapterV1: " + food.getFoodName());
            FoodCall foodCall = new FoodCall(Data.getInstance().getUser().getBearerToken());
            foodCall.likeFood(Data.getInstance().getUser().getId(), food.getId(), view);
        });
    }

    private static void isAdapterV2(Context context, View view, FragmentLibraryBinding binding, List<Food> foodList) {
        FoodAdapterV2 foodAdapter = new FoodAdapterV2();
        foodAdapter.foodArrayList = (ArrayList<Food>) foodList;
        binding.rcView.setLayoutManager(new GridLayoutManager(context, 1));
        binding.rcView.setAdapter(foodAdapter);

        // Установка слушателя в адаптере
        foodAdapter.setOnFoodClickListener(food -> {
            Log.d("FoodAdapter", "Clicked on food in FoodAdapterV2: " + food.getFoodName());
            Bundle args = new Bundle();
            args.putInt("food_id", food.getId());
            Navigation.findNavController(view).navigate(R.id.action_libraryFragment_to_editFoodFragment, args);
        });

        foodAdapter.setOnLikeFoodClickListener(food -> {
            Log.d("FoodAdapter", "Clicked on like for food in FoodAdapterV2: " + food.getFoodName());
            FoodCall foodCall = new FoodCall(Data.getInstance().getUser().getBearerToken());
            foodCall.likeFood(Data.getInstance().getUser().getId(), food.getId(), view);
        });
    }
}
