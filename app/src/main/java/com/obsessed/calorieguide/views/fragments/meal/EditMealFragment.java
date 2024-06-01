package com.obsessed.calorieguide.views.fragments.meal;

import static com.obsessed.calorieguide.data.local.Data.DELAY_DEFAULT;
import static com.obsessed.calorieguide.data.local.Data.SORT_DATE;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.data.callback.food.CallbackGetAllFood;
import com.obsessed.calorieguide.data.callback.meal.CallbackDeleteMealById;
import com.obsessed.calorieguide.data.callback.meal.CallbackUpdateMeal;
import com.obsessed.calorieguide.data.local.room.AppDatabase;
import com.obsessed.calorieguide.data.repository.FoodRepo;
import com.obsessed.calorieguide.data.local.Data;
import com.obsessed.calorieguide.data.models.food.Food;
import com.obsessed.calorieguide.data.callback.meal.CallbackGetMealById;
import com.obsessed.calorieguide.data.models.food.FoodIdQuantity;
import com.obsessed.calorieguide.data.models.Meal;
import com.obsessed.calorieguide.data.remote.network.meal.MealCallWithToken;
import com.obsessed.calorieguide.data.repository.MealRepo;
import com.obsessed.calorieguide.tools.Func;
import com.obsessed.calorieguide.tools.convert.FillClass;
import com.obsessed.calorieguide.tools.convert.ResizedBitmap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class EditMealFragment extends Fragment implements CallbackGetMealById, CallbackGetAllFood, CallbackDeleteMealById, CallbackUpdateMeal {
    private static final int GALLERY_REQUEST_CODE = 100;
    byte[] byteArray;
    private static final String ARG_MEAL_ID = "meal_id";
    private int mealId;
    FieldValidation fieldValidation;
    ImageView imageView;
    NavController navController;
    AppDatabase db;
    Handler handler;

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
        db = AppDatabase.getInstance(requireContext());
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_meal, container, false);
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

        //Подгрузка данных
        AppDatabase db = AppDatabase.getInstance(requireContext());
        MealRepo mealRepo = new MealRepo(db.mealDao());
        mealRepo.getMealById(mealId, this);

        FoodRepo repo = new FoodRepo(db.foodDao());
        repo.getAllFood(SORT_DATE, 1,  this);

        view.findViewById(R.id.btDelete).setOnClickListener(v -> {
            MealCallWithToken mealCallWithToken = new MealCallWithToken(Data.getInstance().getUser().getBearerToken());
            mealCallWithToken.deleteMealById(mealId, this);

            requireView().findViewById(R.id.lnMain).setVisibility(View.GONE);
            requireView().findViewById(R.id.arrow_back).setVisibility(View.GONE);
            requireView().findViewById(R.id.loading).setVisibility(View.VISIBLE);
            Func.setTimeLimitLoading(handler, DELAY_DEFAULT, requireContext(), view, requireActivity());
        });

        // Подгрузка изображения из галереи или камеры
        imageView.setOnClickListener(v -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
        });

        view.findViewById(R.id.btSetNumber).setOnClickListener(v -> {
            //repo.getAllFood(SORT_LIKE_DESCENDING, 1, this);
            fieldValidation.fillLnFood(foodList, meal.getFoodIdQuantities());
        });

        // Отправка на сервер введенных данных
        requireView().findViewById(R.id.btAdd).setOnClickListener(v -> {
            ArrayList<EditText> etList;
            ArrayList<FoodIdQuantity> foodIdQuantities;
            try {
                etList = fieldValidation.getEtList();
            }
            catch (IllegalArgumentException | NullPointerException e) {
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

                requireView().findViewById(R.id.lnMain).setVisibility(View.GONE);
                requireView().findViewById(R.id.arrow_back).setVisibility(View.GONE);
                requireView().findViewById(R.id.loading).setVisibility(View.VISIBLE);
                Func.setTimeLimitLoading(handler, DELAY_DEFAULT, requireContext(), view, requireActivity());
                mealCallWithToken.updateMeal(mealId, FillClass.fillMeal(etList, byteArray, foodIdQuantities), this);
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
        if(isAdded()) {
            requireActivity().runOnUiThread(() -> {
                if (meal != null && foodList != null) {
                    fieldValidation.setValues(foodList, meal);
                }
            });
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

    @Override
    public void onRemoteDeleteMealById() {
        handler.removeCallbacksAndMessages(null); // Отменяем таймер
        MealRepo repo = new MealRepo(db.mealDao());
        repo.deleteMealById(mealId, this);
    }

    @Override
    public void onLocalDeleteMealById() {
        if (isAdded()) {
            requireActivity().runOnUiThread(() -> {
                Navigation.findNavController(requireView()).popBackStack();
            });
        }
    }

    @Override
    public void onMealUpdatedRemote(Meal meal) {
        handler.removeCallbacksAndMessages(null);
        MealRepo repo  = new MealRepo(db.mealDao());
        repo.updateMeal(meal, this);
    }

    @Override
    public void onMealUpdatedLocal() {
        if(isAdded()) {
            requireActivity().runOnUiThread(() -> {
                Navigation.findNavController(requireView()).popBackStack();
            });
        }
    }
}