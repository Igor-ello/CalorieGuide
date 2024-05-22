package com.obsessed.calorieguide.data.models.day;

import android.util.Log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.obsessed.calorieguide.data.local.room.IntakeListConverter;

import java.util.ArrayList;

@Entity(tableName = "day_table")
@TypeConverters({IntakeListConverter.class})
public class Day {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private ArrayList<Intake> breakfast; // Завтрак
    private ArrayList<Intake> lunch; // Обед
    private ArrayList<Intake> dinner; // Ужин

    public Day(ArrayList<Intake> breakfast, ArrayList<Intake> lunch, ArrayList<Intake> dinner) {
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
    }

    @Override
    public String toString() {
        return "Day{" +
                "id=" + id +
                ", breakfast=" + breakfast +
                ", lunch=" + lunch +
                ", dinner=" + dinner +
                '}';
    }

    public int posInArray(Object obj, String type) {
        Log.d("args", type + " " + obj.toString());
        if (type.equals("breakfast")) {
            Log.d("args", breakfast.size() + "");
            return breakfast.indexOf(obj);
        } else if (type.equals("lunch")) {
            return lunch.indexOf(obj);
        } else if (type.equals("dinner")) {
            return dinner.indexOf(obj);
        }
        return -1;
    }

    public ArrayList<Intake> getAllArray() {
        ArrayList<Intake> allArray = new ArrayList<>();
        allArray.addAll(breakfast);
        allArray.addAll(lunch);
        allArray.addAll(dinner);
        return allArray;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    // Add
    public void addBreakfast(Intake breakfast) {
        if (this.breakfast == null) {
            this.breakfast = new ArrayList<>();
        }
        this.breakfast.add(breakfast);
    }
    public void addLunch(Intake lunch) {
        if (this.lunch == null) {
            this.lunch = new ArrayList<>();
        }
        this.lunch.add(lunch);
    }
    public void addDinner(Intake dinner) {
        if (this.dinner == null) {
            this.dinner = new ArrayList<>();
        }
        this.dinner.add(dinner);
    }

    // Delete
    public void deleteByIdBreakfast(int id) {
        this.breakfast.remove(id);
    }
    public void deleteByIdLunch(int id) {
        this.lunch.remove(id);
    }
    public void deleteByIdDinner(int id) {
        this.dinner.remove(id);
    }

    // Get
    public ArrayList<Intake> getBreakfast() {
        return breakfast;
    }
    public void setBreakfast(ArrayList<Intake> breakfast) {
        this.breakfast = breakfast;
    }

    public ArrayList<Intake> getLunch() {
        return lunch;
    }
    public void setLunch(ArrayList<Intake> lunch) {
        this.lunch = lunch;
    }

    public ArrayList<Intake> getDinner() {
        return dinner;
    }
    public void setDinner(ArrayList<Intake> dinner) {
        this.dinner = dinner;
    }


}