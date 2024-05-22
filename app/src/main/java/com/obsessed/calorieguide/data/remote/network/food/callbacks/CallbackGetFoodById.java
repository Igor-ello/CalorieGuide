package com.obsessed.calorieguide.data.remote.network.food.callbacks;

import com.obsessed.calorieguide.data.models.food.Food;

public interface CallbackGetFoodById {
    void onFoodByIdReceived(Food food);
}
