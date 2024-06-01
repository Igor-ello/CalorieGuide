package com.obsessed.calorieguide.data.callback.meal;

import android.widget.ImageView;

public interface CallbackLikeMeal {
    public void onLikeMealSuccess(ImageView imageView, boolean isLiked, int id, int likes);
}
