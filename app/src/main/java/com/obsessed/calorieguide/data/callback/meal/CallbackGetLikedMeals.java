package com.obsessed.calorieguide.data.callback.meal;

import com.obsessed.calorieguide.data.models.Meal;

import java.util.ArrayList;

public interface CallbackGetLikedMeals {
    void onLikedMealsReceived(ArrayList<Meal> mealsArrayList);
}
