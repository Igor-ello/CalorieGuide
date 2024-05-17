package com.obsessed.calorieguide.views.adapters.food.listeners;

import android.widget.ImageView;

import com.obsessed.calorieguide.data.models.Food;

public interface OnLikeFoodClickListener {
    void onLikeFoodClick(Food food, ImageView imageView);
}
