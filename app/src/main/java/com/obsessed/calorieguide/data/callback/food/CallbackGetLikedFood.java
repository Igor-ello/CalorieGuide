package com.obsessed.calorieguide.data.callback.food;

import com.obsessed.calorieguide.data.models.food.Food;

import java.util.ArrayList;

public interface CallbackGetLikedFood {
    void onLikedFoodReceived(ArrayList<Food> foodArrayList);
}
