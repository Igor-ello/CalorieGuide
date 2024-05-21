package com.obsessed.calorieguide.data.repository;

import android.os.Handler;
import android.os.Looper;

import com.obsessed.calorieguide.data.local.dao.MealDao;
import com.obsessed.calorieguide.data.models.Meal;
import com.obsessed.calorieguide.data.remote.network.meal.MealCall;
import com.obsessed.calorieguide.data.remote.network.meal.callbacks.CallbackGetAllMeal;
import com.obsessed.calorieguide.tools.Data;

public class MealRepo {
    private MealDao mealDao;
    private Runnable updateRunnable;
    private final Handler handler;

    public MealRepo(MealDao mealDao) {
        this.mealDao = mealDao;
        handler = new Handler(Looper.getMainLooper());
    }

    public void refreshMeal(CallbackGetAllMeal callback) {
        updateRunnable = () -> {
            MealCall call = new MealCall();
            if (Data.getInstance().getUser() != null)
                call.getAllMeal(Data.getInstance().getUser().getId(), callback);
            else call.getAllMeal(callback);
        };
        handler.post(updateRunnable);
    }

    private void insertMeal(Meal meal) {
        // Вставляем данные о еде в локальную базу данных
        mealDao.insert(meal);
    }
}
