package com.obsessed.calorieguide;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.obsessed.calorieguide.adapters.FoodAdapter;
import com.obsessed.calorieguide.retrofit.Food;
import com.obsessed.calorieguide.retrofit.FoodCall;
import com.obsessed.calorieguide.retrofit.FoodCallback;

import java.util.ArrayList;
import java.util.List;

public class LibraryFragment extends Fragment implements FoodCallback {
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
        Log.d("mlg", "Libra " + String.valueOf(this.hashCode()));

        NavBarFragment nvb = new NavBarFragment(view);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_nav_bar, nvb);
        fragmentTransaction.commit();

        requireActivity().runOnUiThread(() -> {
            FoodCall foodCall = new FoodCall(this); // Передаем экземпляр FoodCallback в конструктор FoodCall
            //foodCall.getFoodById(2);
            //foodCall.getAllFoodName();
            foodCall.getAllFood();
        });
    }

    @Override
    public void onFoodByIdReceived(String foodName) {
        // Обновляем UI с полученным foodName
        TextView textView = requireView().findViewById(R.id.text);
        textView.setText(foodName);
    }

    @Override
    public void onAllFoodNameReceived(String allFoodName) {
        TextView textView = requireView().findViewById(R.id.text2);
        textView.setText(allFoodName);
    }

    @Override
    public void onAllFoodReceived(List<Food> foodList) {
        FoodAdapter foodAdapter = new FoodAdapter();
        foodAdapter.foodArrayList = (ArrayList<Food>) foodList;

        RecyclerView rcView = requireView().findViewById(R.id.rcView);
        rcView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        rcView.setAdapter(foodAdapter);



//        Button buttonAdd = requireView().findViewById(R.id.buttonAdd);
//        buttonAdd.setOnClickListener(view -> {
//            List<String> ingredients = new ArrayList<>();
//            ingredients.add(String.valueOf(R.drawable.block_flipped));
//            foodAdapter.addFood(new Food("Чай-бутерброд", ingredients));
//        });

    }
}