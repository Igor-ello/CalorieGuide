package com.obsessed.calorieguide.data.repository;

import android.os.Handler;
import android.os.Looper;

import com.obsessed.calorieguide.data.callback.food.CallbackGetFoodById;
import com.obsessed.calorieguide.data.local.dao.FoodDao;
import com.obsessed.calorieguide.data.models.food.Food;
import com.obsessed.calorieguide.data.remote.network.food.FoodCall;
import com.obsessed.calorieguide.data.callback.food.CallbackGetAllFood;
import com.obsessed.calorieguide.data.local.Data;
import com.obsessed.calorieguide.data.repository.async_task.GetAllFoodTask;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class FoodRepo {
    private final FoodDao foodDao;
    private final Handler handler;

    public FoodRepo(FoodDao foodDao) {
        this.foodDao = foodDao;
        handler = new Handler(Looper.getMainLooper());
    }

    public void refreshFood(String sort, int twoDecade, CallbackGetAllFood callback) {
        Runnable runnable = () -> {
            FoodCall call = new FoodCall();
            if (Data.getInstance().getUser() != null)
                call.getAllFood(sort, Data.getInstance().getUser().getId(), 1, callback);
            else call.getAllFood(sort, 0, twoDecade, callback);
        };
        handler.post(runnable);
    }

    public void getAllFood(String sortType, int twoDecade, int userId, CallbackGetAllFood callback) {
        new GetAllFoodTask(foodDao, new ArrayList<>(), sortType, twoDecade, userId, callback).execute();
    }

    public void getFoodById(int id, CallbackGetFoodById callback) {
        Executors.newSingleThreadExecutor().execute(() -> {
            callback.onFoodByIdReceived(foodDao.getFoodById(id));
        });
    }
}

