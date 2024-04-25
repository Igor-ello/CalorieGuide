package com.obsessed.calorieguide.food_fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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

import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.convert.FillClass;
import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.retrofit.food.FoodCallPost;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class AddFoodFragment extends Fragment {
    // Константа для определения requestCode
    private static final int GALLERY_REQUEST_CODE = 100;
    ImageView imageView;
    byte[] byteArray;
    FieldValidation fieldValidation;

    public AddFoodFragment() {
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
        return inflater.inflate(R.layout.fragment_add_food, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Инициализируем поля
        init(view);

        // Подгрузка изображения из галереи или камеры
        imageView.setOnClickListener(v -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
        });

        // Отправка на сервер введенных данных
        requireView().findViewById(R.id.btSave).setOnClickListener(v -> {
            ArrayList<EditText> etList = fieldValidation.getValues();
            if(etList != null){
                FoodCallPost foodCall = new FoodCallPost(Data.getInstance().getUser().getBearerToken());
                foodCall.postFood(FillClass.fillFood(etList, byteArray));

                Navigation.findNavController(view).popBackStack();
            } else {
                Toast.makeText(requireContext(), "Fill in all the fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init(View view){
        imageView = view.findViewById(R.id.image);
        fieldValidation = new FieldValidation(requireView());
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
                Bitmap resizedBitmap = FieldValidation.getResizedBitmap(originalBitmap,
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