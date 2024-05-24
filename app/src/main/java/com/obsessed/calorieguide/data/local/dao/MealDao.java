package com.obsessed.calorieguide.data.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.obsessed.calorieguide.data.models.Meal;

import java.util.List;

@Dao
public interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Meal meal);

    @Update
    void update(Meal meal);

    @Delete
    void delete(Meal meal);

    @Query("DELETE FROM meal_table")
    void deleteAllMeals();

    @Query("SELECT * FROM meal_table")
    List<Meal> getAllMeal();

    @Query("SELECT * FROM meal_table WHERE id=:id")
    Meal getMealById(int id);

    @Query("SELECT * FROM meal_table ORDER BY likes ASC LIMIT :limit OFFSET :offset")
    List<Meal> getMealsByLikesAsc(int offset, int limit);

    @Query("SELECT * FROM meal_table ORDER BY likes DESC LIMIT :limit OFFSET :offset")
    List<Meal> getMealsByLikesDesc(int offset, int limit);

    @Query("SELECT * FROM meal_table ORDER BY id ASC LIMIT :limit OFFSET :offset")
    List<Meal> getMealsFromOldest(int offset, int limit);

    @Query("SELECT * FROM meal_table ORDER BY id DESC LIMIT :limit OFFSET :offset")
    List<Meal> getMealsFromNewest(int offset, int limit);

    @Query("SELECT EXISTS (SELECT 1 FROM meal_table WHERE author_id = :userId AND id = :mealId)")
    boolean doesUserLikeMeal(int userId, int mealId);

    @Query("SELECT * FROM meal_table WHERE meal_name LIKE '%' || :word || '%' ORDER BY meal_name LIKE '%' || :word || '%' DESC, likes DESC")
    List<Meal> searchMealsByName(String word);

    @Query("SELECT * FROM meal_table WHERE description LIKE '%' || :word || '%' ORDER BY description LIKE '%' || :word || '%' DESC, likes DESC")
    List<Meal> searchMealsByDescription(String word);
}
