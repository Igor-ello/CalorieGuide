package com.obsessed.calorieguide;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.obsessed.calorieguide.local.dao.FoodDao;
import com.obsessed.calorieguide.local.dao.MealDao;
import com.obsessed.calorieguide.local.dao.UserDao;
import com.obsessed.calorieguide.network.food.Food;
import com.obsessed.calorieguide.network.meal.FoodIdQuantity;
import com.obsessed.calorieguide.network.meal.Meal;
import com.obsessed.calorieguide.network.user.User;

@Database(entities = {Meal.class, FoodIdQuantity.class, Food.class, User.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract MealDao mealDao();
    public abstract FoodDao foodDao();
    public abstract UserDao userDao();


    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}

