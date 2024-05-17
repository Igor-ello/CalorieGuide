package com.obsessed.calorieguide.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.obsessed.calorieguide.network.meal.Meal;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MealDao {
    @Insert
    void insert(Meal meal);

    @Update
    void update(Meal meal);

    @Delete
    void delete(Meal meal);

    @Query("DELETE FROM meal_table")
    void deleteAllMeals();

    @Query("SELECT * FROM meal_table")
    List<Meal> getAllMeals();
}
