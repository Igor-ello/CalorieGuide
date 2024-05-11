package com.obsessed.calorieguide.data;

import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.obsessed.calorieguide.R;

public class Func {
    public static void setLikeState(ImageView imageView) {
        if(imageView != null) {
            if (imageView.getDrawable().getConstantState().equals(
                    ContextCompat.getDrawable(imageView.getContext(), R.drawable.like_not_active).getConstantState())) {
                imageView.setImageResource(R.drawable.like_active);
            } else {
                imageView.setImageResource(R.drawable.like_not_active);
            }
        }
    }
}
