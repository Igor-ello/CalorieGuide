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
import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.retrofit.meal.CallbackGetMealById;
import com.obsessed.calorieguide.retrofit.meal.FoodIdQuantity;
import com.obsessed.calorieguide.retrofit.meal.Meal;
import com.obsessed.calorieguide.retrofit.meal.MealCall;
import com.obsessed.calorieguide.retrofit.meal.MealCallAndCallback;
import com.obsessed.calorieguide.tools.convert.FillClass;

import java.util.ArrayList;


public class EditMealFragment extends Fragment implements CallbackGetMealById {
    private static final int GALLERY_REQUEST_CODE = 100;
    byte[] byteArray;
    private static final String ARG_MEAL_ID = "meal_id";
    private int mealId;
    FieldValidation fieldValidation;
    ImageView imageView;


    public EditMealFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mealId = getArguments().getInt(ARG_MEAL_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_meal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Инициализируем поля
        init(view);

        //Подгрузка данных
        requireActivity().runOnUiThread(() -> {
            MealCallAndCallback mealCallAndCallback = new MealCallAndCallback(this);
            mealCallAndCallback.getMealById(mealId);
        });

        view.findViewById(R.id.btDelete).setOnClickListener(v -> {
            MealCall mealCall = new MealCall(Data.getInstance().getUser().getBearerToken());
            mealCall.deleteMeal(mealId);

            Navigation.findNavController(view).popBackStack();
        });

//        // Подгрузка изображения из галереи или камеры
//        imageView.setOnClickListener(v -> {
//            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
//        });

        // Отправка на сервер введенных данных
        requireView().findViewById(R.id.btSave).setOnClickListener(v -> {
            ArrayList<EditText> etList = fieldValidation.getEtList();
            ArrayList<FoodIdQuantity> foodIdQuantities = fieldValidation.getFoodIdQuantities(requireContext());
            if(etList != null){
                MealCall mealCall = new MealCall(Data.getInstance().getUser().getBearerToken());
                mealCall.updateMeal(mealId, FillClass.fillMeal(etList, byteArray, foodIdQuantities));

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
    public void onMealByIdReceived(Meal meal) {

    }
}