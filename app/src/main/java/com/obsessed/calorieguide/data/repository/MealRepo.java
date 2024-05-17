package com.obsessed.calorieguide.data.repository;

import androidx.lifecycle.LiveData;

import com.obsessed.calorieguide.data.local.dao.MealDao;
import com.obsessed.calorieguide.data.models.Food;
import com.obsessed.calorieguide.data.models.Meal;
import com.obsessed.calorieguide.data.remote.api.MealApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealRepo {

    private MealDao mealDao;
    private MealApi mealApi;

    public MealRepo(MealDao mealDao, MealApi mealApi) {
        this.mealDao = mealDao;
        this.mealApi = mealApi;
    }

//    public LiveData<List<Meal>> getAllMeals() {
//        return mealDao.getAllMeals();
//    }
//
//    public LiveData<Meal> getMealById(int mealId) {
//        return mealDao.getMealById(mealId);
//    }

    public void refreshMeals() {
        // Обновление данных блюд из удаленного источника
        mealApi.getAllMealsLive(new Callback<List<Meal>>() {
            @Override
            public void onResponse(Call<List<Meal>> call, Response<List<Meal>> response) {
                if (response.isSuccessful()) {
                    List<Meal> mealList = response.body();
                    if (mealList != null) {
                        // Сохраняем полученные данные в локальную базу данных
                        for (Meal meal : mealList) {
                            insertMeal(meal);
                        }
                    }
                } else {
                    // Обработка ошибки запроса
                }
            }

            @Override
            public void onFailure(Call<List<Meal>> call, Throwable t) {
                // Обработка ошибки сети
            }
        });
    }

    private void insertMeal(Meal meal) {
        // Вставляем данные о еде в локальную базу данных
        mealDao.insert(meal);
    }
}
