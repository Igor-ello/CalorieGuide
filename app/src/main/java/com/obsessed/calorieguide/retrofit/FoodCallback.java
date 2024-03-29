package com.obsessed.calorieguide.retrofit;

import java.util.List;

public interface FoodCallback {
    void onFoodByIdReceived(Food food);
    void onAllFoodReceived(List<Food> foodList);
}
