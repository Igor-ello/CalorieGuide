package com.obsessed.calorieguide.data.repository;

import android.os.Handler;
import android.os.Looper;

import com.obsessed.calorieguide.data.local.dao.FoodDao;
import com.obsessed.calorieguide.data.models.Food;
import com.obsessed.calorieguide.data.remote.network.food.FoodCall;
import com.obsessed.calorieguide.data.remote.network.food.callbacks.CallbackGetAllFood;
import com.obsessed.calorieguide.tools.Data;

public class FoodRepo {
    private final FoodDao foodDao;
    private Runnable updateRunnable;
    private final Handler handler;

    public FoodRepo(FoodDao foodDao) {
        this.foodDao = foodDao;
        handler = new Handler(Looper.getMainLooper());
    }

    public void refreshFood(CallbackGetAllFood callback) {
        updateRunnable = () -> {
            FoodCall call = new FoodCall();
            if (Data.getInstance().getUser() != null)
                call.getAllFood(Data.getInstance().getUser().getId(), callback);
            else call.getAllFood(callback);
        };
        handler.post(updateRunnable);
    }

    private void insertFood(Food food) {
        // Вставляем данные о еде в локальную базу данных
        foodDao.insert(food);
    }
}

