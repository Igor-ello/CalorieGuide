package com.obsessed.calorieguide.fragments.meal;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.adapters.food.OnFoodClickListener;
import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.retrofit.food.CallbackGetAllFood;
import com.obsessed.calorieguide.retrofit.food.Food;
import com.obsessed.calorieguide.retrofit.food.FoodCallAndCallback;
import com.obsessed.calorieguide.retrofit.meal.FoodIdQuantity;
import com.obsessed.calorieguide.retrofit.meal.MealCall;
import com.obsessed.calorieguide.tools.convert.FillClass;

import java.util.ArrayList;
import java.util.List;

public class AddMealFragment extends Fragment implements CallbackGetAllFood {
    private static final int GALLERY_REQUEST_CODE = 100;
    byte[] byteArray;
    FieldValidation fieldValidation;
    ImageView imageView;

    public AddMealFragment() {
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
        return inflater.inflate(R.layout.fragment_add_meal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Инициализируем поля
        init(view);

//        // Подгрузка изображения из галереи или камеры
//        imageView.setOnClickListener(v -> {
//            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
//        });

        view.findViewById(R.id.btSetNumber).setOnClickListener(v -> {
            FoodCallAndCallback foodCallAndCallback = new FoodCallAndCallback(this);
            foodCallAndCallback.getAllFood();
        });

        // Отправка на сервер введенных данных
        requireView().findViewById(R.id.btSave).setOnClickListener(v -> {
            ArrayList<EditText> etList = fieldValidation.getEtList();
            ArrayList<FoodIdQuantity> foodIdQuantities = fieldValidation.getFoodIdQuantities(requireContext());
            if(etList != null){
                MealCall mealCall = new MealCall(Data.getInstance().getUser().getBearerToken());
                mealCall.postMeal(FillClass.fillMeal(etList, byteArray, foodIdQuantities));

                Navigation.findNavController(view).popBackStack();
            } else {
                Toast.makeText(requireContext(), "Fill in all the fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void init(View view){
        imageView = view.findViewById(R.id.image);
        fieldValidation = new FieldValidation(requireView());
    }

    @Override
    public void onAllFoodReceived(List<Food> foodList) {
        List<String> foodNames = new ArrayList<>();
        for (Food food : foodList) {
            foodNames.add(food.getFoodName());
        }
        fieldValidation.fillLnFood(requireContext(), foodNames);;
    }
}