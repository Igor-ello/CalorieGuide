package com.obsessed.calorieguide;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.obsessed.calorieguide.adapters.FoodAdapterV1;
import com.obsessed.calorieguide.adapters.FoodAdapterV2;
import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.databinding.FragmentLibraryBinding;
import com.obsessed.calorieguide.retrofit.food.Food;
import com.obsessed.calorieguide.retrofit.food.FoodCall;
import com.obsessed.calorieguide.retrofit.food.FoodCallForAdapter;
import com.obsessed.calorieguide.retrofit.food.CallbackGetAllFood;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LibraryFragment extends Fragment implements CallbackGetAllFood {
    FragmentLibraryBinding binding;

    public LibraryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_library, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentLibraryBinding.bind(view);

        //NavBarFragment
        setupNavBarFragment(view);

        //Подгрузка данных
        requireActivity().runOnUiThread(() -> {
            FoodCallForAdapter foodCallForAdapter = new FoodCallForAdapter(this); // Передаем экземпляр CallbackGetAllFood в конструктор FoodCallForAdapter
            foodCallForAdapter.getAllFood();
        });

        //Кнопка для добавления нового фрукта
        requireView().findViewById(R.id.buttonAdd).setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireView());
            navController.navigate(R.id.action_libraryFragment_to_addFoodFragment);
        });
    }

    @Override
    public void onAllFoodReceived(List<Food> foodList) {
        if (isAdded()) { // Проверяем, привязан ли фрагмент к активности
            if (Data.getInstance().getAdapterType() == 2) {
                FoodAdapterV2 foodAdapter = new FoodAdapterV2();
                foodAdapter.foodArrayList = (ArrayList<Food>) foodList;
                binding.rcView.setLayoutManager(new GridLayoutManager(requireContext(), 1));
                binding.rcView.setAdapter(foodAdapter);

                // Установка слушателя в адаптере
                foodAdapter.setOnFoodClickListener(food -> {
                    Log.d("FoodAdapter", "Clicked on food in FoodAdapterV2: " + food.getFoodName());
                    Bundle args = new Bundle();
                    args.putInt("food_id", food.getId());
                    Navigation.findNavController(requireView()).navigate(R.id.action_libraryFragment_to_editFoodFragment, args);
                });
            } else {
                FoodAdapterV1 foodAdapter = new FoodAdapterV1();
                foodAdapter.foodArrayList = (ArrayList<Food>) foodList;
                binding.rcView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
                binding.rcView.setAdapter(foodAdapter);

                // Установка слушателя в адаптере
                foodAdapter.setOnFoodClickListener(food -> {
                    Log.d("FoodAdapter", "Clicked on food in FoodAdapterV1: " + food.getFoodName());
                    Bundle args = new Bundle();
                    args.putInt("food_id", food.getId());
                    Navigation.findNavController(requireView()).navigate(R.id.action_libraryFragment_to_editFoodFragment, args);
                });

                foodAdapter.setOnLikeFoodClickListener(food -> {
                    Log.d("FoodAdapter", "Clicked on like for food in FoodAdapterV1: " + food.getFoodName());
                    FoodCall foodCall = new FoodCall(Data.getInstance().getUser().getBearerToken());
                    foodCall.likeFood(Data.getInstance().getUser().getId(), food.getId(), requireView());
                });
            }
        }
    }

    private void setupNavBarFragment(View view) {
        Log.d("MainFragment", "Setting up navigation bar fragment");
        NavBarFragment nvb = new NavBarFragment(view);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_nav_bar, nvb);
        fragmentTransaction.commit();
    }
}