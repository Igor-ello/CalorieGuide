package com.obsessed.calorieguide.views.fragments.main;

import android.animation.ObjectAnimator;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.fragment.app.FragmentActivity;

import com.obsessed.calorieguide.data.local.Data;
import com.obsessed.calorieguide.tools.DayFunc;
import com.obsessed.calorieguide.databinding.FragmentMainBinding;
import com.obsessed.calorieguide.data.models.User;

public class Stats {
    private static Stats uniqueInstance;
    private static User user;
    private FragmentMainBinding binding;
    private FragmentActivity fragmentActivity;

    private Stats() {}

    public static Stats getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Stats();
        }
        return uniqueInstance;
    }

    public void init(FragmentMainBinding binding, FragmentActivity fragmentActivity) {
        user = Data.getInstance().getUser();
        this.binding = binding;
        this.fragmentActivity = fragmentActivity;
    }

    public void update() {
        fragmentActivity.runOnUiThread(() -> {
            updateValues();
            initCalories();
            initCPF();
            initPrsBar();
        });
    }

    private static void updateValues() {
        user.setCalories_current(DayFunc.getCalories());
        user.setCarbohydrates_current(DayFunc.getCarbohydrates());
        user.setProteins_current(DayFunc.getProteins());
        user.setFats_current(DayFunc.getFats());
    }

    private void initCalories() {
        binding.stats.tvCaloriesTotal.setText(String.valueOf(user.getCalories_current()));
        binding.stats.tvCaloriesLeft.setText(String.valueOf(user.getCalories_goal() - user.getCalories_current()));
        binding.stats.tvCaloriesGoal.setText(String.valueOf(user.getCalories_goal()));
    }

    public void initCPF() {
        binding.statsCpf.tvCarb.setText(user.getCarbohydrates_current() + "/" + user.getCarbohydrates_goal() + "g");
        binding.statsCpf.tvProteins.setText(user.getProteins_current() + "/" + user.getProteins_goal() + "g");
        binding.statsCpf.tvFats.setText(user.getFats_current() + "/" + user.getFats_goal() + "g");
    }

    private void initPrsBar() {
        updateProgressAnimated(binding.stats.prgBar, user.getCalories_current() * 100 / (user.getCalories_goal()+1));
        binding.stats.prgBar.setProgress( (int) (user.getCalories_current() * 100 / (user.getCalories_goal()+1)));

        updateProgressAnimated(binding.statsCpf.prgBarCarb, user.getCarbohydrates_current() * 100 / (user.getCarbohydrates_goal()+1));
        binding.statsCpf.prgBarCarb.setProgress( (int) (user.getCarbohydrates_current() * 100 / (user.getCarbohydrates_goal()+1)));

        updateProgressAnimated(binding.statsCpf.prgBarProteins, user.getProteins_current() * 100 / (user.getProteins_goal()+1));
        binding.statsCpf.prgBarProteins.setProgress( (int) (user.getProteins_current() * 100 / (user.getProteins_goal()+1)));

        updateProgressAnimated(binding.statsCpf.prgBarFats, user.getFats_current() * 100 / (user.getFats_goal()+1));
        binding.statsCpf.prgBarFats.setProgress( (int) (user.getFats_current() * 100 / (user.getFats_goal()+1)));
    }

    private static void updateProgressAnimated(ProgressBar progressBar, int newProgress) {
        int currentProgress = progressBar.getProgress();
        Log.d("Progress", "Current progress: " + currentProgress + " New progress: " + newProgress);

        // Проверяем, если новое значение прогресса отличается от текущего
        if (newProgress != currentProgress) {
            // Создаем анимацию изменения прогресса
            ObjectAnimator animator = ObjectAnimator.ofInt(progressBar, "progress", currentProgress, newProgress);
            animator.setDuration(1000);
            animator.start(); // Запускаем анимацию
        }
    }

}
