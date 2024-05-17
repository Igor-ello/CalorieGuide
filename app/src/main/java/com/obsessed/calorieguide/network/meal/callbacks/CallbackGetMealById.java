package com.obsessed.calorieguide.network.meal.callbacks;

import com.obsessed.calorieguide.network.meal.Meal;

public interface CallbackGetMealById {
    void onMealByIdReceived(Meal meal);
}
