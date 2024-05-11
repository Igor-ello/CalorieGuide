package com.obsessed.calorieguide.retrofit.meal.callbacks;

import com.obsessed.calorieguide.retrofit.meal.Meal;

import java.util.List;

public interface CallbackGetAllMeal {
    void onAllMealReceived(List<Meal> mealList);
}
