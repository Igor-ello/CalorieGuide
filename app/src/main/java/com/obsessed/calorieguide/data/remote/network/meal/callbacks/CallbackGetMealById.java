package com.obsessed.calorieguide.data.remote.network.meal.callbacks;

import com.obsessed.calorieguide.data.models.Meal;

public interface CallbackGetMealById {
    void onMealByIdReceived(Meal meal);
}
