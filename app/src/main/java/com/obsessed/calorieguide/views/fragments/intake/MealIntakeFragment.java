package com.obsessed.calorieguide.views.fragments.intake;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;

import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.data.callback.day.CallbackRefreshDay;
import com.obsessed.calorieguide.data.callback.meal.CallbackGetLikedMeals;
import com.obsessed.calorieguide.data.local.room.AppDatabase;
import com.obsessed.calorieguide.data.repository.DayRepo;
import com.obsessed.calorieguide.data.repository.MealRepo;
import com.obsessed.calorieguide.databinding.FragmentMealLibraryBinding;
import com.obsessed.calorieguide.views.adapters.meal.MealIntakeAdapter;
import com.obsessed.calorieguide.data.local.Data;
import com.obsessed.calorieguide.tools.DayFunc;
import com.obsessed.calorieguide.tools.Func;
import com.obsessed.calorieguide.data.callback.meal.CallbackLikeMeal;
import com.obsessed.calorieguide.data.callback.meal.CallbackSearchMeal;
import com.obsessed.calorieguide.data.models.Meal;
import com.obsessed.calorieguide.data.remote.network.meal.MealCallWithToken;

import java.util.ArrayList;

public class MealIntakeFragment extends Fragment implements CallbackSearchMeal, CallbackLikeMeal, CallbackGetLikedMeals, CallbackRefreshDay {
    FragmentMealLibraryBinding binding;
    private static final String ARG_ARRAY_TYPE = "array_type";
    private String arrayType;
    private AppDatabase db;

    public MealIntakeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!= null) {
            arrayType = getArguments().getString(ARG_ARRAY_TYPE);
        }
        db = AppDatabase.getInstance(requireActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal_library, container, false);
        getActivity().findViewById(R.id.bottomNV).setVisibility(view.GONE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentMealLibraryBinding.bind(view);
        MealRepo repo = new MealRepo(db.mealDao());

        repo.getLikedMeals(Data.getInstance().getUser().getId(), this);

        binding.arrowBack.arrowBack.setOnClickListener(v -> {
            Navigation.findNavController(view).popBackStack();
        });

        binding.btToFoodLib.setVisibility(View.GONE);

        binding.searchAndAdd.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.trim().isEmpty()) {
                    repo.searchMeals(query, MealIntakeFragment.this);
                } else {
                    repo.getLikedMeals(Data.getInstance().getUser().getId(), MealIntakeFragment.this);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.trim().isEmpty()) {
                    repo.searchMeals(newText, MealIntakeFragment.this);
                } else {
                    repo.getLikedMeals(Data.getInstance().getUser().getId(), MealIntakeFragment.this);
                }
                return false;
            }
        });
    }

    @Override
    public void mealSearchReceived(ArrayList<Meal> mealList) {
        requireActivity().runOnUiThread(() -> {
            MealIntakeAdapter adapter = new MealIntakeAdapter(mealList);
            binding.rcView.setLayoutManager(new GridLayoutManager(requireContext(), 1));
            binding.rcView.setAdapter(adapter);

            // Установка слушателя в адаптере
            adapter.setOnMealClickListener(meal -> {
                Log.d("Adapter", "Clicked on meal in MealIntakeAdapter: " + meal.getMeal_name());
                Bundle args = new Bundle();
                args.putInt("meal_id", meal.getId());
                Navigation.findNavController(requireView()).navigate(R.id.editMealFragment, args);
            });

            adapter.setOnLikeMealClickListener((meal, imageView) -> {
                Log.d("Adapter", "Clicked on like for meal in MealIntakeAdapter: " + meal.getMeal_name());
                MealCallWithToken call = new MealCallWithToken(Data.getInstance().getUser().getBearerToken());
                call.likeMeal(Data.getInstance().getUser().getId(), meal, imageView, this);
            });

            adapter.setOnAddMealClickListener(meal -> {
                Log.d("Adapter", "Clicked on add for meal in MealIntakeAdapter: " + meal.getMeal_name() + "; ArrayType: " + arrayType);
                DayFunc.addObjectToDay(meal, arrayType);

                requireView().findViewById(R.id.loading).setVisibility(View.VISIBLE);
                requireView().findViewById(R.id.lnMain).setVisibility(View.GONE);

                AppDatabase db = AppDatabase.getInstance(requireContext());
                DayRepo dayRepo = new DayRepo(db.dayDao());
                dayRepo.refreshDay(this);
            });
        });
    }

    @Override
    public void onLikeMealSuccess(ImageView imageView, boolean isLiked) {
        Func.setLikeState(imageView, isLiked);
    }

    @Override
    public void onLikedMealsReceived(ArrayList<Meal> mealsArrayList) {
        mealSearchReceived(mealsArrayList);
    }

    @Override
    public void onRefreshDay() {
        requireActivity().runOnUiThread(() -> {
            requireView().findViewById(R.id.loading).setVisibility(View.GONE);
            requireView().findViewById(R.id.lnMain).setVisibility(View.VISIBLE);
        });
    }
}