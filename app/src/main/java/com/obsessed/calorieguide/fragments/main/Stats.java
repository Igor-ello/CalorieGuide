package com.obsessed.calorieguide.fragments.main;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.retrofit.user.User;

public class Stats {
    private static User user;
    public static void init(View view, FragmentActivity fragmentActivity) {
        user = Data.getInstance().getUser();
        initCalories(view);
        initCPF(view);
        initPrsBar(view, fragmentActivity);
    }

    private static void initCalories(View view) {
        ((TextView)view.findViewById(R.id.tvCaloriesTotal)).setText(String.valueOf(user.getCaloriesCurrent()));
        ((TextView)view.findViewById(R.id.tvCaloriesLeft)).setText(String.valueOf(user.getCaloriesGoal() - user.getCaloriesCurrent()));
        ((TextView)view.findViewById(R.id.tvCaloriesGoal)).setText(String.valueOf(user.getCaloriesGoal()));
    }

    public static void initCPF(View view) {
        ((TextView)view.findViewById(R.id.tvCarb)).setText(user.getCarbonatesCurrent() + "/" + user.getCarbonatesGoal() + "g");
        ((TextView)view.findViewById(R.id.tvProteins)).setText(user.getProteinsCurrent() + "/" + user.getProteinsGoal() + "g");
        ((TextView)view.findViewById(R.id.tvFats)).setText(user.getFatsCurrent() + "/" + user.getFatsGoal() + "g");
    }

    private static void initPrsBar(View view, FragmentActivity fragmentActivity) {
        fragmentActivity.runOnUiThread(() -> {
            try {
                ((ProgressBar)view.findViewById(R.id.prgBarCarb)).setProgress((int) (user.getProteinsCurrent() * 100 / user.getProteinsGoal()));
                ((ProgressBar)view.findViewById(R.id.prgBarFats)).setProgress((int) (user.getFatsCurrent() * 100 / user.getFatsGoal()));
                ((ProgressBar)view.findViewById(R.id.prgBarProteins)).setProgress((int) (user.getFatsCurrent() * 100 / user.getFatsGoal()));
            } catch (Exception e) {
                ((ProgressBar)view.findViewById(R.id.prgBarCarb)).setProgress((int) (0));
                ((ProgressBar)view.findViewById(R.id.prgBarFats)).setProgress((int) (0));
                ((ProgressBar)view.findViewById(R.id.prgBarProteins)).setProgress((int) (0));
                e.printStackTrace();
                Log.e("Error", e.getMessage());
            }

        });
    }
}
