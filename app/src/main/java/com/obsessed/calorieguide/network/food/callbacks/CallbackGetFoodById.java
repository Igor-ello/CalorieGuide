package com.obsessed.calorieguide.network.food.callbacks;

import com.obsessed.calorieguide.network.food.Food;

public interface CallbackGetFoodById {
    void onFoodByIdReceived(Food food);
}
