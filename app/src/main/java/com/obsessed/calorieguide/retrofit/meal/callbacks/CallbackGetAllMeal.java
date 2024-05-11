package com.obsessed.calorieguide.retrofit.meal.callbacks;

import com.obsessed.calorieguide.retrofit.meal.Meal;

import java.util.ArrayList;
import java.util.List;

public interface CallbackGetAllMeal {
    void onAllMealReceived(ArrayList<Meal> mealList);
}
