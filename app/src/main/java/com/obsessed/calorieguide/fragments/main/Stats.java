package com.obsessed.calorieguide.fragments.main;

import android.animation.ObjectAnimator;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.fragment.app.FragmentActivity;

import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.data.Day;
import com.obsessed.calorieguide.data.DayFunc;
import com.obsessed.calorieguide.databinding.FragmentMainBinding;
import com.obsessed.calorieguide.retrofit.user.User;

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
        user.setCaloriesCurrent(DayFunc.getCalories());
        user.setCarbohydratesCurrent(DayFunc.getCarbohydrates());
        user.setProteinsCurrent(DayFunc.getProteins());
        user.setFatsCurrent(DayFunc.getFats());
    }

    private void initCalories() {
        binding.stats.tvCaloriesTotal.setText(String.valueOf(user.getCaloriesCurrent()));
        binding.stats.tvCaloriesLeft.setText(String.valueOf(user.getCaloriesGoal() - user.getCaloriesCurrent()));
        binding.stats.tvCaloriesGoal.setText(String.valueOf(user.getCaloriesGoal()));
    }

    public void initCPF() {
        binding.statsCpf.tvCarb.setText(user.getCarbohydratesCurrent() + "/" + user.getCarbonatesGoal() + "g");
        binding.statsCpf.tvProteins.setText(user.getProteinsCurrent() + "/" + user.getProteinsGoal() + "g");
        binding.statsCpf.tvFats.setText(user.getFatsCurrent() + "/" + user.getFatsGoal() + "g");
    }

    private void initPrsBar() {
        updateProgressAnimated(binding.stats.prgBar, user.getCaloriesCurrent() * 100 / (user.getCaloriesGoal()+1));
        binding.stats.prgBar.setProgress( (int) (user.getCaloriesCurrent() * 100 / (user.getCaloriesGoal()+1)));

        updateProgressAnimated(binding.statsCpf.prgBarCarb, user.getCarbohydratesCurrent() * 100 / (user.getCarbonatesGoal()+1));
        binding.statsCpf.prgBarCarb.setProgress( (int) (user.getCarbohydratesCurrent() * 100 / (user.getCarbonatesGoal()+1)));

        updateProgressAnimated(binding.statsCpf.prgBarProteins, user.getProteinsCurrent() * 100 / (user.getProteinsGoal()+1));
        binding.statsCpf.prgBarProteins.setProgress( (int) (user.getProteinsCurrent() * 100 / (user.getProteinsGoal()+1)));

        updateProgressAnimated(binding.statsCpf.prgBarFats, user.getFatsCurrent() * 100 / (user.getFatsGoal()+1));
        binding.statsCpf.prgBarFats.setProgress( (int) (user.getFatsCurrent() * 100 / (user.getFatsGoal()+1)));
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
