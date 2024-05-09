package com.obsessed.calorieguide.fragments.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.adapters.intake.IntakeAdapter;
import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.databinding.FragmentMainBinding;

import java.util.ArrayList;

public class Intakes {

    public static void intakes(FragmentMainBinding binding, Context context, View view, ArrayList<Object> objArrayList) {
        // Устанавливаем текст надписей
        binding.intakeLunch.tvName.setText("Обед");
        binding.intakeBreakfast.tvName.setText("Завтрак");
        binding.intakeDinner.tvName.setText("Ужин");

        // Создаем адаптер и устанавливаем его для каждого приема пищи
        IntakeAdapter intakeAdapter = new IntakeAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        intakeAdapter.objArrayList = objArrayList;

        // Установка списка объектов для каждого приема пищи
        binding.intakeLunch.rcView.setLayoutManager(layoutManager);
        binding.intakeLunch.rcView.setAdapter(intakeAdapter);

//        intakeAdapter.objectList = day.getBreakfast();
//        binding.intakeBreakfast.rcViewFood.setAdapter(intakeAdapter);
//
//        intakeAdapter.objArrayList = day.getDinner();
//        binding.intakeDinner.rcViewFood.setAdapter(intakeAdapter);

        // Настройка обработчиков нажатий кнопок добавления
        binding.intakeLunch.btAddFood.setOnClickListener(v -> {
            //Navigation.findNavController(view).navigate(R.id.action_dayFragment_to_addFoodFragment);
        });
        binding.intakeLunch.btAddMeal.setOnClickListener(v -> {
            //Navigation.findNavController(view).navigate(R.id.action_dayFragment_to_addMealFragment);
        });

//        binding.intakeBreakfast.btAddFood.setOnClickListener(v -> {
//            Navigation.findNavController(view).navigate(R.id.action_dayFragment_to_addFoodFragment);
//        });
//        binding.intakeBreakfast.btAddMeal.setOnClickListener(v -> {
//            Navigation.findNavController(view).navigate(R.id.action_dayFragment_to_addMealFragment);
//        });
//
//        binding.intakeDinner.btAddFood.setOnClickListener(v ->{
//            Navigation.findNavController(view).navigate(R.id.action_dayFragment_to_addFoodFragment);
//        });
//        binding.intakeDinner.btAddMeal.setOnClickListener(v ->{
//            Navigation.findNavController(view).navigate(R.id.action_dayFragment_to_addMealFragment);
//        });
    }

}
