package com.obsessed.calorieguide.data.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.obsessed.calorieguide.data.models.food.Food;

import java.util.List;

@Dao
public interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Food food);

    @Update
    void update(Food food);

    @Delete
    void delete(Food food);

    @Query("DELETE FROM food_table")
    void deleteAllFood();


    @Query("SELECT * FROM food_table")
    List<Food> getAllFood();

    @Query("SELECT * FROM food_table WHERE id = :id")
    Food getFoodById(int id);

    @Query("SELECT * FROM food_table WHERE isLiked = 1")
    List<Food> getFoodByLikes();

    @Query("SELECT * FROM food_table ORDER BY likes ASC LIMIT :limit OFFSET :offset")
    List<Food> getFoodByLikesAsc(int offset, int limit);

    @Query("SELECT * FROM food_table ORDER BY likes DESC LIMIT :limit OFFSET :offset")
    List<Food> getFoodByLikesDesc(int offset, int limit);

    @Query("SELECT * FROM food_table ORDER BY id ASC LIMIT :limit OFFSET :offset")
    List<Food> getFoodFromOldest(int offset, int limit);

    @Query("SELECT * FROM food_table ORDER BY id DESC LIMIT :limit OFFSET :offset")
    List<Food> getFoodFromNewest(int offset, int limit);

    @Query("SELECT EXISTS (SELECT 1 FROM food_table WHERE id = :foodId AND isLiked = 1)")
    boolean doesUserLikeFood(int foodId);

    @Query("SELECT * FROM food_table WHERE food_name LIKE '%' || :word || '%' ORDER BY food_name LIKE '%' || :word || '%' DESC, likes DESC")
    List<Food> searchFoodByName(String word);

    @Query("SELECT * FROM food_table WHERE description LIKE '%' || :word || '%' ORDER BY description LIKE '%' || :word || '%' DESC, likes DESC")
    List<Food> searchFoodByDescription(String word);

    @Query("DELETE FROM food_table WHERE id = :foodId")
    int deleteFoodById(int foodId);

    @Query("UPDATE food_table SET food_name = :foodName, description = :description, calories = :calories, proteins = :proteins, fats = :fats, carbohydrates = :carbohydrates, picture = :picture WHERE id = :id")
    void updateFoodById(int id, String foodName, String description, int calories, int proteins, int fats, int carbohydrates, byte[] picture);

    @Query("UPDATE food_table SET isLiked = :isLiked, likes = :likes WHERE id = :id")
    void likeFoodById(int id, boolean isLiked, int likes);
}

