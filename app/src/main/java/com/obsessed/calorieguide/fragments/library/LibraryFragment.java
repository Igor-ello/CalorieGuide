package com.obsessed.calorieguide.fragments.library;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.obsessed.calorieguide.fragments.NavBarFragment;
import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.databinding.FragmentLibraryBinding;
import com.obsessed.calorieguide.retrofit.food.Food;
import com.obsessed.calorieguide.retrofit.food.FoodCallForAdapter;
import com.obsessed.calorieguide.retrofit.food.CallbackGetAllFood;

import java.util.List;

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
            FoodCallForAdapter foodCallForAdapter = new FoodCallForAdapter(this);
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
            AllFoodReceived.onAllFoodReceived(requireContext(), requireView(), binding, foodList);
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