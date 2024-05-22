package com.obsessed.calorieguide.data.repository;

import android.util.Log;

import com.obsessed.calorieguide.data.local.dao.DayDao;
import com.obsessed.calorieguide.data.models.day.Day;
import com.obsessed.calorieguide.tools.Data;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class DayRepo {
    private DayDao dayDao;

    public DayRepo(DayDao dayDao) {
        this.dayDao = dayDao;
    }

    public void refreshDay() {
        Executors.newSingleThreadExecutor().execute(() ->{
            dayDao.insert(Data.getInstance().getDay());
            Log.d("DayRepo", "RefreshDay: " + Data.getInstance().getDay().toString());
        });
    }

    public void deleteDay(int dayId) {
        Executors.newSingleThreadExecutor().execute(() ->{
            dayDao.deleteById(dayId);
        });
    }

    public void deleteAllDays() {
        Executors.newSingleThreadExecutor().execute(() ->{
            dayDao.deleteAllDays();
        });
    }

    public void newDay() {
        Executors.newSingleThreadExecutor().execute(() ->{
            dayDao.insert(new Day(new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        });
    }
}
