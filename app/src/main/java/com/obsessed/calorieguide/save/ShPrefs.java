package com.obsessed.calorieguide.save;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.obsessed.calorieguide.retrofit.user.User;

public class ShPrefs {
    public static void saveUser(User user, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Сериализация объекта User в строку JSON
        Gson gson = new Gson();
        String userJson = gson.toJson(user);

        // Сохранение данных
        editor.putString("user", userJson);
        editor.apply();
        Log.d("ShPrefs", "SAVE: " + userJson); // Выводим данные в лог
    }

    public static User getUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("myPrefs", MODE_PRIVATE);

        // Получение данных
        String userJson = sharedPreferences.getString("user", null);

        if (userJson != null) {
            // Десериализация строки JSON обратно в объект User
            Gson gson = new Gson();
            User user = gson.fromJson(userJson, User.class);
            Log.d("ShPrefs", "GET: " + user); // Выводим данные в лог
            return user;
        } else {
            Log.d("ShPrefs", "GET: " + null); // Выводим данные в лог
            return null;
        }
    }
}
