package com.obsessed.calorieguide.network.food.callbacks;

import com.obsessed.calorieguide.network.food.Food;

import java.util.ArrayList;

public interface CallbackGetAllFood {
    void onAllFoodReceived(ArrayList<Food> foodList);
}
