package com.obsessed.calorieguide.data.repository;

import android.os.Handler;
import android.os.Looper;

import com.obsessed.calorieguide.data.callback.meal.CallbackAddMeal;
import com.obsessed.calorieguide.data.callback.meal.CallbackDeleteAllMeals;
import com.obsessed.calorieguide.data.callback.meal.CallbackDeleteMealById;
import com.obsessed.calorieguide.data.callback.meal.CallbackGetLikedMeals;
import com.obsessed.calorieguide.data.callback.meal.CallbackGetMealById;
import com.obsessed.calorieguide.data.callback.meal.CallbackSearchMeal;
import com.obsessed.calorieguide.data.callback.meal.CallbackUpdateMeal;
import com.obsessed.calorieguide.data.local.dao.MealDao;
import com.obsessed.calorieguide.data.models.Meal;
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

    public void deleteMealById(int id, CallbackDeleteMealById callback) {
        Executors.newSingleThreadExecutor().execute(() -> {
            mealDao.deleteMealById(id);
            callback.onLocalDeleteMealById();
        });
    }

    public void deleteAllMeals(CallbackDeleteAllMeals callback) {
        Executors.newSingleThreadExecutor().execute(() -> {
            mealDao.deleteAllMeals();
            callback.onDeleteAllMeals();
        });
    }

    public void updateMeal(Meal meal, CallbackUpdateMeal callbackUpdateMeal) {
        Executors.newSingleThreadExecutor().execute(() -> {
            mealDao.updateMealById(
                    meal.getId(), meal.getMeal_name(),
                    meal.getDescription(), meal.getFoodIdQuantities().toString(),
                    meal.getTotal_calories(), meal.getTotal_fats(),
                    meal.getTotal_proteins(), meal.getTotal_carbohydrates(),
                    meal.getPicture()
            );
            callbackUpdateMeal.onMealUpdatedLocal();
        });
    }

    public void likeMeal(int foodId, boolean isLiked, int likes) {
        Executors.newSingleThreadExecutor().execute(() -> {
            mealDao.likeMealById(foodId, isLiked, likes);
        });
    }

    public void addMeal(Meal meal, CallbackAddMeal callback) {
        Executors.newSingleThreadExecutor().execute(() -> {
            mealDao.insert(meal);
            callback.onAddMealLocal();
        });
    }
}
