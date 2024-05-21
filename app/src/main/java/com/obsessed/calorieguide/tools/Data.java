package com.obsessed.calorieguide.tools;

import com.obsessed.calorieguide.data.models.day.Day;
import com.obsessed.calorieguide.data.models.User;

public class Data {
    static Data uniqueInstance = new Data();
    static final String BASE_URL = "http://95.174.92.190:8088/";
    static final int PICTURE_SIZE = 250;
    static final int QUALITY = 100;

    //For shared preference
    private Day day = null;
    private User user = null;
    private int adapterType = 1;

    private Data() {}

    public static Data getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Data();
        }
        return uniqueInstance;
    }

    public String getBaseUrl() {
        return BASE_URL;
    }

    public int getPictureSize() {
        return PICTURE_SIZE;
    }

    public int getQuality() {
        return QUALITY;
    }

    //For shared preference
    public int getAdapterType() {
        return adapterType;
    }
    public void setAdapterType(int adapterType) {
        this.adapterType = adapterType;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Day getDay() {
        return day;
    }
    public void setDay(Day day) {
        this.day = day;
    }
}
