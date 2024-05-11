package com.obsessed.calorieguide.retrofit.food;

import java.util.ArrayList;
import java.util.List;

public interface CallbackSearchFood {
    void foodSearchReceived(ArrayList<Food> foodList);
}
