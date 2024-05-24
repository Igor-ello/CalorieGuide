package com.obsessed.calorieguide.data.callback.food;

import com.obsessed.calorieguide.data.models.food.Food;

import java.util.ArrayList;

public interface CallbackSearchFood {
    void foodSearchReceived(ArrayList<Food> foodList);
}
