package com.obsessed.calorieguide.data.repository.async_task.meal;

import android.os.AsyncTask;

import com.obsessed.calorieguide.data.callback.meal.CallbackGetAllMeal;
import com.obsessed.calorieguide.data.callback.meal.CallbackSearchMeal;
import com.obsessed.calorieguide.data.local.dao.MealDao;
import com.obsessed.calorieguide.data.models.Meal;
import com.obsessed.calorieguide.data.models.food.Food;
import com.obsessed.calorieguide.tools.Algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchMealsTask extends AsyncTask<Void, Void, List<Meal>> {
    private final MealDao mealDao;
    private final String word;
    private final int userId;
    private final int maxDifference;
    private final CallbackSearchMeal callback;
    private Exception exception;

    public SearchMealsTask(MealDao mealDao, String word, int userId, int maxDifference, CallbackSearchMeal callback) {
        this.mealDao = mealDao;
        this.word = word;
        this.userId = userId;
        this.maxDifference = maxDifference;
        this.callback = callback;
    }

    @Override
    protected List<Meal> doInBackground(Void... voids) {
//        List<Meal> mealsByName = mealDao.searchMealsByName(word);
//        List<Meal> mealsByDescription = mealDao.searchMealsByDescription(word);
        List<Meal> mealsByName = mealDao.getAllMeal();
        List<Meal> mealsByDescription = mealsByName;
        Set<Integer> includedIds = new HashSet<>();
        List<Meal> result = new ArrayList<>();

        try {
            for (Meal meal : mealsByName) {
                boolean nameSimilarity = Algorithm.getInstance().isSimilar(meal.getMeal_name(), word, maxDifference);
                if (nameSimilarity) {
                    if (!includedIds.contains(meal.getId())) {
                        result.add(meal);
                        includedIds.add(meal.getId());
                    }
                }
            }

            for (Meal meal : mealsByDescription) {
                boolean nameSimilarity = Algorithm.getInstance().isSimilar(meal.getMeal_name(), word, maxDifference);
                if (nameSimilarity) {
                    if (!includedIds.contains(meal.getId())) {
                        result.add(meal);
                        includedIds.add(meal.getId());
                    }
                }
            }
        } catch (Exception e) {
            this.exception = e;
        }

        return result;
    }

    @Override
    protected void onPostExecute(List<Meal> meals) {
        if (exception != null) {
            // Handle the error
        } else {
            callback.mealSearchReceived(new ArrayList<>(meals));
        }
    }
}
