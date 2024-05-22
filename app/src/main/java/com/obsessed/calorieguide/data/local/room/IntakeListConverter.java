package com.obsessed.calorieguide.data.local.room;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.obsessed.calorieguide.data.models.day.Intake;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class IntakeListConverter {

    @TypeConverter
    public static ArrayList<Intake> fromString(String value) {
        Type listType = new TypeToken<ArrayList<Intake>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(ArrayList<Intake> list) {
        return new Gson().toJson(list);
    }
}
