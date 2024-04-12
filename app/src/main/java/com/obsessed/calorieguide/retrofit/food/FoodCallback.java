package com.obsessed.calorieguide.retrofit.food;

import java.util.List;

public interface FoodCallback {
    void onFoodByIdReceived(Food food);
    void onAllFoodReceived(List<Food> foodList);
}
