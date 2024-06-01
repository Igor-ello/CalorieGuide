package com.obsessed.calorieguide.data.callback.meal;

import com.obsessed.calorieguide.data.models.Meal;

import java.util.ArrayList;

public interface CallbackGetAllMeal {
    void onAllMealReceived(ArrayList<Meal> mealList);
}
