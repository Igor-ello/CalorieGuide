package com.obsessed.calorieguide.tools;

import com.obsessed.calorieguide.data.models.Food;
import com.obsessed.calorieguide.data.models.Meal;

public class DayFunc {
    private static Day day = Data.getInstance().getDay();

    public static void addObjectToDay(Object object, String arrayType) {
        if (arrayType.equals("breakfast")) {
            day.addBreakfast(object);
        } else if (arrayType.equals("lunch")) {
            day.addLunch(object);
        } else if (arrayType.equals("dinner")) {
            day.addDinner(object);
        }
    }

    public static void deleteObjectFromDay(int posInArray, String arrayType) {
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
