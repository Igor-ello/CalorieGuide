package com.obsessed.calorieguide.retrofit;

import java.util.List;

public interface FoodCallback {
    void onFoodByIdReceived(String foodName);
    void onAllFoodNameReceived(String allFood);
    void onAllFoodReceived(List<Food> foodList);
}
