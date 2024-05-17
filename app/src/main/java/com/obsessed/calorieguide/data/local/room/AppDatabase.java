package com.obsessed.calorieguide.data.local.room;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.obsessed.calorieguide.data.local.dao.FoodDao;
import com.obsessed.calorieguide.data.local.dao.MealDao;
import com.obsessed.calorieguide.data.local.dao.UserDao;
import com.obsessed.calorieguide.data.models.Food;
import com.obsessed.calorieguide.data.remote.network.meal.FoodIdQuantity;
import com.obsessed.calorieguide.data.models.Meal;
import com.obsessed.calorieguide.data.models.User;

@Database(entities = {Meal.class, FoodIdQuantity.class, Food.class, User.class}, version = 3)
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

