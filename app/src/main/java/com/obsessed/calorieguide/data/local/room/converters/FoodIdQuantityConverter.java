package com.obsessed.calorieguide.data.local.room.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.obsessed.calorieguide.data.models.food.FoodIdQuantity;

import java.lang.reflect.Type;
import java.util.List;

public class FoodIdQuantityConverter {
    @TypeConverter
    public static List<FoodIdQuantity> fromString(String value) {
        Type listType = new TypeToken<List<FoodIdQuantity>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<FoodIdQuantity> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
