package com.obsessed.calorieguide.retrofit;

public interface FoodCallback {
    void onFoodByIdReceived(String foodName);
    void onAllFoodReceived(String allFood);
}
