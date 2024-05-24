package com.obsessed.calorieguide.data.local.load;

import static com.obsessed.calorieguide.data.local.Data.SORT_DATE;

import android.content.Context;

import com.obsessed.calorieguide.data.callback.food.CallbackLoadFood;
import com.obsessed.calorieguide.data.callback.meal.CallbackLoadMeal;
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
    private static AppDatabase db;
    private static CallbackLoadFood callbackLoadFood;
    private static CallbackLoadMeal callbackLoadMeal;


    public static LoadRemoteData getInstance(Context context) {
        db = AppDatabase.getInstance(context);
        if (instance == null) {
            instance = new LoadRemoteData();
        }
        return instance;
    }

    public void loadAll() {
        loadFood(1, null);
        loadMeal(1, null);
    }

    public void loadFood(int twoDecade, CallbackLoadFood callback) {
        callbackLoadFood = callback;
        FoodDao foodDao = db.foodDao();
        FoodRepo foodRepo = new FoodRepo(foodDao);
        foodRepo.refreshFood(SORT_DATE, twoDecade,this);
    }

    public void loadMeal(int twoDecade, CallbackLoadMeal callback) {
        callbackLoadMeal = callback;
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

            if (callbackLoadFood != null) {
                callbackLoadFood.onLoadFood(foodList);
            }
        });
    }

    @Override
    public void onAllMealReceived(ArrayList<Meal> mealList) {
        Executors.newSingleThreadExecutor().execute(() -> {
            for (Meal meal : mealList) {
                db.mealDao().insert(meal);
            }

            if (callbackLoadMeal != null) {
                callbackLoadMeal.onLoadMeal(mealList);
            }
        });
    }


}
