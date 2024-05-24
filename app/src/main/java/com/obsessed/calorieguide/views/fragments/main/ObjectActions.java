package com.obsessed.calorieguide.views.fragments.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.obsessed.calorieguide.MainActivityApp;
import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.data.callback.user.CallbackRefreshDay;
import com.obsessed.calorieguide.data.local.room.AppDatabase;
import com.obsessed.calorieguide.data.models.Intake;
import com.obsessed.calorieguide.data.local.Data;
import com.obsessed.calorieguide.data.repository.DayRepo;
import com.obsessed.calorieguide.tools.DayFunc;
import com.obsessed.calorieguide.data.remote.network.food.FoodCallWithToken;
import com.obsessed.calorieguide.data.callback.food.CallbackGetFoodById;
import com.obsessed.calorieguide.data.models.food.Food;
import com.obsessed.calorieguide.data.remote.network.food.FoodCall;
import com.obsessed.calorieguide.data.callback.meal.CallbackGetMealById;
import com.obsessed.calorieguide.data.models.Meal;
import com.obsessed.calorieguide.data.remote.network.meal.MealCallWithToken;
import com.obsessed.calorieguide.data.remote.network.meal.MealCall;

public class ObjectActions extends Fragment implements CallbackGetMealById, CallbackGetFoodById {
    private static final String ARG_OBJECT_ID = "object_id";
    private static final String ARG_OBJECT_TYPE = "object_type";
    private static final String ARG_ARRAY_TYPE = "array_type";
    private static final String ARG_POS_IN_ARRAY = "pos_in_array";
    private int objectId, posInArray;
    private String objectType, arrayType;
    private Intake object;

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
        getActivity().findViewById(R.id.bottomNV).setVisibility(view.GONE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (objectType.equals("meal")) {
            MealCall mealCall = new MealCall();
            mealCall.getMealById(objectId, this);
        } else if (objectType.equals("food")) {
            FoodCall call = new FoodCall();
            call.getFoodById(objectId, this);
        }

        view.findViewById(R.id.btBack).setOnClickListener(v -> {
            Navigation.findNavController(view).popBackStack();
        });

        view.findViewById(R.id.btAddObject).setOnClickListener(v -> {
            if(object != null) {
                DayFunc.addObjectToDay(object, arrayType);
            }
            Navigation.findNavController(view).popBackStack();
        });

        view.findViewById(R.id.btLikeObject).setOnClickListener(v -> {
            if (object != null && objectType.equals("meal")) {
                MealCallWithToken mealCallWithToken = new MealCallWithToken(Data.getInstance().getUser().getBearerToken());
                mealCallWithToken.likeMeal(Data.getInstance().getUser().getId(), (Meal)object, null, null);
            }
            if (object != null && objectType.equals("food")) {
                FoodCallWithToken call = new FoodCallWithToken(Data.getInstance().getUser().getBearerToken());
                call.likeFood(Data.getInstance().getUser().getId(), (Food)object, null, null);
            }
            Navigation.findNavController(view).popBackStack();
        });

        view.findViewById(R.id.btDeleteObject).setOnClickListener(v -> {
            if(object != null) {
                DayFunc.deleteObjectFromDay(posInArray, arrayType);

                AppDatabase db = AppDatabase.getInstance(requireContext());
                DayRepo dayRepo = new DayRepo(db.dayDao());
                dayRepo.refreshDay();
            }
            Navigation.findNavController(view).popBackStack();
        });
    }

    @Override
    public void onFoodByIdReceived(Food food) {
        object = food;
        ((ImageView)requireView().findViewById(R.id.btLikeObject))
                .setImageResource(food.getIsLiked()? R.drawable.like_active : R.drawable.like_not_active_black);;
    }

    @Override
    public void onMealByIdReceived(Meal meal) {
        object = meal;
        ((ImageView)requireView().findViewById(R.id.btLikeObject))
                .setImageResource(meal.getIsLiked()? R.drawable.like_active : R.drawable.like_not_active_black);;
    }
}
