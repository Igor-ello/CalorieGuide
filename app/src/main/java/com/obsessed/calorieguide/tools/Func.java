package com.obsessed.calorieguide.tools;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

    public static void setTimeLimit(Handler handler, int delayMillis, Context context, SwipeRefreshLayout refreshLayout) {
        handler.postDelayed(() -> {
            if (refreshLayout.isRefreshing()) {
                refreshLayout.setRefreshing(false);
                Log.d("TimeLimit", "Loading took longer than 3 seconds");
                Toast.makeText(context, "Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        }, delayMillis);
    }
}
