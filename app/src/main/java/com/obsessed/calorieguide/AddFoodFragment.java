package com.obsessed.calorieguide;

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

import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.retrofit.food.Food;
import com.obsessed.calorieguide.retrofit.food.FoodCallPost;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class AddFoodFragment extends Fragment {
    // Константа для определения requestCode
    private static final int GALLERY_REQUEST_CODE = 100;
    ImageView imageView;
    ArrayList<EditText> edList;
    byte[] byteArray;

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

        init(requireView());

        requireView().findViewById(R.id.image).setOnClickListener(v -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
        });

        requireView().findViewById(R.id.btSave).setOnClickListener(v -> {
            int counter = 0;
            for (EditText ed: edList){
                String text = ed.getText().toString().trim(); // Получаем текст из EditText и удаляем пробелы по краям
                if (!text.isEmpty())
                    counter++;
            }
            if(counter == 6) {
                Food food = new Food(
                        edList.get(0).getText().toString(),
                        edList.get(1).getText().toString(),
                        Integer.parseInt(edList.get(2).getText().toString()),
                        Integer.parseInt(edList.get(3).getText().toString()),
                        Integer.parseInt(edList.get(4).getText().toString()),
                        Integer.parseInt(edList.get(5).getText().toString()),
                        Data.getInstance().getUser().getId(),
                        byteArray);
                FoodCallPost foodCall = new FoodCallPost(Data.getInstance().getUser().getBearerToken());
                foodCall.postFood(food);

                Navigation.findNavController(view).popBackStack();
            } else {
                Toast.makeText(requireContext(), "Fill in all the fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init(View view){
        imageView = view.findViewById(R.id.image);
        edList = new ArrayList<>();
        edList.add(view.findViewById(R.id.edFoodName));
        edList.add(view.findViewById(R.id.edDescription));
        edList.add(view.findViewById(R.id.edCalories));
        edList.add(view.findViewById(R.id.edProteins));
        edList.add(view.findViewById(R.id.edCarbohydrates));
        edList.add(view.findViewById(R.id.edFats));
    }



    // Метод для обработки результата выбора изображения из галереи или камеры
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Получаем URI выбранного изображения из галереи
            Uri selectedImageUri = data.getData();

            try {
                // Получаем Bitmap из URI выбранного изображения
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), selectedImageUri);
                imageView.setImageBitmap(bitmap);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byteArray = stream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}