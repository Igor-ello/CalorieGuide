package com.obsessed.calorieguide.data.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.obsessed.calorieguide.data.models.day.Day;
import com.obsessed.calorieguide.data.models.day.Intake;
import com.obsessed.calorieguide.data.models.food.Food;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface DayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Day day);

    @Update
    void update(Day day);

    @Delete
    void delete(Day day);

    @Query("DELETE FROM day_table")
    void deleteAllDays();

    @Query("SELECT * FROM day_table")
    List<Day> getAllDays();

    @Query("SELECT * FROM day_table WHERE id = :id")
    Day getDayById(int id);

//    @Query("SELECT * FROM day_table WHERE breakfast IS NOT NULL")
//    ArrayList<Intake> getBreakfast();
//
//    // Методы для работы с обедом
//    @Query("SELECT * FROM day_table WHERE lunch IS NOT NULL")
//    ArrayList<Intake> getLunch();
//
//    // Методы для работы с ужином
//    @Query("SELECT * FROM day_table WHERE dinner IS NOT NULL")
//    ArrayList<Intake> getDinner();
}
