package com.obsessed.calorieguide.views.adapters.meal.listeners;


import android.widget.ImageView;

import com.obsessed.calorieguide.data.models.Meal;

public interface OnLikeMealClickListener {
    void onLikeMealClick(Meal meal, ImageView imageView);
}

