package com.obsessed.calorieguide.data.remote.network.food.callbacks;

import com.obsessed.calorieguide.data.models.Food;

import java.util.ArrayList;

public interface CallbackGetAllFood {
    void onAllFoodReceived(ArrayList<Food> foodList);
}