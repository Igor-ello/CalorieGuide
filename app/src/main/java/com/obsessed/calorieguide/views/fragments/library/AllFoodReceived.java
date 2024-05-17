package com.obsessed.calorieguide.views.fragments.library;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.databinding.FragmentFoodLibraryBinding;
import com.obsessed.calorieguide.views.adapters.food.FoodAdapterV1;
import com.obsessed.calorieguide.views.adapters.food.FoodAdapterV2;
import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.network.food.FoodCallWithToken;
import com.obsessed.calorieguide.network.food.callbacks.CallbackLikeFood;
import com.obsessed.calorieguide.network.food.Food;

import java.util.ArrayList;

public class AllFoodReceived {
    Context context;
    View view;
    FragmentFoodLibraryBinding binding;
    ArrayList<Food> foodList;
    CallbackLikeFood callback;

    public AllFoodReceived(Context context, View view, FragmentFoodLibraryBinding binding, ArrayList<Food> foodList, CallbackLikeFood callback) {
        this.context = context;
        this.view = view;
        this.binding = binding;
        this.foodList = foodList;
        this.callback = callback;
    }

    public void onAllFoodReceived() {
        if (Data.getInstance().getAdapterType() != 2) {
            isAdapterV1();
        } else {
            isAdapterV2();
        }
    }

    private void isAdapterV1() {
        FoodAdapterV1 foodAdapter = new FoodAdapterV1(foodList);
        binding.rcView.setLayoutManager(new GridLayoutManager(context, 2));
        binding.rcView.setAdapter(foodAdapter);

        // Установка слушателя в адаптере
        foodAdapter.setOnFoodClickListener(food -> {
            Log.d("FoodAdapter", "Clicked on food in FoodAdapterV1: " + food.getFoodName());
            Bundle args = new Bundle();
            args.putInt("food_id", food.getId());
            Navigation.findNavController(view).navigate(R.id.action_libraryFoodFragment_to_editFoodFragment, args);
        });

        foodAdapter.setOnLikeFoodClickListener((food, imageView) -> {
            Log.d("FoodAdapter", "Clicked on like for food in FoodAdapterV1: " + food.getFoodName());
            FoodCallWithToken call = new FoodCallWithToken(Data.getInstance().getUser().getBearerToken());
            call.likeFood(Data.getInstance().getUser().getId(), food, imageView, callback);
        });
    }

    private void isAdapterV2() {
        FoodAdapterV2 foodAdapter = new FoodAdapterV2(foodList);
        binding.rcView.setLayoutManager(new GridLayoutManager(context, 1));
        binding.rcView.setAdapter(foodAdapter);

        // Установка слушателя в адаптере
        foodAdapter.setOnFoodClickListener(food -> {
            Log.d("FoodAdapter", "Clicked on food in FoodAdapterV2: " + food.getFoodName());
            Bundle args = new Bundle();
            args.putInt("food_id", food.getId());
            Navigation.findNavController(view).navigate(R.id.action_libraryFoodFragment_to_editFoodFragment, args);
        });

        foodAdapter.setOnLikeFoodClickListener((food, imageView)-> {
            Log.d("FoodAdapter", "Clicked on like for food in FoodAdapterV2: " + food.getFoodName());
            FoodCallWithToken call = new FoodCallWithToken(Data.getInstance().getUser().getBearerToken());
            call.likeFood(Data.getInstance().getUser().getId(), food, imageView, callback);
        });
    }

}