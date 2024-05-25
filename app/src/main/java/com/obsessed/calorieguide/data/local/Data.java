package com.obsessed.calorieguide.data.local;

import com.obsessed.calorieguide.data.models.Day;
import com.obsessed.calorieguide.data.models.User;

public class Data {
    private static Data uniqueInstance = new Data();
    public static final String BASE_URL = "http://95.174.92.190:8088/";
    public static final String SORT_LIKE_ASCENDING = "likesAsc";
    public static final String SORT_LIKE_DESCENDING = "likesDesc";
    public static final String SORT_DATE = "";
    public static final int PICTURE_SIZE = 250;
    public static final int QUALITY = 100;
    public static final int DELAY_DEFAULT = 3000;
    public static final int DELAY_FAST = 1000;
    public static final int DELAY_LONG = 6000;

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
