package com.obsessed.calorieguide.retrofit.meal;

import java.util.List;

public interface CallbackGetAllMeal {
    void onAllMealReceived(List<Meal> mealList);
}
