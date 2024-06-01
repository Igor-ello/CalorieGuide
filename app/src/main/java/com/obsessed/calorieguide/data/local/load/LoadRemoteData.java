package com.obsessed.calorieguide.data.local.load;

import static com.obsessed.calorieguide.data.local.Data.SORT_DATE;

import android.content.Context;

import com.obsessed.calorieguide.data.callback.food.CallbackDeleteAllFood;
import com.obsessed.calorieguide.data.callback.food.CallbackGetAllFood;
import com.obsessed.calorieguide.data.callback.food.CallbackLoadFood;
import com.obsessed.calorieguide.data.callback.meal.CallbackDeleteAllMeals;
import com.obsessed.calorieguide.data.callback.meal.CallbackLoadMeal;
import com.obsessed.calorieguide.data.local.room.AppDatabase;
import com.obsessed.calorieguide.data.models.Meal;
import com.obsessed.calorieguide.data.models.food.Food;
import com.obsessed.calorieguide.data.callback.meal.CallbackGetAllMeal;
import com.obsessed.calorieguide.data.repository.FoodRepo;
import com.obsessed.calorieguide.data.repository.MealRepo;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class LoadRemoteData implements CallbackGetAllFood, CallbackDeleteAllFood, CallbackGetAllMeal, CallbackDeleteAllMeals {
    private static LoadRemoteData instance;
    private static AppDatabase db;
    private static CallbackLoadFood callbackLoadFood;
    private static CallbackLoadMeal callbackLoadMeal;
    private static ArrayList<Food> foodListRemote;
    private static ArrayList<Meal> mealListRemote;


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
        foodListRemote = new ArrayList<>();

        FoodRepo foodRepo = new FoodRepo(db.foodDao());
        foodRepo.getAllFoodFromServer(SORT_DATE, twoDecade,this);
    }

    public void loadMeal(int twoDecade, CallbackLoadMeal callback) {
        callbackLoadMeal = callback;
        mealListRemote = new ArrayList<>();

        MealRepo mealRepo = new MealRepo(db.mealDao());
        mealRepo.refreshMeal(SORT_DATE, twoDecade, this);
    }

    @Override
    public void onAllFoodReceived(ArrayList<Food> foodList) {
        foodListRemote = foodList;
        FoodRepo repo = new FoodRepo(db.foodDao());
        repo.deleteAllFood(this);
    }

    @Override
    public void onDeleteAllFood() {
        if(foodListRemote!= null) {
            Executors.newSingleThreadExecutor().execute(() -> {
                for (Food food : foodListRemote) {
                    db.foodDao().insert(food);
                }

                if (callbackLoadFood != null) {
                    callbackLoadFood.onLoadFood(foodListRemote);
                }
            });
        }
    }

    @Override
    public void onAllMealReceived(ArrayList<Meal> mealList) {
        mealListRemote = mealList;
        MealRepo repo = new MealRepo(db.mealDao());
        repo.deleteAllMeals(this);
    }

    @Override
    public void onDeleteAllMeals() {
        if(mealListRemote != null) {
            Executors.newSingleThreadExecutor().execute(() -> {
                for (Meal meal : mealListRemote) {
                    db.mealDao().insert(meal);
                }

                if (callbackLoadMeal != null) {
                    callbackLoadMeal.onLoadMeal(mealListRemote);
                }
            });
        }
    }
}
