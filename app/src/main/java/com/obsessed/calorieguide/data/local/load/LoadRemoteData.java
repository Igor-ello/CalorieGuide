package com.obsessed.calorieguide.data.local.load;

import static com.obsessed.calorieguide.data.local.Data.SORT_DATE;

import android.content.Context;

import com.obsessed.calorieguide.data.local.dao.FoodDao;
import com.obsessed.calorieguide.data.local.dao.MealDao;
import com.obsessed.calorieguide.data.local.room.AppDatabase;
import com.obsessed.calorieguide.data.models.Meal;
import com.obsessed.calorieguide.data.models.food.Food;
import com.obsessed.calorieguide.data.callback.food.CallbackGetAllFood;
import com.obsessed.calorieguide.data.callback.meal.CallbackGetAllMeal;
import com.obsessed.calorieguide.data.repository.FoodRepo;
import com.obsessed.calorieguide.data.repository.MealRepo;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class LoadRemoteData implements CallbackGetAllFood, CallbackGetAllMeal{
    private static LoadRemoteData instance;
    AppDatabase db;

    public static LoadRemoteData getInstance() {
        if (instance == null) {
            instance = new LoadRemoteData();
        }
        return instance;
    }

    public void loadAll(Context context) {
        db = AppDatabase.getInstance(context);
        loadFood(1);
        loadMeal(1);
    }

    public void loadFood(int twoDecade) {
        FoodDao foodDao = db.foodDao();
        FoodRepo foodRepo = new FoodRepo(foodDao);
        foodRepo.refreshFood(SORT_DATE, twoDecade,this);
    }

    public void loadMeal(int twoDecade) {
        MealDao mealDao = db.mealDao();
        MealRepo mealRepo = new MealRepo(mealDao);
        mealRepo.refreshMeal(SORT_DATE, twoDecade, this);
    }

    @Override
    public void onAllFoodReceived(ArrayList<Food> foodList) {
        Executors.newSingleThreadExecutor().execute(() -> {
            for (Food food : foodList) {
                db.foodDao().insert(food);
            }
        });
    }

    @Override
    public void onAllMealReceived(ArrayList<Meal> mealList) {
        Executors.newSingleThreadExecutor().execute(() -> {
            for (Meal meal : mealList) {
                db.mealDao().insert(meal);
            }
        });
    }
}
