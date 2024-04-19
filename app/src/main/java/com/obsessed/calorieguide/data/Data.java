package com.obsessed.calorieguide.data;

import android.util.Log;

import com.obsessed.calorieguide.retrofit.user.User;

public class Data {
    static Data uniqueInstance = new Data();
    static final String baseUrl = "http://95.174.92.190:8088/";
    User user = null;

    private Data() {}

    public static Data getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Data();
        }
        return uniqueInstance;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        Log.d("User", user.getId() + " " + user.getName() + " " + user.getSurname()  + " " + user.getEmail() + " " + user.getPassword() + " " + user.getBearerToken());
    }
}
