package com.obsessed.calorieguide.data.callback.meal;

import com.obsessed.calorieguide.data.models.Meal;

public interface CallbackGetMealById {
    void onMealByIdReceived(Meal meal);
}
