package com.obsessed.calorieguide.data.callback.meal;

import com.obsessed.calorieguide.data.models.Meal;

public interface CallbackAddMeal {
	void onAddMealRemote(Meal meal);
	void onAddMealLocal();
}
