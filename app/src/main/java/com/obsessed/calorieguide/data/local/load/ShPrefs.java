package com.obsessed.calorieguide.data.local.load;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.obsessed.calorieguide.data.local.room.AppDatabase;
import com.obsessed.calorieguide.data.models.day.Day;
import com.obsessed.calorieguide.data.repository.DayRepo;
import com.obsessed.calorieguide.data.local.Data;
import com.obsessed.calorieguide.data.models.User;

import java.util.Calendar;
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
        editor.putInt("day_of_month", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)); // Сохранение day_of_month
        editor.putInt("adapter_type", adapterType); // Сохранение adapterType
        editor.apply();

        Log.d("ShPrefs", "SAVE Data"); // Выводим данные в лог
    }

    public static void loadData(Context context, CallbackLoadData callback) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("myPrefs", MODE_PRIVATE);

        // Получение данных
        int userId = sharedPreferences.getInt("user_id", -1); // Получение user
        int dayId = sharedPreferences.getInt("day_id", -1); // Получение day
        int savedDayOfMonth = sharedPreferences.getInt("day_of_month", -1); // Получение day_of_month
        int currentDayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int adapterType = sharedPreferences.getInt("adapter_type", 1); // Получение adapterType

        AppDatabase db = AppDatabase.getInstance(context);
        DayRepo repo = new DayRepo(db.dayDao());

        Executors.newSingleThreadExecutor().execute(() -> {
            if (savedDayOfMonth != currentDayOfMonth || dayId == -1) {
                LoadRemoteData.getInstance().loadAll(context);
                Data.getInstance().setDay(repo.getNewDay());
                Log.d("ShPrefs", "New day and load remote data");
            } else {
                Data.getInstance().setDay(repo.getLastDay());
                if (Data.getInstance().getDay() == null)
                    Log.d("SPInfo", "Day: null");
                else
                    Log.d("SPInfo", "Day: " + Data.getInstance().getDay().toString());
            }

            Log.d("SPInfo", "Start load data");
            Data.getInstance().setUser(db.userDao().getUserById(userId));
            if (Data.getInstance().getUser() == null)
                Log.d("SPInfo", "User: null");
            else
                Log.d("SPInfo", "User: " + Data.getInstance().getUser().toString());

            Data.getInstance().setAdapterType(adapterType);
            Log.d("SPInfo", "AdapterType: " + adapterType);

            if (callback != null)
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
        editor.putInt("day_of_month", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)); // Сохранение day_of_month
        editor.apply();

        loadData(context, null);

        Log.d("ShPrefs", "DROP Data"); // Выводим данные в лог
    }

}
