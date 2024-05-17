package com.obsessed.calorieguide.data.remote.network.meal.callbacks;

import com.obsessed.calorieguide.data.models.Meal;

import java.util.ArrayList;

public interface CallbackGetAllMeal {
    void onAllMealReceived(ArrayList<Meal> mealList);
}
