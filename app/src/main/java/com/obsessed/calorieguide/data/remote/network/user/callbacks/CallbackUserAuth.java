package com.obsessed.calorieguide.data.remote.network.user.callbacks;

import com.obsessed.calorieguide.data.models.User;

public interface CallbackUserAuth {
    void onSuccess(User user);
    void onFailure();
}
