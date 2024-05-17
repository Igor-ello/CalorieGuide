package com.obsessed.calorieguide.data.repository;

import androidx.lifecycle.LiveData;

import com.obsessed.calorieguide.data.local.dao.FoodDao;
import com.obsessed.calorieguide.data.models.Food;
import com.obsessed.calorieguide.data.remote.api.FoodApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodRepo {
    private final FoodDao foodDao;
    private final FoodApi foodApi;

    public FoodRepo(FoodDao foodDao, FoodApi foodApi) {
        this.foodDao = foodDao;
        this.foodApi = foodApi;
    }

    public void refreshFoodData() {
        // Получаем данные о еде из сетевого источника и сохраняем их в локальную базу данных
        foodApi.getAllFoodLive(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                if (response.isSuccessful()) {
                    List<Food> foodList = response.body();
                    if (foodList != null) {
                        // Сохраняем полученные данные в локальную базу данных
                        for (Food food : foodList) {
                            insertFood(food);
                        }
                    }
                } else {
                    // Обработка ошибки запроса
                }
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                // Обработка ошибки сети
            }
        });
    }

    private void insertFood(Food food) {
        // Вставляем данные о еде в локальную базу данных
        foodDao.insert(food);
    }
}

