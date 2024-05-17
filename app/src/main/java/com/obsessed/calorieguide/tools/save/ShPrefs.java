package com.obsessed.calorieguide.tools.save;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.obsessed.calorieguide.tools.Data;
import com.obsessed.calorieguide.data.models.User;

public class ShPrefs {
    public static void saveData(User user, int adapterType, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Сериализация объекта User в строку JSON
        Gson gson = new Gson();
        String userJson = gson.toJson(user);

        // Сохранение данных
        editor.putString("user", userJson); // Сохранение user
        editor.putInt("adapterType", adapterType); // Сохранение adapterType
        editor.apply();

        Log.d("ShPrefs", "SAVE Data"); // Выводим данные в лог
    }

    public static void loadData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("myPrefs", MODE_PRIVATE);

        // Получение данных
        String userJson = sharedPreferences.getString("user", null); // Получение user
        int adapterType = sharedPreferences.getInt("adapterType", 1); // Получение adapterType

        // Десериализация строки JSON обратно в объект User
        Gson gson = new Gson();
        User user = gson.fromJson(userJson, User.class);

        // Загрузка данных
        Data.getInstance().setUser(user);
        Data.getInstance().setAdapterType(adapterType);

        Log.d("ShPrefs", "LOAD Data");
    }

    public static void dropData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Сохранение данных
        editor.putString("user", null); // Сохранение user
        editor.putInt("adapterType", 1); // Сохранение adapterType
        editor.apply();

        loadData(context);

        Log.d("ShPrefs", "DROP Data"); // Выводим данные в лог
    }

}
