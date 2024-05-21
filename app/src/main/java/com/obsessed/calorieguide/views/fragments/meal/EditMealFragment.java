package com.obsessed.calorieguide.views.fragments.meal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.obsessed.calorieguide.MainActivityApp;
import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.tools.Data;
import com.obsessed.calorieguide.data.remote.network.food.FoodCall;
import com.obsessed.calorieguide.data.remote.network.food.callbacks.CallbackGetAllFood;
import com.obsessed.calorieguide.data.models.food.Food;
import com.obsessed.calorieguide.data.remote.network.meal.callbacks.CallbackGetMealById;
import com.obsessed.calorieguide.data.models.food.FoodIdQuantity;
import com.obsessed.calorieguide.data.models.Meal;
import com.obsessed.calorieguide.data.remote.network.meal.MealCallWithToken;
import com.obsessed.calorieguide.data.remote.network.meal.MealCall;
import com.obsessed.calorieguide.tools.convert.FillClass;
import com.obsessed.calorieguide.tools.convert.ResizedBitmap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class EditMealFragment extends Fragment implements CallbackGetMealById, CallbackGetAllFood {
    private static final int GALLERY_REQUEST_CODE = 100;
    byte[] byteArray;
    private static final String ARG_MEAL_ID = "meal_id";
    private int mealId;
    FieldValidation fieldValidation;
    ImageView imageView;

    //Data received from the server
    Meal meal = null;
    List<Food> foodList = null;


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
        View view = inflater.inflate(R.layout.fragment_edit_meal, container, false);
        ((BottomNavigationView)((MainActivityApp) getActivity()).findViewById(R.id.bottomNV)).setVisibility(view.GONE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Инициализируем поля
        init(view);

        //Подгрузка данных
        requireActivity().runOnUiThread(() -> {
            MealCall mealCall = new MealCall();
            mealCall.getMealById(mealId, this);

            FoodCall call = new FoodCall();
            call.getAllFood(this);
        });

        view.findViewById(R.id.btDelete).setOnClickListener(v -> {
            MealCallWithToken mealCallWithToken = new MealCallWithToken(Data.getInstance().getUser().getBearerToken());
            mealCallWithToken.deleteMeal(mealId);

            Navigation.findNavController(view).popBackStack();
        });

        // Подгрузка изображения из галереи или камеры
        imageView.setOnClickListener(v -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
        });

        // Отправка на сервер введенных данных
        requireView().findViewById(R.id.btSave).setOnClickListener(v -> {
            ArrayList<EditText> etList = fieldValidation.getEtList();
            ArrayList<FoodIdQuantity> foodIdQuantities = fieldValidation.getFoodIdQuantities();
            if(etList != null){
                MealCallWithToken mealCallWithToken = new MealCallWithToken(Data.getInstance().getUser().getBearerToken());
                mealCallWithToken.updateMeal(mealId, FillClass.fillMeal(etList, byteArray, foodIdQuantities));

                Navigation.findNavController(view).popBackStack();
            } else {
                Toast.makeText(requireContext(), "Fill in all the fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void init(View view){
        imageView = view.findViewById(R.id.image);
        fieldValidation = new FieldValidation(requireContext(), requireView());
    }

    @Override
    public void onMealByIdReceived(Meal meal) {
        this.meal = meal;
        onDataReceived(meal, foodList);

        if (meal.getPicture() != null) {
            byteArray = meal.getPicture();
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            imageView.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onAllFoodReceived(ArrayList<Food> foodList) {
        this.foodList = foodList;
        onDataReceived(meal, foodList);
    }

    private void onDataReceived(Meal meal, List<Food> foodList) {
        if (meal != null && foodList != null) {
            fieldValidation.setValues(foodList, meal);
        }
    }

    // Метод для обработки результата выбора изображения из галереи или камеры
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Получаем URI выбранного изображения из галереи
            Uri selectedImageUri = data.getData();

            try {
                // Получаем Bitmap из URI выбранного изображения из галереи
                Bitmap originalBitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), selectedImageUri);

                // Уменьшаем размер Bitmap
                Bitmap resizedBitmap = ResizedBitmap.getResizedBitmap(originalBitmap,
                        Data.getInstance().getPictureSize(),
                        Data.getInstance().getPictureSize());

                // Устанавливаем уменьшенное изображение в ImageView
                imageView.setImageBitmap(resizedBitmap);

                // Сохраняем уменьшенное изображение в бинарный файл
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, Data.getInstance().getQuality(), stream);
                byteArray = stream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}