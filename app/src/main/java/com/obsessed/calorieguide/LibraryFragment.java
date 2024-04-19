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
import android.widget.TextView;

import com.obsessed.calorieguide.adapters.FoodAdapterV1;
import com.obsessed.calorieguide.adapters.FoodAdapterV2;
import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.databinding.FragmentLibraryBinding;
import com.obsessed.calorieguide.retrofit.food.Food;
import com.obsessed.calorieguide.retrofit.food.FoodCall;
import com.obsessed.calorieguide.retrofit.food.FoodCallback;

import java.util.ArrayList;
import java.util.List;

public class LibraryFragment extends Fragment implements FoodCallback {
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
        NavBarFragment nvb = new NavBarFragment(view);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_nav_bar, nvb);
        fragmentTransaction.commit();

        //Подгрузка данных
        requireActivity().runOnUiThread(() -> {
            FoodCall foodCall = new FoodCall(this); // Передаем экземпляр FoodCallback в конструктор FoodCall
            foodCall.getAllFood();
        });

        //Кнопка для добавления нового фрукта
        requireView().findViewById(R.id.buttonAdd).setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireView());
            navController.navigate(R.id.action_libraryFragment_to_addFoodFragment);
        });
    }

    @Override
    public void onFoodByIdReceived(Food food) {
        // Обновляем UI с полученным foodName
        TextView textView = requireView().findViewById(R.id.text);
        textView.setText(food.getFood_name());
    }

    @Override
    public void onAllFoodReceived(List<Food> foodList) {
        if(Data.getInstance().getAdapterType() == 2) {
            FoodAdapterV2 foodAdapter = new FoodAdapterV2();
            foodAdapter.foodArrayList = (ArrayList<Food>) foodList;

            binding.rcView.setLayoutManager(new GridLayoutManager(requireContext(), 1));
            binding.rcView.setAdapter(foodAdapter);
        } else {
            FoodAdapterV1 foodAdapter = new FoodAdapterV1();
            foodAdapter.foodArrayList = (ArrayList<Food>) foodList;

            binding.rcView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
            binding.rcView.setAdapter(foodAdapter);
        }
    }
}