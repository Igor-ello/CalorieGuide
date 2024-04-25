package com.obsessed.calorieguide.food_fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
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
    ArrayList<EditText> etList;
    byte[] byteArray;
    ImageView img;

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

        // Инициализируем полей
        init(requireView());

        // Подгрузка изображения из галереи или камеры
        requireView().findViewById(R.id.image).setOnClickListener(v -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
        });

        // Отправка на сервер введенных данных
        requireView().findViewById(R.id.btSave).setOnClickListener(v -> {
            int counter = 0;
            for (EditText et: etList){
                et.setText(et.getText().toString().trim());
                if (!et.getText().toString().isEmpty())
                    counter++;
            }
            if(counter == etList.size()){
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
        etList = new ArrayList<>();
        etList.add(view.findViewById(R.id.edFoodName));
        etList.add(view.findViewById(R.id.edDescription));
        etList.add(view.findViewById(R.id.edCalories));
        etList.add(view.findViewById(R.id.edProteins));
        etList.add(view.findViewById(R.id.edCarbohydrates));
        etList.add(view.findViewById(R.id.edFats));
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

                // Уменьшаем размер Bitmap до 125x125 dp
                Bitmap resizedBitmap = getResizedBitmap(originalBitmap, 200, 200);

                // Устанавливаем уменьшенное изображение в ImageView
                imageView.setImageBitmap(resizedBitmap);

                // Сохраняем уменьшенное изображение в бинарный файл
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byteArray = stream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // Создаем матрицу для масштабирования
        Matrix matrix = new Matrix();

        // Масштабируем изображение с матрицей
        matrix.postScale(scaleWidth, scaleHeight);

        // Пересоздаем изображение с новыми размерами
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
    }

}