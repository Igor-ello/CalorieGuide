package com.obsessed.calorieguide.views.fragments.meal;

import static com.obsessed.calorieguide.data.local.Data.SORT_LIKE_DESCENDING;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.data.callback.food.CallbackGetAllFood;
import com.obsessed.calorieguide.data.local.room.AppDatabase;
import com.obsessed.calorieguide.data.repository.FoodRepo;
import com.obsessed.calorieguide.data.local.Data;
import com.obsessed.calorieguide.data.models.food.Food;
import com.obsessed.calorieguide.data.models.food.FoodIdQuantity;
import com.obsessed.calorieguide.data.remote.network.meal.MealCallWithToken;
import com.obsessed.calorieguide.tools.convert.FillClass;
import com.obsessed.calorieguide.tools.convert.ResizedBitmap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class AddMealFragment extends Fragment implements CallbackGetAllFood {
    private static final int GALLERY_REQUEST_CODE = 100;
    byte[] byteArray;
    FieldValidation fieldValidation;
    ImageView imageView;
    NavController navController;

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
        View view = inflater.inflate(R.layout.fragment_add_meal, container, false);
        getActivity().findViewById(R.id.bottomNV).setVisibility(view.GONE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Инициализируем поля
        init(view);

        view.findViewById(R.id.arrow_back).setOnClickListener(v -> {
            navController.popBackStack();
        });

        // Подгрузка изображения из галереи или камеры
        imageView.setOnClickListener(v -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
        });

        view.findViewById(R.id.btSetNumber).setOnClickListener(v -> {
            AppDatabase db = AppDatabase.getInstance(requireContext());
            FoodRepo foodRepo = new FoodRepo(db.foodDao());
            foodRepo.getAllFood(SORT_LIKE_DESCENDING, 1,this);
        });

        // Отправка на сервер введенных данных
        requireView().findViewById(R.id.btSave).setOnClickListener(v -> {
            ArrayList<EditText> etList;
            ArrayList<FoodIdQuantity> foodIdQuantities;
            try {
                etList = fieldValidation.getEtList();
            }
            catch (NullPointerException | IllegalArgumentException e) {
                return;
            }
			if(etList != null){
                try {
                    foodIdQuantities = fieldValidation.getFoodIdQuantities();
                } catch (NullPointerException e) {
                    Toast.makeText(requireContext(), "Please, fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                } catch (NumberFormatException e) {
                    Toast.makeText(requireContext(), "You can't enter more than 50 or less than 1", Toast.LENGTH_SHORT).show();
                    return;
                }
                MealCallWithToken mealCallWithToken = new MealCallWithToken(Data.getInstance().getUser().getBearerToken());
                mealCallWithToken.postMeal(FillClass.fillMeal(etList, byteArray, foodIdQuantities));

                Navigation.findNavController(view).popBackStack();
            } else {
                Toast.makeText(requireContext(), "Fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void init(View view){
        imageView = view.findViewById(R.id.image);
        fieldValidation = new FieldValidation(requireContext(), requireView());
        navController = Navigation.findNavController(view);
    }

    @Override
    public void onAllFoodReceived(ArrayList<Food> foodList) {
        fieldValidation.fillLnFood(foodList, null);;
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
                        Data.getInstance().PICTURE_SIZE,
                        Data.getInstance().PICTURE_SIZE);

                // Устанавливаем уменьшенное изображение в ImageView
                imageView.setImageBitmap(resizedBitmap);

                // Сохраняем уменьшенное изображение в бинарный файл
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, Data.getInstance().QUALITY, stream);
                byteArray = stream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}