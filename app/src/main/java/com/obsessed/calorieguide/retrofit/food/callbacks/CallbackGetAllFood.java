package com.obsessed.calorieguide.retrofit.food.callbacks;

import com.obsessed.calorieguide.retrofit.food.Food;

import java.util.ArrayList;
import java.util.List;

public interface CallbackGetAllFood {
    void onAllFoodReceived(ArrayList<Food> foodList);
}
