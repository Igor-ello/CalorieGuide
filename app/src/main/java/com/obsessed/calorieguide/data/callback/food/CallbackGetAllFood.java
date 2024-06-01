package com.obsessed.calorieguide.data.callback.food;

import com.obsessed.calorieguide.data.models.food.Food;

import java.util.ArrayList;

public interface CallbackGetAllFood {
    void onAllFoodReceived(ArrayList<Food> foodList);
}
