package com.obsessed.calorieguide.adapters.food.listeners;

import android.widget.ImageView;

import com.obsessed.calorieguide.retrofit.food.Food;

public interface OnLikeFoodClickListener {
    void onLikeFoodClick(Food food, ImageView imageView);
}
