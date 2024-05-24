package com.obsessed.calorieguide.views.fragments.library;

import static com.obsessed.calorieguide.data.local.Data.SORT_DATE;

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
import com.obsessed.calorieguide.data.local.Data;
import com.obsessed.calorieguide.data.repository.FoodRepo;
import com.obsessed.calorieguide.tools.Func;
import com.obsessed.calorieguide.databinding.FragmentFoodLibraryBinding;
import com.obsessed.calorieguide.data.remote.network.food.FoodCall;
import com.obsessed.calorieguide.data.callback.food.CallbackLikeFood;
import com.obsessed.calorieguide.data.models.food.Food;
import com.obsessed.calorieguide.data.callback.food.CallbackGetAllFood;
import com.obsessed.calorieguide.data.callback.food.CallbackSearchFood;
import com.obsessed.calorieguide.views.fragments.intake.FoodIntakeFragment;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class LibraryFoodFragment extends Fragment implements CallbackGetAllFood, CallbackLikeFood, CallbackSearchFood {
    private FragmentFoodLibraryBinding binding;
    private AppDatabase db;


    public LibraryFoodFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = AppDatabase.getInstance(requireContext());
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
        binding = FragmentFoodLibraryBinding.bind(view);

        FoodRepo repo = new FoodRepo(db.foodDao());
        repo.getAllFood(SORT_DATE, 1, 0, this);

        //Поиск фруктов в библиотеке
        binding.searchAndAdd.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                repo.searchFood(query, Data.getInstance().getUser().getId(),  LibraryFoodFragment.this);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                repo.searchFood(newText, Data.getInstance().getUser().getId(), LibraryFoodFragment.this);
                return false;
            }
        });


        //Кнопка для добавления нового фрукта
        view.findViewById(R.id.btAdd).setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireView());
            navController.navigate(R.id.action_libraryFoodFragment_to_addFoodFragment);
        });

        //Кнопка для перехода в meal library
        view.findViewById(R.id.btToMealLib).setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_libraryFoodFragment_to_libraryMealFragment);
        });
    }

    @Override
    public void onAllFoodReceived(ArrayList<Food> foodList) {
        requireActivity().runOnUiThread(() -> {
            Log.d("Received", "Size: " + foodList.size());
            new AllFoodReceived(requireContext(), requireView(), binding, foodList, this)
                    .onAllFoodReceived();
        });
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