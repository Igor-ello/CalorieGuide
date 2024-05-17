package com.obsessed.calorieguide.data;

import android.util.Log;

import java.util.ArrayList;

public class Day {
    private static Day uniqueInstance = new Day();
    private ArrayList<Object> breakfast; // Завтрак
    private ArrayList<Object> lunch; // Обед
    private ArrayList<Object> dinner; // Ужин

    private Day() {
        this.breakfast = new ArrayList<>();
        this.lunch = new ArrayList<>();
        this.dinner = new ArrayList<>();
    }

    public static Day getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Day();
        }
        return uniqueInstance;
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

    // Add
    public void addBreakfast(Object breakfast) {
        this.breakfast.add(breakfast);
    }
    public void addLunch(Object lunch) {
        this.lunch.add(lunch);
    }
    public void addDinner(Object dinner) {
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
    public ArrayList<Object> getBreakfast() {
        return breakfast;
    }
    public ArrayList<Object> getLunch() {
        return lunch;
    }
    public ArrayList<Object> getDinner() {
        return dinner;
    }

    public ArrayList<Object> getAllArray() {
        ArrayList<Object> allArray = new ArrayList<>();
        allArray.addAll(breakfast);
        allArray.addAll(lunch);
        allArray.addAll(dinner);
        return allArray;
    }

}