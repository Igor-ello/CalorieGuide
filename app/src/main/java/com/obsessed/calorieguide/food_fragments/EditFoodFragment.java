package com.obsessed.calorieguide.food_fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.retrofit.food.CallbackGetFoodById;
import com.obsessed.calorieguide.retrofit.food.Food;
import com.obsessed.calorieguide.retrofit.food.FoodCall;

public class EditFoodFragment extends Fragment implements CallbackGetFoodById {
    private static final String ARG_FOOD_ID = "food_id";
    private int food_id;

    public EditFoodFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            food_id = getArguments().getInt(ARG_FOOD_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_food, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Подгрузка данных
        requireActivity().runOnUiThread(() -> {
            FoodCall foodCall = new FoodCall(this);
            foodCall.getFoodById(food_id);
        });
    }

    @Override
    public void onFoodByIdReceived(Food food) {

    }
}
