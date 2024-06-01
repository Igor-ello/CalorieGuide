package com.obsessed.calorieguide.data.callback.user;

import com.obsessed.calorieguide.data.models.User;

public interface CallbackUserAuth {
    void onUserAuthSuccess(User user);
    void onUserAuthFailure();
}
