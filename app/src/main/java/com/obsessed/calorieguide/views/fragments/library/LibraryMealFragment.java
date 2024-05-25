package com.obsessed.calorieguide.views.fragments.library;

import static com.obsessed.calorieguide.data.local.Data.DELAY_LONG;
import static com.obsessed.calorieguide.data.local.Data.SORT_DATE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;

import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.data.callback.meal.CallbackLoadMeal;
import com.obsessed.calorieguide.data.local.load.LoadRemoteData;
import com.obsessed.calorieguide.data.local.room.AppDatabase;
import com.obsessed.calorieguide.data.repository.MealRepo;
import com.obsessed.calorieguide.tools.Func;
import com.obsessed.calorieguide.databinding.FragmentMealLibraryBinding;
import com.obsessed.calorieguide.data.callback.meal.CallbackGetAllMeal;
import com.obsessed.calorieguide.data.callback.meal.CallbackLikeMeal;
import com.obsessed.calorieguide.data.models.Meal;
import com.obsessed.calorieguide.data.callback.meal.CallbackSearchMeal;

import java.util.ArrayList;
import java.util.Collections;

public class LibraryMealFragment extends Fragment implements CallbackGetAllMeal, CallbackLikeMeal, CallbackSearchMeal, CallbackLoadMeal {
    private FragmentMealLibraryBinding binding;
    private AppDatabase db;
    private MealRepo repo;
    private Handler handler;
    NavController navController;


    public LibraryMealFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = AppDatabase.getInstance(requireContext());
        repo = new MealRepo(db.mealDao());
        handler = new Handler(Looper.getMainLooper());
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
        navController = Navigation.findNavController(requireView());

        repo.getAllMeals(SORT_DATE, 1, this);

        binding.arrowBack.arrowBack.setVisibility(View.GONE);

        //Поиск питания
        binding.searchAndAdd.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.trim().isEmpty()) {
                    repo.searchMeals(query, LibraryMealFragment.this);
                } else {
                    repo.getAllMeals(SORT_DATE, 1,  LibraryMealFragment.this);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.trim().isEmpty()) {
                    repo.searchMeals(newText, LibraryMealFragment.this);
                } else {
                    repo.getAllMeals(SORT_DATE, 1,  LibraryMealFragment.this);
                }
                return false;
            }
        });

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            //Обновление списка фруктов из удаленной базы данных
            LoadRemoteData.getInstance(requireContext()).loadMeal(1, this);
            Func.setTimeLimitRefLn(handler, DELAY_LONG, requireContext(), binding.swipeRefreshLayout);
        });

        //Кнопка для добавления нового питания
        view.findViewById(R.id.btAdd).setOnClickListener(v -> {
            navController.navigate(R.id.action_libraryMealFragment_to_addMealFragment);
        });

        //Кнопка для перехода в food library
        view.findViewById(R.id.btToFoodLib).setOnClickListener(v -> {
           navController.popBackStack();
        });
    }

    @Override
    public void onAllMealReceived(ArrayList<Meal> mealList) {
        if (isAdded()) {
            requireActivity().runOnUiThread(() -> {
                Log.d("Received", "Size: " + mealList.size());
                new AllMealReceived(requireContext(), requireView(), binding, mealList, this)
                        .allMealReceived();
            });
        }
    }

    @Override
    public void onLikeMealSuccess(ImageView imageView, boolean isLiked) {
        Func.setLikeState(imageView, isLiked);
    }

    @Override
    public void mealSearchReceived(ArrayList<Meal> mealList) {
        if (isAdded()) { // Проверяем, привязан ли фрагмент к активности
            new AllMealReceived(requireContext(), requireView(), binding, mealList, this)
                    .allMealReceived();
        }
    }

    @Override
    public void onLoadMeal(ArrayList<Meal> mealList) {
        handler.removeCallbacksAndMessages(null); // Отменяем таймер
        repo.getAllMeals(SORT_DATE, 1, this);
        binding.swipeRefreshLayout.setRefreshing(false);
    }
}