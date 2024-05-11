package com.obsessed.calorieguide.fragments.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.obsessed.calorieguide.MainActivityApp;
import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.data.Day;
import com.obsessed.calorieguide.retrofit.food.FoodCallWithToken;
import com.obsessed.calorieguide.retrofit.food.callbacks.CallbackGetFoodById;
import com.obsessed.calorieguide.retrofit.food.Food;
import com.obsessed.calorieguide.retrofit.food.FoodCall;
import com.obsessed.calorieguide.retrofit.meal.callbacks.CallbackGetMealById;
import com.obsessed.calorieguide.retrofit.meal.Meal;
import com.obsessed.calorieguide.retrofit.meal.MealCall;
import com.obsessed.calorieguide.retrofit.meal.MealCallAndCallback;

public class ObjectActions extends Fragment implements CallbackGetMealById, CallbackGetFoodById {
    private static final String ARG_OBJECT_ID = "object_id";
    private static final String ARG_OBJECT_TYPE = "object_type";
    private static final String ARG_ARRAY_TYPE = "array_type";
    private static final String ARG_POS_IN_ARRAY = "pos_in_array";
    private int objectId, posInArray;
    private String objectType, arrayType;
    private Object object;

    public ObjectActions() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            objectId = getArguments().getInt(ARG_OBJECT_ID);
            objectType = getArguments().getString(ARG_OBJECT_TYPE);
            arrayType = getArguments().getString(ARG_ARRAY_TYPE);
            posInArray = getArguments().getInt(ARG_POS_IN_ARRAY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.object_actions, container, false);
        ((BottomNavigationView)((MainActivityApp) getActivity()).findViewById(R.id.bottomNV)).setVisibility(view.GONE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (objectType.equals("meal")) {
            MealCallAndCallback mealCallAndCallback = new MealCallAndCallback(this, Data.getInstance().getUser().getBearerToken());
            mealCallAndCallback.getMealById(objectId);
        } else if (objectType.equals("food")) {
            FoodCall call = new FoodCall();
            call.getFoodById(objectId, this);
        }

        view.findViewById(R.id.btBack).setOnClickListener(v -> {
            Navigation.findNavController(view).popBackStack();
        });

        view.findViewById(R.id.btAddObject).setOnClickListener(v -> {
            if(object != null) {
                Day day = Data.getInstance().getDay();
                if(arrayType.equals("breakfast")) {
                    day.addBreakfast(object);
                } else if(arrayType.equals("lunch")) {
                    day.addLunch(object);
                } else if(arrayType.equals("dinner")) {
                    day.addDinner(object);
                }
            }
            Navigation.findNavController(view).popBackStack();
        });

        view.findViewById(R.id.btLikeObject).setOnClickListener(v -> {
            if (object != null && objectType.equals("meal")) {
                MealCall mealCall = new MealCall(Data.getInstance().getUser().getBearerToken());
                mealCall.likeMeal(Data.getInstance().getUser().getId(), (Meal)object, null, null);
            }
            if (object != null && objectType.equals("food")) {
                FoodCallWithToken call = new FoodCallWithToken(Data.getInstance().getUser().getBearerToken());
                call.likeFood(Data.getInstance().getUser().getId(), (Food)object, null, null);
            }
            Navigation.findNavController(view).popBackStack();
        });

        view.findViewById(R.id.btDeleteObject).setOnClickListener(v -> {
            if(object != null) {
                Day day = Data.getInstance().getDay();
                if(arrayType.equals("breakfast")) {
                    day.deleteByIdBreakfast(posInArray);
                } else if(arrayType.equals("lunch")) {
                    day.deleteByIdLunch(posInArray);
                } else if(arrayType.equals("dinner")) {
                    day.deleteByIdDinner(posInArray);
                }
            }
            Navigation.findNavController(view).popBackStack();
        });
    }

    @Override
    public void onFoodByIdReceived(Food food) {
        object = food;
    }

    @Override
    public void onMealByIdReceived(Meal meal) {
        object = meal;
    }
}
