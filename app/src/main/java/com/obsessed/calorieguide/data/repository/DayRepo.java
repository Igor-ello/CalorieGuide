package com.obsessed.calorieguide.data.repository;

import android.content.Context;
import android.util.Log;

import com.obsessed.calorieguide.data.local.dao.DayDao;
import com.obsessed.calorieguide.data.models.Day;
import com.obsessed.calorieguide.data.local.Data;

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

    public void deleteDayBuId(int dayId) {
        Executors.newSingleThreadExecutor().execute(() ->{
            dayDao.deleteById(dayId);
        });
    }

    public void deleteAllDays() {
        Executors.newSingleThreadExecutor().execute(() ->{
            dayDao.deleteAllDays();
        });
    }

    public Day getNewDay() {
        dayDao.insert(new Day(new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        return getLastDay();
    }

    public Day getLastDay() {
        return dayDao.getLastDay();
    }
}
