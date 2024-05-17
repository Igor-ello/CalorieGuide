package com.obsessed.calorieguide.network.meal.callbacks;

import com.obsessed.calorieguide.network.meal.Meal;

import java.util.ArrayList;

public interface CallbackGetAllMeal {
    void onAllMealReceived(ArrayList<Meal> mealList);
}
