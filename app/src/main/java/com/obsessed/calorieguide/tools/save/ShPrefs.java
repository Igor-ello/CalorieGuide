package com.obsessed.calorieguide.tools.save;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.obsessed.calorieguide.data.local.dao.DayDao;
import com.obsessed.calorieguide.data.local.room.AppDatabase;
import com.obsessed.calorieguide.data.models.day.Day;
import com.obsessed.calorieguide.data.repository.DayRepo;
import com.obsessed.calorieguide.tools.Data;
import com.obsessed.calorieguide.data.models.User;

import java.util.concurrent.Executors;

public class ShPrefs {
    public static void saveData(User user, int adapterType, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Day day = Data.getInstance().getDay();

        // Сохранение данных
        if (user != null) {
            editor.putInt("user_id", user.getId()); // Сохранение user_id
        }
        if (day != null) {
            editor.putInt("day_id", day.getId()); // Сохранение day_id
        }
        editor.putInt("adapter_type", adapterType); // Сохранение adapterType
        editor.apply();

        Log.d("ShPrefs", "SAVE Data"); // Выводим данные в лог
    }

    public static void loadData(Context context, CallbackLoadData callback) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("myPrefs", MODE_PRIVATE);

        // Получение данных
        int userId = sharedPreferences.getInt("user_id", -1); // Получение user
        int dayId = sharedPreferences.getInt("day_id", -1); // Получение day
        int adapterType = sharedPreferences.getInt("adapter_type", 1); // Получение adapterType

        // Загрузка данных
        AppDatabase db = AppDatabase.getInstance(context);
        DayRepo repo = new DayRepo(db.dayDao());
        repo.newDay();

        Executors.newSingleThreadExecutor().execute(() -> {
            Data.getInstance().setUser(db.userDao().getUserById(userId));
            if (Data.getInstance().getUser() == null)
                Log.d("SPInfo", "User: null");
            else
                Log.d("SPInfo", "User: " + db.userDao().getUserById(userId).toString());

            Data.getInstance().setDay(db.dayDao().getDayById(1));
            if (Data.getInstance().getDay() == null)
                Log.d("SPInfo", "Day: null");
            else
                Log.d("SPInfo", "Day: " + db.dayDao().getDayById(1).toString());

            Data.getInstance().setAdapterType(adapterType);
            Log.d("SPInfo", "AdapterType: " + adapterType);
            callback.onLoadData();
        });

        Log.d("ShPrefs", "LOAD Data");
    }

    public static void dropData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // CLEAR DATA
        editor.putInt("user_id", -1); // Сохранение user
        editor.putInt("day_id", -1); // Сохранение day
        editor.putInt("adapter_type", 1); // Сохранение adapterType
        editor.apply();

        loadData(context, null);

        Log.d("ShPrefs", "DROP Data"); // Выводим данные в лог
    }

}
