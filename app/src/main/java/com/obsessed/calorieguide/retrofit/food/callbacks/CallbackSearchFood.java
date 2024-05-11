package com.obsessed.calorieguide.retrofit.food.callbacks;

import com.obsessed.calorieguide.retrofit.food.Food;

import java.util.ArrayList;
import java.util.List;

public interface CallbackSearchFood {
    void foodSearchReceived(ArrayList<Food> foodList);
}
