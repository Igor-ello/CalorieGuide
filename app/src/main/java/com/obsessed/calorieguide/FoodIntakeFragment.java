package com.obsessed.calorieguide;

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

import com.obsessed.calorieguide.adapters.food.FoodAdapterLike;
import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.data.DayFunc;
import com.obsessed.calorieguide.data.Func;
import com.obsessed.calorieguide.databinding.FragmentFoodLibraryBinding;
import com.obsessed.calorieguide.retrofit.food.CallbackLikeFood;
import com.obsessed.calorieguide.retrofit.food.CallbackSearchFood;
import com.obsessed.calorieguide.retrofit.food.Food;
import com.obsessed.calorieguide.retrofit.food.FoodCall;

import java.util.ArrayList;

public class FoodIntakeFragment extends Fragment implements CallbackSearchFood, CallbackLikeFood {
    FragmentFoodLibraryBinding binding;
    private static final String ARG_ARRAY_TYPE = "array_type";
    private String arrayType;

    public FoodIntakeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!= null) {
            arrayType = getArguments().getString(ARG_ARRAY_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_library, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentFoodLibraryBinding.bind(view);

        requireActivity().runOnUiThread(() -> {
            //TODO
        });

        binding.btToMealLib.setVisibility(View.GONE);

        binding.searchAndAdd.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Вызывается при отправке запроса поиска (нажатии Enter или отправке формы)
                FoodCall call = new FoodCall(Data.getInstance().getUser().getBearerToken());
                call.searchFood(query, FoodIntakeFragment.this);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Вызывается при изменении текста в строке поиска
                // Здесь обычно выполняется поиск по мере ввода
                // В этом примере не обрабатываем изменения текста в реальном времени
                return false;
            }
        });
    }

    @Override
    public void foodSearchReceived(ArrayList<Food> foodList) {
        FoodAdapterLike adapter = new FoodAdapterLike(foodList);
        binding.rcView.setLayoutManager(new GridLayoutManager(requireContext(), 1));
        binding.rcView.setAdapter(adapter);

        // Установка слушателя в адаптере
        adapter.setOnFoodClickListener(food -> {
            Log.d("Adapter", "Clicked on food in FoodAdapterLike: " + food.getFoodName());
            Bundle args = new Bundle();
            args.putInt("food_id", food.getId());
            Navigation.findNavController(requireView()).navigate(R.id.editFoodFragment, args);
        });

        adapter.setOnLikeFoodClickListener((food, imageView) -> {
            Log.d("Adapter", "Clicked on like for food in FoodAdapterLike: " + food.getFoodName());
            FoodCall foodCall = new FoodCall(Data.getInstance().getUser().getBearerToken());
            foodCall.likeFood(Data.getInstance().getUser().getId(), food.getId(), imageView, this);
        });

        adapter.setOnAddFoodClickListener(food -> {
            Log.d("Adapter", "Clicked on add for food in FoodAdapterLike: " + food.getFoodName());
            DayFunc.addObjectToDay(food, arrayType);
        });
    }

    @Override
    public void onLikeFoodSuccess(ImageView imageView) {
        Func.setLikeState(imageView);
    }
}