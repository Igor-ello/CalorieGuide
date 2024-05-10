package com.obsessed.calorieguide.fragments.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.adapters.intake.IntakeAdapter;
import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.data.Day;
import com.obsessed.calorieguide.databinding.FragmentMainBinding;
import com.obsessed.calorieguide.retrofit.food.Food;
import com.obsessed.calorieguide.retrofit.meal.Meal;

public class Intakes {
    private static Day day;
    private static IntakeAdapter adapterBreakfast;
    private static IntakeAdapter adapterLunch;
    private static IntakeAdapter adapterDinner;


    public static void init(FragmentMainBinding binding, Context context, Activity activity) {
        // Создаем адаптер и устанавливаем его для каждого приема пищи
        adapterBreakfast = new IntakeAdapter();
        adapterLunch = new IntakeAdapter();
        adapterDinner = new IntakeAdapter();

        day = Data.getInstance().getDay();
        if (day == null) return;

        // Устанавливаем текст надписей
        binding.intakeBreakfast.tvName.setText("Завтрак");
        binding.intakeLunch.tvName.setText("Обед");
        binding.intakeDinner.tvName.setText("Ужин");

        // Установка списка объектов для каждого приема пищи
        adapterBreakfast.objArrayList = day.getBreakfast();
        setLM(context, binding.intakeBreakfast.rcView);
        binding.intakeBreakfast.rcView.setAdapter(adapterBreakfast);

        adapterLunch.objArrayList = day.getLunch();
        setLM(context, binding.intakeLunch.rcView);
        binding.intakeLunch.rcView.setAdapter(adapterLunch);

        adapterDinner.objArrayList = day.getDinner();
        setLM(context, binding.intakeDinner.rcView);
        binding.intakeDinner.rcView.setAdapter(adapterDinner);

        setAdapterListeners(binding);


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

    private static void setLM(Context context, RecyclerView recyclerView){
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    private static void setAdapterListeners(FragmentMainBinding binding){
        // Установка слушателя в адаптере
        adapterBreakfast.setOnObjClickListener(obj -> {
            Bundle args = createBundle(obj, "breakfast", Day.getInstance().posInArray(obj, "breakfast"));
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_mainFragment_to_objectActions, args);
        });

        adapterLunch.setOnObjClickListener(obj -> {
            Bundle args = createBundle(obj, "lunch", Day.getInstance().posInArray(obj, "lunch"));
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_mainFragment_to_objectActions, args);
        });

        adapterDinner.setOnObjClickListener(obj -> {
            Bundle args = createBundle(obj, "dinner", Day.getInstance().posInArray(obj, "dinner"));
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_mainFragment_to_objectActions, args);
        });
    }

    private static Bundle createBundle(Object obj, String array_type, int pos_in_array){
        if (obj != null) {
            Bundle args = new Bundle();

            if (obj instanceof Food) {
                args.putInt("object_id", ((Food) obj).getId());
                args.putString("object_type", "food");
            }
            else if (obj instanceof Meal) {
                args.putInt("object_id", ((Meal) obj).getId());
                args.putString("object_type", "meal");
            }
            args.putString("array_type", array_type);
            args.putInt("pos_in_array", pos_in_array);

            return args;
        }
        return null;
    }
}
