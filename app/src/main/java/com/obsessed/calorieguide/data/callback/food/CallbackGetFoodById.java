package com.obsessed.calorieguide.data.callback.food;

import com.obsessed.calorieguide.data.models.food.Food;

public interface CallbackGetFoodById {
    void onFoodByIdReceived(Food food);
}
