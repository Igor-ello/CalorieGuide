package com.obsessed.calorieguide.data.repository;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.obsessed.calorieguide.data.callback.meal.CallbackGetMealById;
import com.obsessed.calorieguide.data.local.dao.MealDao;
import com.obsessed.calorieguide.data.models.Meal;
import com.obsessed.calorieguide.data.remote.network.meal.MealCall;
import com.obsessed.calorieguide.data.callback.meal.CallbackGetAllMeal;
import com.obsessed.calorieguide.data.local.Data;
import com.obsessed.calorieguide.data.repository.async_task.GetAllMealsTask;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
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

    public void getAllMeals(String sortType, int twoDecade, int userId, CallbackGetAllMeal callback) {
        new GetAllMealsTask(mealDao, new ArrayList<>(), sortType, twoDecade, userId, callback).execute();
    }

    public void getMealById(int id, CallbackGetMealById callback) {
        Executors.newSingleThreadExecutor().execute(() -> {
            callback.onMealByIdReceived(mealDao.getMealById(id));
        });
    }
}
