package com.obsessed.calorieguide.views.fragments.main;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.databinding.FragmentMainBinding;
import com.obsessed.calorieguide.views.adapters.intake.IntakeAdapter;
import com.obsessed.calorieguide.tools.Data;
import com.obsessed.calorieguide.data.models.day.Day;
import com.obsessed.calorieguide.data.models.food.Food;
import com.obsessed.calorieguide.data.models.Meal;

public class Intakes {
    private static Intakes uniqueInstance;
    private FragmentMainBinding binding;
    private Context context;
    private static Day day;
    private static IntakeAdapter adapterBreakfast;
    private static IntakeAdapter adapterLunch;
    private static IntakeAdapter adapterDinner;

    private Intakes() {}

    public static Intakes getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Intakes();
            day = Data.getInstance().getDay();

            // Создаем адаптер и устанавливаем его для каждого приема пищи
            adapterBreakfast = new IntakeAdapter();
            adapterLunch = new IntakeAdapter();
            adapterDinner = new IntakeAdapter();
        }
        return uniqueInstance;
    }

    public void init(FragmentMainBinding binding, Context context) {
        this.binding = binding;
        this.context = context;

        initTv();
        initArrayListsForAdapters();
        setAdapterListeners();
        initButtons();
    }

    private void initTv() {
        // Устанавливаем текст надписей
        binding.intakeBreakfast.tvName.setText("Завтрак");
        binding.intakeLunch.tvName.setText("Обед");
        binding.intakeDinner.tvName.setText("Ужин");
    }

    private void initArrayListsForAdapters() {
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
    }

    private void initButtons() {
        View view = binding.getRoot();
        NavController navController = Navigation.findNavController(view);

        // Настройка обработчиков нажатий кнопок добавления
        binding.intakeBreakfast.btAddFood.setOnClickListener(v -> {
            navController.navigate(R.id.action_mainFragment_to_foodIntakeFragment,
                    createBundle("breakfast"));
        });
        binding.intakeBreakfast.btAddMeal.setOnClickListener(v -> {
            navController.navigate(R.id.action_mainFragment_to_mealIntakeFragment,
                    createBundle("breakfast"));
        });

        binding.intakeLunch.btAddFood.setOnClickListener(v -> {
            navController.navigate(R.id.action_mainFragment_to_foodIntakeFragment,
                    createBundle("lunch"));
        });
        binding.intakeLunch.btAddMeal.setOnClickListener(v -> {
            navController.navigate(R.id.action_mainFragment_to_mealIntakeFragment,
                    createBundle("lunch"));
        });

        binding.intakeDinner.btAddFood.setOnClickListener(v ->{
            navController.navigate(R.id.action_mainFragment_to_foodIntakeFragment,
                    createBundle("dinner"));
        });
        binding.intakeDinner.btAddMeal.setOnClickListener(v ->{
            navController.navigate(R.id.action_mainFragment_to_mealIntakeFragment,
                    createBundle("dinner"));
        });
    }

    private void setLM(Context context, RecyclerView recyclerView){
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setAdapterListeners(){
        // Установка слушателя в адаптере
        adapterBreakfast.setOnObjClickListener(obj -> {
            Bundle args = createBundle(obj, "breakfast", Data.getInstance().getDay().posInArray(obj, "breakfast"));
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_mainFragment_to_objectActions, args);
        });

        adapterLunch.setOnObjClickListener(obj -> {
            Bundle args = createBundle(obj, "lunch", Data.getInstance().getDay().posInArray(obj, "lunch"));
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_mainFragment_to_objectActions, args);
        });

        adapterDinner.setOnObjClickListener(obj -> {
            Bundle args = createBundle(obj, "dinner", Data.getInstance().getDay().posInArray(obj, "dinner"));
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_mainFragment_to_objectActions, args);
        });
    }

    private Bundle createBundle(Object obj, String array_type, int pos_in_array){
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

    private Bundle createBundle(String array_type){
        Bundle bundle = new Bundle();
        bundle.putString("array_type", array_type);
        return bundle;
    }
}
