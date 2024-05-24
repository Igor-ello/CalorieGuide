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

import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.data.local.room.AppDatabase;
import com.obsessed.calorieguide.data.local.Data;
import com.obsessed.calorieguide.data.repository.MealRepo;
import com.obsessed.calorieguide.tools.Func;
import com.obsessed.calorieguide.databinding.FragmentMealLibraryBinding;
import com.obsessed.calorieguide.data.callback.meal.CallbackGetAllMeal;
import com.obsessed.calorieguide.data.callback.meal.CallbackLikeMeal;
import com.obsessed.calorieguide.data.models.Meal;
import com.obsessed.calorieguide.data.remote.network.meal.MealCall;
import com.obsessed.calorieguide.data.callback.meal.CallbackSearchMeal;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class LibraryMealFragment extends Fragment implements CallbackGetAllMeal, CallbackLikeMeal, CallbackSearchMeal {
    FragmentMealLibraryBinding binding;
    private AppDatabase db;


    public LibraryMealFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = AppDatabase.getInstance(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal_library, container, false);
        getActivity().findViewById(R.id.bottomNV).setVisibility(view.VISIBLE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentMealLibraryBinding.bind(view);

        MealRepo repo = new MealRepo(db.mealDao());
        repo.getAllMeals(SORT_DATE, 1, 0, this);

        //Поиск питания
        binding.searchAndAdd.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                repo.searchMeals(query, Data.getInstance().getUser().getId(), LibraryMealFragment.this);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                repo.searchMeals(newText, Data.getInstance().getUser().getId(), LibraryMealFragment.this);
                return false;
            }
        });

        //Кнопка для добавления нового питания
        view.findViewById(R.id.btAdd).setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireView());
            navController.navigate(R.id.action_libraryMealFragment_to_addMealFragment);
        });

        //Кнопка для перехода в food library
        view.findViewById(R.id.btToFoodLib).setOnClickListener(v -> {
           NavController navController = Navigation.findNavController(requireView());
           navController.popBackStack();
        });
    }

    @Override
    public void onAllMealReceived(ArrayList<Meal> mealList) {
        requireActivity().runOnUiThread(() -> {
            Log.d("Received", "Size: " + mealList.size());
            new AllMealReceived(requireContext(), requireView(), binding, mealList, this)
                    .onAllMealReceived();
        });
    }

    @Override
    public void onLikeMealSuccess(ImageView imageView, boolean isLiked) {
        Func.setLikeState(imageView, isLiked);
    }

    @Override
    public void mealSearchReceived(ArrayList<Meal> mealList) {
        if (isAdded()) { // Проверяем, привязан ли фрагмент к активности
            new AllMealReceived(requireContext(), requireView(), binding, mealList, this)
                    .onAllMealReceived();
        }
    }
}