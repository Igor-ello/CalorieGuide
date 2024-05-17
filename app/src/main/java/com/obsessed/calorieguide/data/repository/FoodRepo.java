package com.obsessed.calorieguide.data.repository;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.obsessed.calorieguide.data.local.dao.FoodDao;
import com.obsessed.calorieguide.data.local.room.AppDatabase;
import com.obsessed.calorieguide.data.models.Food;
import com.obsessed.calorieguide.data.remote.api.FoodApi;
import com.obsessed.calorieguide.data.remote.network.food.FoodCall;
import com.obsessed.calorieguide.data.remote.network.food.callbacks.CallbackGetAllFood;
import com.obsessed.calorieguide.tools.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodRepo {
    private final FoodDao foodDao;
    //private final FoodApi foodApi;
    private final Executor executor;
    private Runnable updateRunnable;
    private final Handler handler = new Handler(Looper.getMainLooper());

    public FoodRepo(FoodDao foodDao) {
        this.foodDao = foodDao;
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void startPeriodicRefresh(CallbackGetAllFood callback) {
        updateRunnable = new Runnable() {
            @Override
            public void run() {
                FoodCall call = new FoodCall();
                if (Data.getInstance().getUser() != null)
                    call.getAllFood(Data.getInstance().getUser().getId(), callback);
                else call.getAllFood(callback);
                handler.postDelayed(this, 300000); // 300000 ms = 5 minutes
            }
        };
        handler.post(updateRunnable);
    }


    private void insertFood(Food food) {
        // Вставляем данные о еде в локальную базу данных
        foodDao.insert(food);
    }
}

