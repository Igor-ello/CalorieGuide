package com.obsessed.calorieguide.data.repository;


import com.obsessed.calorieguide.data.local.dao.UserDao;
import com.obsessed.calorieguide.data.models.User;
import com.obsessed.calorieguide.data.remote.network.user.UserCall;
import com.obsessed.calorieguide.tools.convert.FillClass;

import java.util.concurrent.Executors;


public class UserRepo {
    private UserDao userDao;

    public UserRepo(UserDao userDao) {
        this.userDao = userDao;
    }

    public void refreshUser(User user) {
        Executors.newSingleThreadExecutor().execute(() ->{
            UserCall call = new UserCall(user.getBearerToken());
            call.updateUser(user.getId(), FillClass.fillRegistrationRequest(user));

            userDao.insert(user);
        });
    }
}
