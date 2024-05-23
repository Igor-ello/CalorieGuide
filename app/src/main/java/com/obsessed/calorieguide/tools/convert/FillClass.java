package com.obsessed.calorieguide.tools.convert;

import android.widget.EditText;

import com.obsessed.calorieguide.data.local.Data;
import com.obsessed.calorieguide.data.models.food.Food;
import com.obsessed.calorieguide.data.models.food.FoodIdQuantity;
import com.obsessed.calorieguide.data.models.Meal;
import com.obsessed.calorieguide.data.remote.network.user.RegistrationRequest;
import com.obsessed.calorieguide.data.models.User;

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
                user.getUser_name(),
                user.getSurname(),
                user.getEmail(),
                user.getPassword(),
                user.getCalories_goal(),
                user.getCarbohydrates_goal(),
                user.getProteins_goal(),
                user.getFats_goal()
        );
        return registrationRequest;
    }
}
