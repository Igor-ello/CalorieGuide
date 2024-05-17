package com.obsessed.calorieguide.views.fragments.meal;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.network.food.Food;
import com.obsessed.calorieguide.network.meal.FoodIdQuantity;
import com.obsessed.calorieguide.network.meal.Meal;
import com.obsessed.calorieguide.tools.ViewFactory;

import java.util.ArrayList;
import java.util.List;

public class FieldValidation {
    View view;
    Context context;

    List<String> foodNames;
    List<Food> foodList;

    ArrayList<EditText> etList;
    EditText etNumberOfIng;

    //For lnFood and getFoodIdQuantities
    ArrayList<Spinner> spinnerList;
    ArrayList<EditText> editTextList;
    LinearLayout lnFood;

    public FieldValidation(Context context, View view) {
        this.context = context;
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

    public void fillLnFood(List<Food> foodList, List<FoodIdQuantity> foodIdQuantityList){
        // Класс для создания новых элементов
        ViewFactory viewFactory = new ViewFactory(context);
        spinnerList = new ArrayList<>();
        editTextList = new ArrayList<>();
        this.foodList = foodList;
        lnFood.removeAllViews();

        foodNames = new ArrayList<>();
        for (Food food : foodList) {
            foodNames.add(food.getFoodName());
        }

        int numberOfElements = Integer.parseInt(etNumberOfIng.getText().toString());
        ArrayAdapter<String> adapter = new ArrayAdapter(context,
                android.R.layout.simple_spinner_item, foodNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Создаем и добавляем нужное количество элементов
        for (int i = 0; i < numberOfElements; i++) {
            LinearLayout linearLayout = viewFactory.createLinearLayout(); // Создаем новый LinearLayout для каждого элемента
            Spinner spinner = viewFactory.createSpinner(); // Создаем Spinner
            EditText editText = viewFactory.createEditText(); // Создаем EditText

            spinner.setAdapter(adapter);
            spinnerList.add(spinner);
            linearLayout.addView(spinner);

            editTextList.add(editText);
            linearLayout.addView(editText);

            if(foodIdQuantityList != null) {
                spinner.setSelection(findPositionById(foodIdQuantityList.get(i).getProductId()));
                editText.setText(foodIdQuantityList.get(i).getQuantity() + "");
            }


            // Добавляем созданный LinearLayout в существующий LinearLayout lnSpinners
            lnFood.addView(linearLayout);
        }
    }

    public void setValues(List<Food> foodList, Meal meal) {
        for (int i = 0; i < etList.size(); i++) {
            etList.get(i).setText(meal.getValues().get(i).toString());
        }
        List<FoodIdQuantity> foodIdQuantities = meal.getFoodIdQuantities();
        etNumberOfIng.setText(String.valueOf(foodIdQuantities.size()));
        fillLnFood(foodList, foodIdQuantities);
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

    public ArrayList<FoodIdQuantity> getFoodIdQuantities() {
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
                int productId = foodList.get(spinner.getSelectedItemPosition()).getId();
                int quantity = Integer.parseInt(editTextValue);

                // Создаем объект FoodIdQuantity и добавляем его в список
                FoodIdQuantity foodIdQuantity = new FoodIdQuantity(productId, quantity);
                foodIdQuantities.add(foodIdQuantity);
            }
        }

        return foodIdQuantities;
    }

    private int findPositionById(int productId) {
        for (int j = 0; j < foodList.size(); j++) {
            if (foodList.get(j).getId() == productId) {
                return j;
            }
        }
        return 0;
    }


}
