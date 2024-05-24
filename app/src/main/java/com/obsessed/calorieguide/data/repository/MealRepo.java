package com.obsessed.calorieguide.data.repository;

import android.os.Handler;
import android.os.Looper;

import com.obsessed.calorieguide.data.callback.meal.CallbackGetLikedMeals;
import com.obsessed.calorieguide.data.callback.meal.CallbackGetMealById;
import com.obsessed.calorieguide.data.callback.meal.CallbackSearchMeal;
import com.obsessed.calorieguide.data.local.dao.MealDao;
import com.obsessed.calorieguide.data.models.Meal;
import com.obsessed.calorieguide.data.models.food.Food;
import com.obsessed.calorieguide.data.remote.network.meal.MealCall;
import com.obsessed.calorieguide.data.callback.meal.CallbackGetAllMeal;
import com.obsessed.calorieguide.data.local.Data;
import com.obsessed.calorieguide.data.repository.async_task.meal.GetAllMealsTask;
import com.obsessed.calorieguide.data.repository.async_task.meal.SearchMealsTask;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class MealRepo {
    private MealDao mealDao;
    private final Handler handler;

    public MealRepo(MealDao mealDao) {
        this.mealDao = mealDao;
        handler = new Handler(Looper.getMainLooper());
    }

    public void refreshMeal(String sort, int twoDecade, CallbackGetAllMeal callback) {
        Runnable runnable = () -> {
            MealCall call = new MealCall();
            if (Data.getInstance().getUser() != null)
                call.getAllMeal(sort, Data.getInstance().getUser().getId(), 1, callback);
            else call.getAllMeal(sort, 0, twoDecade, callback);
        };
        handler.post(runnable);
    }

    public void getAllMeals(String sortType, int twoDecade, CallbackGetAllMeal callback) {
        int userId = Data.getInstance().getUser().getId();
        new GetAllMealsTask(mealDao, sortType, twoDecade, userId, callback).execute();
    }

    public void searchMeals(String word, CallbackSearchMeal callback) {
        int userId = Data.getInstance().getUser().getId();
        new SearchMealsTask(mealDao, word, userId, 1, callback).execute();
    }

    public void getMealById(int id, CallbackGetMealById callback) {
        Executors.newSingleThreadExecutor().execute(() -> {
            callback.onMealByIdReceived(mealDao.getMealById(id));
        });
    }

    public void getLikedMeals(int userId, CallbackGetLikedMeals callback) {
        Executors.newSingleThreadExecutor().execute(() -> {
            callback.onLikedMealsReceived((ArrayList<Meal>) mealDao.getLikedMeals(userId));
        });
    }
}
