package com.obsessed.calorieguide.data.repository.async_task;

import android.os.AsyncTask;

import com.obsessed.calorieguide.data.callback.meal.CallbackGetAllMeal;
import com.obsessed.calorieguide.data.local.dao.MealDao;
import com.obsessed.calorieguide.data.models.Meal;

import java.util.ArrayList;
import java.util.List;

public class GetAllMealsTask extends AsyncTask<Void, Void, List<Meal>> {
    private final MealDao mealDao;
    private final ArrayList<Meal> mealsArrayList;
    private final String sortType;
    private final int twoDecade;
    private final int userId;
    private Exception exception;
    CallbackGetAllMeal callback;

    public GetAllMealsTask(MealDao mealDao, ArrayList<Meal> mealsArrayList, String sortType, int twoDecade, int userId,  CallbackGetAllMeal callback) {
        this.mealDao = mealDao;
        this.mealsArrayList = mealsArrayList;
        this.sortType = sortType;
        this.twoDecade = twoDecade;
        this.userId = userId;
        this.callback = callback;
    }

    @Override
    protected List<Meal> doInBackground(Void... voids) {
        List<Meal> meals = null;
        int offset = (twoDecade - 1) * 20;
        int limit = 20;

        try {
            switch (sortType) {
                case "likesAsc":
                    meals = mealDao.getMealsByLikesAsc(offset, limit);
                    break;
                case "likesDesc":
                    meals = mealDao.getMealsByLikesDesc(offset, limit);
                    break;
                case "fromOldest":
                    meals = mealDao.getMealsFromOldest(offset, limit);
                    break;
                case "fromNewest":
                default:
                    meals = mealDao.getMealsFromNewest(offset, limit);
                    break;
            }

            if (userId != 0 && meals != null) {
                for (Meal meal : meals) {
                    boolean isLiked = mealDao.doesUserLikeMeal(userId, meal.getId());
                    meal.setIsLiked(isLiked);
                }
            }
        } catch (Exception e) {
            this.exception = e;
        }
        return meals;
    }

    @Override
    protected void onPostExecute(List<Meal> meals) {
        if (exception != null) {
            // Handle the error
        } else {
            mealsArrayList.addAll(meals);
            callback.onAllMealReceived(mealsArrayList);
        }
    }
}