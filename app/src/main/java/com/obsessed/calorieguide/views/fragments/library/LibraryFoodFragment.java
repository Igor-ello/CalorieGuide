package com.obsessed.calorieguide.views.fragments.library;

import static com.obsessed.calorieguide.data.local.Data.SORT_DATE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.obsessed.calorieguide.data.callback.food.CallbackLoadFood;
import com.obsessed.calorieguide.data.local.load.LoadRemoteData;
import com.obsessed.calorieguide.data.local.room.AppDatabase;
import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.data.repository.FoodRepo;
import com.obsessed.calorieguide.tools.Func;
import com.obsessed.calorieguide.databinding.FragmentFoodLibraryBinding;
import com.obsessed.calorieguide.data.callback.food.CallbackLikeFood;
import com.obsessed.calorieguide.data.models.food.Food;
import com.obsessed.calorieguide.data.callback.food.CallbackGetAllFood;
import com.obsessed.calorieguide.data.callback.food.CallbackSearchFood;
import com.obsessed.calorieguide.views.adapters.food.FoodAdapterV1;

import java.util.ArrayList;

public class LibraryFoodFragment extends Fragment implements CallbackGetAllFood, CallbackLikeFood, CallbackSearchFood, CallbackLoadFood {
    private FragmentFoodLibraryBinding binding;
    private AppDatabase db;
    private FoodRepo repo;
    private Handler handler;
    private NavController navController;


    public LibraryFoodFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = AppDatabase.getInstance(requireContext());
        repo = new FoodRepo(db.foodDao());
        handler = new Handler(Looper.getMainLooper());
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
        navController = Navigation.findNavController(requireView());

        repo.getAllFood(SORT_DATE, 1, this);

        binding.arrowBack.arrowBack.setVisibility(View.GONE);

        //Поиск фруктов в библиотеке
        binding.searchAndAdd.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.trim().isEmpty()) {
                    repo.searchFood(query, LibraryFoodFragment.this);
                } else {
                    repo.getAllFood(SORT_DATE, 1, LibraryFoodFragment.this);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.trim().isEmpty()) {
                    repo.searchFood(newText, LibraryFoodFragment.this);
                } else {
                    repo.getAllFood(SORT_DATE, 1, LibraryFoodFragment.this);
                }
                return false;
            }
        });

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            //Обновление списка фруктов из удаленной базы данных
            LoadRemoteData.getInstance(requireContext()).loadFood(1, this);
            Func.setTimeLimit(handler, 3000, requireContext(), binding.swipeRefreshLayout);
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

    @Override
    public void onAllFoodReceived(ArrayList<Food> foodList) {
        if (isAdded()) {
            requireActivity().runOnUiThread(() -> {
                Log.d("Received", "Size: " + foodList.size());
                new AllFoodReceived(requireContext(), requireView(), binding, foodList, this)
                        .allFoodReceived();
            });
        }
    }

    @Override
    public void onLikeFoodSuccess(ImageView imageView, boolean isLiked) {
        Func.setLikeState(imageView, isLiked);
    }

    @Override
    public void foodSearchReceived(ArrayList<Food> foodList) {
        requireActivity().runOnUiThread(() -> {
            new AllFoodReceived(requireContext(), requireView(), binding, foodList, this)
                    .allFoodReceived();
        });
    }

    @Override
    public void onLoadFood(ArrayList<Food> foodList) {
        handler.removeCallbacksAndMessages(null); // Отменяем таймер
        repo.getAllFood(SORT_DATE, 1, this);
        binding.swipeRefreshLayout.setRefreshing(false);
    }
}