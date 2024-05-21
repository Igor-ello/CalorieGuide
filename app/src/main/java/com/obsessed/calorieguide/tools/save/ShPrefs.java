package com.obsessed.calorieguide.tools.save;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.obsessed.calorieguide.data.local.room.AppDatabase;
import com.obsessed.calorieguide.tools.Data;
import com.obsessed.calorieguide.data.models.User;

import java.util.concurrent.Executors;

public class ShPrefs {
    public static void saveData(User user, int adapterType, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Сохранение данных
        if (user != null) {
            editor.putInt("user_id", user.getId()); // Сохранение user_id
        }
        editor.putInt("adapter_type", adapterType); // Сохранение adapterType
        editor.apply();

        Log.d("ShPrefs", "SAVE Data"); // Выводим данные в лог
    }

    public static void loadData(Context context, CallbackLoadData callback) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("myPrefs", MODE_PRIVATE);

        // Получение данных
        int userId = sharedPreferences.getInt("user_id", -1); // Получение user
        int adapterType = sharedPreferences.getInt("adapter_type", 1); // Получение adapterType

        // Загрузка данных
        AppDatabase db = AppDatabase.getInstance(context);

        Executors.newSingleThreadExecutor().execute(() -> {
            Data.getInstance().setUser(db.userDao().getUserById(userId));
            Data.getInstance().setAdapterType(adapterType);
            callback.onLoadData();
        });

        Log.d("ShPrefs", "LOAD Data");
    }

    public static void dropData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // CLEAR DATA
        editor.putInt("user_id", -1); // Сохранение user
        editor.putInt("adapter_type", 1); // Сохранение adapterType
        editor.apply();

        loadData(context, null);

        Log.d("ShPrefs", "DROP Data"); // Выводим данные в лог
    }

}
