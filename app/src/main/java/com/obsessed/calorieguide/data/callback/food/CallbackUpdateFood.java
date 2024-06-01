package com.obsessed.calorieguide.data.callback.food;

import com.obsessed.calorieguide.data.models.food.Food;

public interface CallbackUpdateFood {
	void onFoodUpdatedRemote (Food food);
	void onFoodUpdatedLocal ();
}
