package com.obsessed.calorieguide.data.remote.network.food.callbacks;

import android.widget.ImageView;

public interface CallbackLikeFood {
    public void onLikeFoodSuccess(ImageView imageView, boolean isLiked);
}
