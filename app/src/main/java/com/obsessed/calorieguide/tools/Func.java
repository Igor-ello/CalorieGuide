package com.obsessed.calorieguide.tools;

import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.obsessed.calorieguide.R;

public class Func {
    public static void setLikeState(ImageView imageView, boolean isLiked) {
        if(imageView != null) {
            if (isLiked) {
                imageView.setImageResource(R.drawable.like_active);
            } else {
                imageView.setImageResource(R.drawable.like_not_active);
            }
        }
    }
}
