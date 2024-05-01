package com.obsessed.calorieguide.fragments.meal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.retrofit.food.Food;
import com.obsessed.calorieguide.retrofit.meal.FoodIdQuantity;
import com.obsessed.calorieguide.tools.ViewFactory;

import java.util.ArrayList;
import java.util.List;

public class FieldValidation {
    View view;
    ArrayList<EditText> etList;
    EditText etMealName, etDescription, etNumberOfIng;

    //For lnFood and getFoodIdQuantities
    ArrayList<Spinner> spinnerList;
    ArrayList<EditText> editTextList;
    LinearLayout lnFood;

    public FieldValidation(View view) {
        this.view = view;

        init(view);
    }

    private void init(View view){
        etList = new ArrayList<>();
        etList.add(view.findViewById(R.id.etMealName));
        etList.add(view.findViewById(R.id.etDescription));

        etNumberOfIng = view.findViewById(R.id.etNumberOfIng);
        lnFood = view.findViewById(R.id.lnFood);
    }

    public void fillLnFood(Context context, List<String> foodList){
        // Класс для создания новых элементов
        ViewFactory viewFactory = new ViewFactory(context);
        spinnerList = new ArrayList<>();
        editTextList = new ArrayList<>();

        int numberOfElements = Integer.parseInt(etNumberOfIng.getText().toString());
        ArrayAdapter<String> adapter = new ArrayAdapter(context,
                android.R.layout.simple_spinner_item, foodList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Создаем и добавляем нужное количество элементов
        for (int i = 0; i < numberOfElements; i++) {
            // Создаем новый LinearLayout для каждого элемента
            LinearLayout linearLayout = viewFactory.createLinearLayout();

            // Создаем и добавляем Spinner
            Spinner spinner = viewFactory.createSpinner();
            spinner.setAdapter(adapter);
            spinnerList.add(spinner);
            linearLayout.addView(spinner);

            // Создаем и добавляем EditText
            EditText editText = viewFactory.createEditText();
            editTextList.add(editText);
            linearLayout.addView(editText);

            // Добавляем созданный LinearLayout в существующий LinearLayout lnSpinners
            lnFood.addView(linearLayout);
        }
    }

    public void setValues(Food food) {
        for (int i = 0; i < etList.size(); i++) {
            etList.get(i).setText(food.getValues().get(i).toString());
        }
    }

    public ArrayList<EditText> getEtList(){
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

    public ArrayList<FoodIdQuantity> getFoodIdQuantities(Context context) {
        ArrayList<FoodIdQuantity> foodIdQuantities = new ArrayList<>();

        // Проверка наличия данных в списках Spinner и EditText
        if (spinnerList.size() == editTextList.size()) {
            // Перебор элементов в списках Spinner и EditText
            for (int i = 0; i < spinnerList.size(); i++) {
                Spinner spinner = spinnerList.get(i);
                EditText editText = editTextList.get(i);

                // Проверка на пустое значение в EditText
                String editTextValue = editText.getText().toString().trim();
                if (editTextValue.isEmpty()) {
                    // Вывод Toast о пустом поле
                    Toast.makeText(context, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
                    // Очищаем список и выходим из метода
                    foodIdQuantities.clear();
                    return foodIdQuantities;
                }

                // Получаем выбранное значение из Spinner и текст из EditText
                int productId = spinner.getSelectedItemPosition();
                int quantity = Integer.parseInt(editTextValue);

                // Создаем объект FoodIdQuantity и добавляем его в список
                FoodIdQuantity foodIdQuantity = new FoodIdQuantity(productId, quantity);
                foodIdQuantities.add(foodIdQuantity);
            }
        }

        return foodIdQuantities;
    }

}
