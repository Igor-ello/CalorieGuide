package com.obsessed.calorieguide.data;

import com.obsessed.calorieguide.retrofit.user.User;

public class Data {
    static Data uniqueInstance = new Data();
    static Day day = Day.getInstance();
    static final String BASE_URL = "http://95.174.92.190:8088/";
    User user = null;
    int adapterType = 1;
    static final int PICTURE_SIZE = 250;
    static final int QUALITY = 100;

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

    public int getPictureSize() {
        return PICTURE_SIZE;
    }

    public int getQuality() {
        return QUALITY;
    }

    public Day getDay() {
        return day;
    }
}
