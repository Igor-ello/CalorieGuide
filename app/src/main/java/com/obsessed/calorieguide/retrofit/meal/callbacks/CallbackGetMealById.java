package com.obsessed.calorieguide.retrofit.meal.callbacks;

import com.obsessed.calorieguide.retrofit.meal.Meal;

public interface CallbackGetMealById {
    void onMealByIdReceived(Meal meal);
}
