package com.obsessed.calorieguide.adapters.meal.listeners;


import android.widget.ImageView;

import com.obsessed.calorieguide.retrofit.meal.Meal;

public interface OnLikeMealClickListener {
    void onLikeMealClick(Meal meal, ImageView imageView);
}

