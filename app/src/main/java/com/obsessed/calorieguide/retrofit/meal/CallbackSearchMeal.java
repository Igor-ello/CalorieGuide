package com.obsessed.calorieguide.retrofit.meal;

import java.util.ArrayList;
import java.util.List;

public interface CallbackSearchMeal {
    void mealSearchReceived(ArrayList<Meal> mealList);
}

