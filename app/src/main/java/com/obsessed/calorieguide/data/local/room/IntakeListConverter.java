package com.obsessed.calorieguide.data.local.room;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.obsessed.calorieguide.data.models.Meal;
import com.obsessed.calorieguide.data.models.day.Intake;
import com.obsessed.calorieguide.data.models.food.Food;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class IntakeListConverter {
    private static Gson gson;

    static {
        RuntimeTypeAdapterFactory<Intake> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory
                .of(Intake.class, "type") // "type" - это поле JSON, которое будет определять подтип
                .registerSubtype(Food.class, "food")
                .registerSubtype(Meal.class, "meal");

        gson = new GsonBuilder()
                .registerTypeAdapterFactory(runtimeTypeAdapterFactory)
                .create();
    }

    @TypeConverter
    public static ArrayList<Intake> fromString(String value) {
        Type listType = new TypeToken<ArrayList<Intake>>() {}.getType();
        return gson.fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<Intake> list) {
        return gson.toJson(list);
    }
}
