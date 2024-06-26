package com.obsessed.calorieguide.data.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.obsessed.calorieguide.data.models.Day;

import java.util.List;

@Dao
public interface DayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Day day);

    @Update
    void update(Day day);

    @Delete
    void delete(Day day);

    @Query("SELECT * FROM day_table ORDER BY id DESC LIMIT 1")
    Day getLastDay();

    @Query("DELETE FROM day_table WHERE id = :id")
    void deleteById(int id);

    @Query("DELETE FROM day_table")
    void deleteAllDays();

    @Query("SELECT * FROM day_table")
    List<Day> getAllDays();

    @Query("SELECT * FROM day_table WHERE id = :id")
    Day getDayById(int id);
}
