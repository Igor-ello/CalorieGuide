package com.obsessed.calorieguide.tools.convert;

import android.widget.EditText;

import com.obsessed.calorieguide.data.Data;
import com.obsessed.calorieguide.retrofit.food.Food;
import com.obsessed.calorieguide.retrofit.meal.FoodIdQuantity;
import com.obsessed.calorieguide.retrofit.meal.Meal;
import com.obsessed.calorieguide.retrofit.user.RegistrationRequest;
import com.obsessed.calorieguide.retrofit.user.User;

import java.util.ArrayList;
import java.util.List;

public class FillClass {
    public static Food fillFood(ArrayList<EditText> etList, byte[] byteArray) {
        Food food = new Food(
                etList.get(0).getText().toString(),
                etList.get(1).getText().toString(),
                Integer.parseInt(etList.get(2).getText().toString()),
                Integer.parseInt(etList.get(3).getText().toString()),
                Integer.parseInt(etList.get(4).getText().toString()),
                Integer.parseInt(etList.get(5).getText().toString()),
                Data.getInstance().getUser().getId(),
                byteArray);
        return food;
    }

    public static Meal fillMeal(ArrayList<EditText> etList, byte[] byteArray, List<FoodIdQuantity> foodIdQuantities) {
        Meal meal = new Meal(
                etList.get(0).getText().toString(),
                foodIdQuantities,
                Data.getInstance().getUser().getId(),
                etList.get(1).getText().toString(),
                byteArray
                );
        return meal;
    }

    public static RegistrationRequest fillRegistrationRequest(User user) {
        RegistrationRequest registrationRequest = new RegistrationRequest(
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPassword()
        );
        return registrationRequest;
    }
}
