package com.obsessed.calorieguide.network.food.callbacks;

import com.obsessed.calorieguide.network.food.Food;

import java.util.ArrayList;

public interface CallbackSearchFood {
    void foodSearchReceived(ArrayList<Food> foodList);
}
