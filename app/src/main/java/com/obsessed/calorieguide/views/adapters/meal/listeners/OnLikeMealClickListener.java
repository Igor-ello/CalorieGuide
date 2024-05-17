package com.obsessed.calorieguide.views.adapters.meal.listeners;


import android.widget.ImageView;

import com.obsessed.calorieguide.network.meal.Meal;

public interface OnLikeMealClickListener {
    void onLikeMealClick(Meal meal, ImageView imageView);
}

