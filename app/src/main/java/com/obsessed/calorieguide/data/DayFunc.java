package com.obsessed.calorieguide.data;

import com.obsessed.calorieguide.retrofit.food.Food;
import com.obsessed.calorieguide.retrofit.meal.Meal;

import java.util.ArrayList;

public class DayFunc {
    private static Day day = Data.getInstance().getDay();

    // Get
    public static int getCalories() {
        int sum = 0;
        for(Object obj : day.getAllArray()) {
            if (obj instanceof Food)
                sum += ((Food)obj).getCalories();
            if (obj instanceof Meal)
                sum += ((Meal)obj).getTotalCalories();
        }
        return sum;
    }

    public static int getCarbohydrates() {
        int sum = 0;
        for(Object obj : day.getAllArray()) {
            if (obj instanceof Food)
                sum += ((Food)obj).getCarbohydrates();
            if (obj instanceof Meal)
                sum += ((Meal)obj).getTotalCarbohydrates();
        }
        return sum;
    }

    public static int getProteins() {
        int sum = 0;
        for(Object obj : day.getAllArray()) {
            if (obj instanceof Food)
                sum += ((Food)obj).getProteins();
            if (obj instanceof Meal)
                sum += ((Meal)obj).getTotalProteins();
        }
        return sum;
    }

    public static int getFats() {
        int sum = 0;
        for(Object obj : day.getAllArray()) {
            if (obj instanceof Food)
                sum += ((Food)obj).getFats();
            if (obj instanceof Meal)
                sum += ((Meal)obj).getTotalFats();
        }
        return sum;
    }

}
