package com.obsessed.calorieguide.data.callback.meal;

import com.obsessed.calorieguide.data.models.Meal;

public interface CallbackUpdateMeal {
	void onMealUpdatedRemote(Meal meal);
	void onMealUpdatedLocal();
}
