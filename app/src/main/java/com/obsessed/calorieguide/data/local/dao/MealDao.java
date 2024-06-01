package com.obsessed.calorieguide.data.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.obsessed.calorieguide.data.models.Meal;
import com.obsessed.calorieguide.data.models.food.FoodIdQuantity;

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

    @Query("SELECT * FROM meal_table WHERE isLiked = 1 AND author_id = :userId")
    List<Meal> getLikedMeals(int userId);

    @Query("DELETE FROM meal_table WHERE id = :mealId")
    int deleteMealById(int mealId);

    @Query("UPDATE meal_table SET description = :description, total_calories  =  :calories, total_proteins  =  :proteins, total_fats   =  :fats, total_carbohydrates   =  :carbohydrates, meal_name = :name, products_id = :productsId, picture = :picture WHERE id  =  :id")
    void updateMealById(int id, String name, String description, String productsId, int calories, int proteins, int fats, int carbohydrates, byte[] picture);

    @Query("UPDATE meal_table SET isLiked = :isLiked, likes = :likes WHERE id = :id")
    void likeMealById(int id, boolean isLiked, int likes);
}
