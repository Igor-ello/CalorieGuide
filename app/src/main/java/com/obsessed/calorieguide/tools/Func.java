package com.obsessed.calorieguide.tools;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.obsessed.calorieguide.R;

import java.util.List;

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

    public static void setTimeLimitRefLn(Handler handler, int delayMillis, Context context, SwipeRefreshLayout refreshLayout) {
        handler.postDelayed(() -> {
            if (refreshLayout!= null && refreshLayout.isRefreshing()) {
                refreshLayout.setRefreshing(false);
                Log.d("TimeLimit", "RefreshLayout: Loading took longer than" + delayMillis/1000 + " seconds");
                Toast.makeText(context, "Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        }, delayMillis);
    }

    public  static void setTimeLimitLoading(Handler handler, int delayMillis, Context context, View view, Activity activity) {
        handler.postDelayed(() -> {
            ConstraintLayout constraintLayout = view.findViewById(R.id.loading);
            if (constraintLayout != null && constraintLayout.isShown()) {
                //linearLayout.setVisibility(View.GONE);
                activity.runOnUiThread(() -> {
                    Navigation.findNavController(view).popBackStack();
                });
                Log.d("TimeLimit", "RefreshLayout: Loading took longer than" + delayMillis/1000 + " seconds");
                Toast.makeText(context, "Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        }, delayMillis);
    }
}
