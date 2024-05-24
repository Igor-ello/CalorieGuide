package com.obsessed.calorieguide.views.fragments.library;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;

import com.obsessed.calorieguide.data.local.room.AppDatabase;
import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.data.repository.FoodRepo;
import com.obsessed.calorieguide.tools.Data;
import com.obsessed.calorieguide.tools.Func;
import com.obsessed.calorieguide.databinding.FragmentFoodLibraryBinding;
import com.obsessed.calorieguide.data.local.dao.FoodDao;
import com.obsessed.calorieguide.data.remote.network.food.FoodCall;
import com.obsessed.calorieguide.data.remote.network.food.callbacks.CallbackLikeFood;
import com.obsessed.calorieguide.data.models.food.Food;
import com.obsessed.calorieguide.data.remote.network.food.callbacks.CallbackGetAllFood;
import com.obsessed.calorieguide.data.remote.network.food.callbacks.CallbackSearchFood;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LibraryFoodFragment extends Fragment implements CallbackGetAllFood, CallbackLikeFood, CallbackSearchFood {
    private FragmentFoodLibraryBinding binding;
    private AppDatabase db;
    private FoodDao foodDao;
    private final Executor executor;
    NavController navController;


    public LibraryFoodFragment() {
        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = AppDatabase.getInstance(requireContext());
        foodDao = db.foodDao();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_library, container, false);
        getActivity().findViewById(R.id.bottomNV).setVisibility(view.VISIBLE);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(requireView());
        binding = FragmentFoodLibraryBinding.bind(view);

        binding.arrowBack.arrowBack.setVisibility(View.GONE);

        FoodRepo foodRepo = new FoodRepo(foodDao);
        foodRepo.refreshFood(this);
        executor.execute(() -> {
            ArrayList<Food> foodList = (ArrayList<Food>) foodDao.getAllFood();
            requireActivity().runOnUiThread(() -> showAllFood(foodList));
        });


        //Поиск фруктов в библиотеке
        binding.searchAndAdd.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                FoodCall call = new FoodCall();
                call.searchFood(query, Data.getInstance().getUser().getId(), LibraryFoodFragment.this);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        //Кнопка для добавления нового фрукта
        view.findViewById(R.id.btAdd).setOnClickListener(v -> {
            navController.navigate(R.id.action_libraryFoodFragment_to_addFoodFragment);
        });

        //Кнопка для перехода в meal library
        view.findViewById(R.id.btToMealLib).setOnClickListener(v -> {
            navController.navigate(R.id.action_libraryFoodFragment_to_libraryMealFragment);
        });
    }

    public void showAllFood(ArrayList<Food> foodList) {
        Log.d("Received", "Size: " + foodList.size());
        new AllFoodReceived(requireContext(), requireView(), binding, foodList, this)
                .onAllFoodReceived();
    }

    @Override
    public void onAllFoodReceived(ArrayList<Food> foodList) {
        if (isAdded()) { // Проверяем, привязан ли фрагмент к активности
            // Вставка или обновление данных в локальной базе данных
            executor.execute(() -> {
                for (Food food : foodList) {
                    foodDao.insert(food);
                }
            });
            showAllFood(foodList);
        }
    }

    @Override
    public void onLikeFoodSuccess(ImageView imageView, boolean isLiked) {
        Func.setLikeState(imageView, isLiked);
    }

    @Override
    public void foodSearchReceived(ArrayList<Food> foodList) {
        if (isAdded()) { // Проверяем, привязан ли фрагмент к активности
            new AllFoodReceived(requireContext(), requireView(), binding, foodList, this)
                    .onAllFoodReceived();
        }
    }
}