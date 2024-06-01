package com.obsessed.calorieguide.data.callback.food;

import com.obsessed.calorieguide.data.models.food.Food;

public interface CallbackAddFood {
	void onAddFoodRemote(Food food);
	void onAddFoodLocal();
}
