package com.obsessed.calorieguide.tools;

import android.util.Log;

import com.obsessed.calorieguide.data.models.day.Day;
import com.obsessed.calorieguide.data.models.day.Intake;
import com.obsessed.calorieguide.data.models.food.Food;
import com.obsessed.calorieguide.data.models.Meal;

public class DayFunc {
    private static Day day;

    public static void addObjectToDay(Intake object, String arrayType) {
        day = Data.getInstance().getDay();
        if (arrayType.equals("breakfast")) {
            day.addBreakfast(object);
            Log.d("DayFunc", "Add to breakfast");
        } else if (arrayType.equals("lunch")) {
            day.addLunch(object);
            Log.d("DayFunc", "Add to lunch");
        } else if (arrayType.equals("dinner")) {
            day.addDinner(object);
            Log.d("DayFunc", "Add to dinner");
        } else Log.d("DayFunc", "Invalid array type");
    }

    public static void deleteObjectFromDay(int posInArray, String arrayType) {
        day = Data.getInstance().getDay();
        if (arrayType.equals("breakfast")) {
            day.deleteByIdBreakfast(posInArray);
        } else if (arrayType.equals("lunch")) {
            day.deleteByIdLunch(posInArray);
        } else if (arrayType.equals("dinner")) {
            day.deleteByIdDinner(posInArray);
        }
    }

    // Get
    public static int getCalories() {
        day = Data.getInstance().getDay();
        int sum = 0;
        for(Object obj : day.getAllArray()) {
            if (obj instanceof Food)
                sum += ((Food)obj).getCalories();
            if (obj instanceof Meal)
                sum += ((Meal)obj).getTotal_calories();
        }
        return sum;
    }

    public static int getCarbohydrates() {
        day = Data.getInstance().getDay();
        int sum = 0;
        for(Object obj : day.getAllArray()) {
            if (obj instanceof Food)
                sum += ((Food)obj).getCarbohydrates();
            if (obj instanceof Meal)
                sum += ((Meal)obj).getTotal_carbohydrates();
        }
        return sum;
    }

    public static int getProteins() {
        day = Data.getInstance().getDay();
        int sum = 0;
        for(Object obj : day.getAllArray()) {
            if (obj instanceof Food)
                sum += ((Food)obj).getProteins();
            if (obj instanceof Meal)
                sum += ((Meal)obj).getTotal_proteins();
        }
        return sum;
    }

    public static int getFats() {
        day = Data.getInstance().getDay();
        int sum = 0;
        for(Object obj : day.getAllArray()) {
            if (obj instanceof Food)
                sum += ((Food)obj).getFats();
            if (obj instanceof Meal)
                sum += ((Meal)obj).getTotal_fats();
        }
        return sum;
    }

}
