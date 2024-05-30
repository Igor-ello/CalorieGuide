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
import com.obsessed.calorieguide.data.callback.food.CallbackGetLikedFood;
import com.obsessed.calorieguide.data.local.room.AppDatabase;
import com.obsessed.calorieguide.data.repository.DayRepo;
import com.obsessed.calorieguide.data.repository.FoodRepo;
import com.obsessed.calorieguide.databinding.FragmentFoodLibraryBinding;
import com.obsessed.calorieguide.views.adapters.food.FoodIntakeAdapter;
import com.obsessed.calorieguide.data.local.Data;
import com.obsessed.calorieguide.tools.DayFunc;
import com.obsessed.calorieguide.tools.Func;
import com.obsessed.calorieguide.data.remote.network.food.FoodCallWithToken;
import com.obsessed.calorieguide.data.callback.food.CallbackLikeFood;
import com.obsessed.calorieguide.data.callback.food.CallbackSearchFood;
import com.obsessed.calorieguide.data.models.food.Food;
import com.obsessed.calorieguide.data.remote.network.food.FoodCall;
import com.obsessed.calorieguide.views.fragments.library.LibraryFoodFragment;

import java.util.ArrayList;

public class FoodIntakeFragment extends Fragment implements CallbackSearchFood, CallbackLikeFood, CallbackGetLikedFood, CallbackRefreshDay {
    FragmentFoodLibraryBinding binding;
    private static final String ARG_ARRAY_TYPE = "array_type";
    private String arrayType;
    private AppDatabase db;

    public FoodIntakeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!= null) {
            arrayType = getArguments().getString(ARG_ARRAY_TYPE);
        }
        db = AppDatabase.getInstance(requireContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_library, container, false);
        getActivity().findViewById(R.id.bottomNV).setVisibility(view.GONE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentFoodLibraryBinding.bind(view);
        FoodRepo repo = new FoodRepo(db.foodDao());

        repo.getLikedFood(Data.getInstance().getUser().getId(), this);

        binding.arrowBack.arrowBack.setOnClickListener(v -> {
            Navigation.findNavController(view).popBackStack();
        });

        binding.btToMealLib.setVisibility(View.GONE);

        binding.searchAndAdd.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.trim().isEmpty()) {
                    repo.searchFood(query, FoodIntakeFragment.this);
                } else {
                    repo.getLikedFood(Data.getInstance().getUser().getId(), FoodIntakeFragment.this);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.trim().isEmpty()) {
                    repo.searchFood(newText, FoodIntakeFragment.this);
                } else {
                    repo.getLikedFood(Data.getInstance().getUser().getId(), FoodIntakeFragment.this);
                }
                return false;
            }
        });
    }

    @Override
    public void foodSearchReceived(ArrayList<Food> foodList) {
        Log.d("Adapter", "FoodSearchReceived: " + foodList.size());
        requireActivity().runOnUiThread(()  -> {
            FoodIntakeAdapter adapter = new FoodIntakeAdapter(foodList);
            binding.rcView.setLayoutManager(new GridLayoutManager(requireContext(), 1));
            binding.rcView.setAdapter(adapter);

            // Установка слушателя в адаптере
            adapter.setOnFoodClickListener(food -> {
                Log.d("Adapter", "Clicked on food in FoodIntakeAdapter: " + food.getFood_name());
                Bundle args = new Bundle();
                args.putInt("food_id", food.getId());
                Navigation.findNavController(requireView()).navigate(R.id.editFoodFragment, args);
            });

            adapter.setOnLikeFoodClickListener((food, imageView) -> {
                Log.d("Adapter", "Clicked on like for food in FoodAdapterLike: " + food.getFood_name());
                FoodCallWithToken call = new FoodCallWithToken(Data.getInstance().getUser().getBearerToken());
                call.likeFood(Data.getInstance().getUser().getId(), food, imageView, this);
            });

            adapter.setOnAddFoodClickListener(food -> {
                Log.d("Adapter", "Clicked on add for food in FoodIntakeAdapter: " + food.getFood_name() + "; ArrayType: " + arrayType);
                DayFunc.addObjectToDay(food, arrayType);

                requireView().findViewById(R.id.loading).setVisibility(View.VISIBLE);
                requireView().findViewById(R.id.lnMain).setVisibility(View.GONE);

                AppDatabase db = AppDatabase.getInstance(requireContext());
                DayRepo dayRepo = new DayRepo(db.dayDao());
                dayRepo.refreshDay(this);
            });
        });
    }

    @Override
    public void onLikeFoodSuccess(ImageView imageView, boolean isLiked, int id, int likes)  {
        Func.setLikeState(imageView, isLiked);
        FoodRepo repo = new FoodRepo(db.foodDao());
        repo.likeFood(id, isLiked, likes);
    }

    @Override
    public void onLikedFoodReceived(ArrayList<Food> foodArrayList) {
        Log.d("Adapter", "onLikedFoodReceived: " + foodArrayList.size());
        foodSearchReceived(foodArrayList);
    }

    @Override
    public void onRefreshDay() {
        requireActivity().runOnUiThread(() -> {
            requireView().findViewById(R.id.loading).setVisibility(View.GONE);
            requireView().findViewById(R.id.lnMain).setVisibility(View.VISIBLE);
        });
    }
}