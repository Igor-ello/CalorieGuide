package com.obsessed.calorieguide.views.fragments.food;

import android.view.View;
import android.widget.EditText;

import com.obsessed.calorieguide.R;
import com.obsessed.calorieguide.data.models.food.Food;

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
        etList.add(view.findViewById(R.id.etFoodName));
        etList.add(view.findViewById(R.id.etDescription));
        etList.add(view.findViewById(R.id.etCalories));
        etList.add(view.findViewById(R.id.etProteins));
        etList.add(view.findViewById(R.id.etCarbohydrates));
        etList.add(view.findViewById(R.id.etFats));
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
}
