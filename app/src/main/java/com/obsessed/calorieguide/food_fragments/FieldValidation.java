package com.obsessed.calorieguide.food_fragments;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.View;
import android.widget.EditText;

import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.retrofit.food.Food;

import java.util.ArrayList;

public class FieldValidation {
    View view;
    ArrayList<EditText> etList;

    public FieldValidation(View view) {
        this.view = view;

        init(view);
    }

    private void init(View view){
        etList = new ArrayList<>();
        etList.add(view.findViewById(R.id.edFoodName));
        etList.add(view.findViewById(R.id.edDescription));
        etList.add(view.findViewById(R.id.edCalories));
        etList.add(view.findViewById(R.id.edProteins));
        etList.add(view.findViewById(R.id.edCarbohydrates));
        etList.add(view.findViewById(R.id.edFats));
    }

    public void setValues(Food food) {
        for (int i = 0; i < etList.size(); i++) {
            etList.get(i).setText(food.getValues().get(i).toString());
        }
    }

    public ArrayList<EditText> getValues(){
        int counter = 0;
        for (EditText et: etList){
            et.setText(et.getText().toString().trim());
            if (!et.getText().toString().isEmpty())
                counter++;
        }
        if(counter == etList.size()){
            return etList;
        } else {
            return null;
        }
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
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
