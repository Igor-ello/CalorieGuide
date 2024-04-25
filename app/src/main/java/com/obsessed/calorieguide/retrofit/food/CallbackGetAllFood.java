package com.obsessed.calorieguide.retrofit.food;

import java.util.List;

public interface CallbackGetAllFood {
    void onAllFoodReceived(List<Food> foodList);
}
