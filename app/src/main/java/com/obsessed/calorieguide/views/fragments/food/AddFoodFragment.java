package com.obsessed.calorieguide.views.fragments.food;

import static com.obsessed.calorieguide.data.local.Data.DELAY_DEFAULT;

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

import android.os.Handler;
import android.os.Looper;
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
import com.obsessed.calorieguide.data.callback.food.CallbackAddFood;
import com.obsessed.calorieguide.data.local.room.AppDatabase;
import com.obsessed.calorieguide.data.models.food.Food;
import com.obsessed.calorieguide.data.remote.network.food.FoodCallWithToken;
import com.obsessed.calorieguide.data.repository.FoodRepo;
import com.obsessed.calorieguide.tools.Func;
import com.obsessed.calorieguide.tools.convert.FillClass;
import com.obsessed.calorieguide.data.local.Data;
import com.obsessed.calorieguide.tools.convert.ResizedBitmap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class AddFoodFragment extends Fragment implements CallbackAddFood {
    // Константа для определения requestCode
    private static final int GALLERY_REQUEST_CODE = 100;
    ImageView imageView;
    byte[] byteArray;
    FieldValidation fieldValidation;
    NavController navController;
    AppDatabase db;
    Handler handler;

    public AddFoodFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = AppDatabase.getInstance(requireContext());
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_food, container, false);
        ((BottomNavigationView)((MainActivityApp) getActivity()).findViewById(R.id.bottomNV)).setVisibility(view.GONE);
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

        // Отправка на сервер введенных данных
        requireView().findViewById(R.id.btAdd).setOnClickListener(v -> {
            ArrayList<EditText> etList = fieldValidation.getValues();
            if(etList != null){
                FoodCallWithToken call = new FoodCallWithToken(Data.getInstance().getUser().getBearerToken());
                try {
                    call.postFood(FillClass.fillFood(etList, byteArray), this);

                    requireView().findViewById(R.id.lnMain).setVisibility(View.GONE);
                    requireView().findViewById(R.id.arrow_back).setVisibility(View.GONE);
                    requireView().findViewById(R.id.loading).setVisibility(View.VISIBLE);
                    Func.setTimeLimitLoading(handler, DELAY_DEFAULT, requireContext(), view, requireActivity());
                }
                catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Invalid values", Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                Toast.makeText(requireContext(), "Fill in all the fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init(View view){
        imageView = view.findViewById(R.id.image);
        fieldValidation = new FieldValidation(requireView());
        navController = Navigation.findNavController(view);
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
    public void onAddFoodRemote(Food food) {
        handler.removeCallbacksAndMessages(null);
        FoodRepo repo  = new FoodRepo(db.foodDao());
        repo.addFood(food, this);
    }

    @Override
    public void onAddFoodLocal() {
        if(isAdded()) {
            requireActivity().runOnUiThread(() -> {
                Navigation.findNavController(requireView()).popBackStack();
            });
        }
    }
}