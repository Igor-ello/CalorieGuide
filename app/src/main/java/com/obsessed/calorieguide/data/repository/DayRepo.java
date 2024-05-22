package com.obsessed.calorieguide.data.repository;

import android.util.Log;

import com.obsessed.calorieguide.data.local.dao.DayDao;
import com.obsessed.calorieguide.data.local.dao.UserDao;
import com.obsessed.calorieguide.data.models.User;
import com.obsessed.calorieguide.data.models.day.Day;
import com.obsessed.calorieguide.data.remote.network.user.UserCall;
import com.obsessed.calorieguide.tools.Data;
import com.obsessed.calorieguide.tools.convert.FillClass;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class DayRepo {
    private DayDao dayDao;

    public DayRepo(DayDao dayDao) {
        this.dayDao = dayDao;
    }

    public void refreshDay() {
        Executors.newSingleThreadExecutor().execute(() ->{
            Log.d("DayRepo", "RefreshDay: " + Data.getInstance().getDay().toString());
            dayDao.insert(Data.getInstance().getDay());
        });
    }

    public void newDay() {
        Executors.newSingleThreadExecutor().execute(() ->{
            dayDao.insert(new Day(new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        });
    }
}
