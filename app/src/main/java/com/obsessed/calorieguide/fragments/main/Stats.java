package com.obsessed.calorieguide.fragments.main;

import android.animation.ObjectAnimator;
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
                ProgressBar prsBar = view.findViewById(R.id.prgBar);
                ProgressBar progressBarCarb = view.findViewById(R.id.prgBarCarb);
                ProgressBar progressBarProteins = view.findViewById(R.id.prgBarProteins);
                ProgressBar progressBarFats = view.findViewById(R.id.prgBarFats);

                progressBarCarb.setProgress( (int) (user.getCarbonatesCurrent() * 100 / user.getCarbonatesGoal()));
                //updateProgressAnimated(progressBarCarb, 30);
                progressBarProteins.setProgress( (int) (user.getProteinsCurrent() * 100 / user.getProteinsGoal()));
                //updateProgressAnimated(progressBarProteins, 30);
                progressBarFats.setProgress( (int) (user.getFatsCurrent() * 100 / user.getFatsGoal()));
                //updateProgressAnimated(progressBarFats, 30);

            } catch (Exception e) {
                ((ProgressBar)view.findViewById(R.id.prgBar)).setProgress((int) (0));
                ((ProgressBar)view.findViewById(R.id.prgBarCarb)).setProgress((int) (0));
                ((ProgressBar)view.findViewById(R.id.prgBarFats)).setProgress((int) (0));
                ((ProgressBar)view.findViewById(R.id.prgBarProteins)).setProgress((int) (0));
                e.printStackTrace();
                Log.e("Error", e.getMessage());
            }

        });
    }

    private static void updateProgressAnimated(ProgressBar progressBar, int newProgress) {
        int currentProgress = progressBar.getProgress();
        Log.d("Progress", "Current progress: " + currentProgress);

        // Проверяем, если новое значение прогресса отличается от текущего
        if (newProgress != currentProgress) {
            // Создаем анимацию изменения прогресса
            ObjectAnimator animator = ObjectAnimator.ofInt(progressBar, "progress", currentProgress, newProgress);
            animator.setDuration(1000);
            animator.start(); // Запускаем анимацию
        }
    }

}
