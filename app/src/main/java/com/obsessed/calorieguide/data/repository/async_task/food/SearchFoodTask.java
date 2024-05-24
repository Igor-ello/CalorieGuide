package com.obsessed.calorieguide.data.repository.async_task.food;

import android.os.AsyncTask;

import com.obsessed.calorieguide.data.callback.food.CallbackSearchFood;
import com.obsessed.calorieguide.data.local.dao.FoodDao;
import com.obsessed.calorieguide.data.models.food.Food;
import com.obsessed.calorieguide.tools.Algorithm;

import java.util.ArrayList;
import java.util.List;

public class SearchFoodTask extends AsyncTask<Void, Void, List<Food>> {
    private final FoodDao foodDao;
    private final String word;
    private final int userId;
    private final int maxDifference;
    private final CallbackSearchFood callback;
    private Exception exception;

    public SearchFoodTask(FoodDao foodDao, String word, int userId, int maxDifference, CallbackSearchFood callback) {
        this.foodDao = foodDao;
        this.word = word;
        this.userId = userId;
        this.maxDifference = maxDifference;
        this.callback = callback;
    }

    @Override
    protected List<Food> doInBackground(Void... voids) {
//        List<Food> foodsByName = foodDao.searchFoodByName(word);
//        List<Food> foodsByDescription = foodDao.searchFoodByDescription(word);
        List<Food> foodsByName = foodDao.getAllFood(); // Получаем все продукты из базы данных
        List<Food> foodsByDescription = foodsByName; // Получаем все продукты из базы данных
        List<Food> result = new ArrayList<>();

        try {
            // Проходимся по результатам поиска по имени
            for (Food food : foodsByName) {
                // Проверяем сходство имени продукта с поисковым запросом
                boolean nameSimilarity = Algorithm.getInstance().isSimilar(food.getFood_name(), word, maxDifference);
                if (nameSimilarity) {
                    // Проверяем, был ли продукт уже добавлен в результаты
                    if (!result.contains(food)) {
                        result.add(food);
                    }
                }
            }

            // Проходимся по результатам поиска по описанию
            for (Food food : foodsByDescription) {
                // Проверяем сходство описания продукта с поисковым запросом
                boolean descriptionSimilarity = Algorithm.getInstance().isSimilar(food.getDescription(), word, maxDifference);
                if (descriptionSimilarity) {
                    // Проверяем, был ли продукт уже добавлен в результаты
                    if (!result.contains(food)) {
                        result.add(food);
                    }
                }
            }
        } catch (Exception e) {
            this.exception = e;
        }

        return result;
    }

    @Override
    protected void onPostExecute(List<Food> foodList) {
        if (exception != null) {
            // Handle the error
        } else {
            callback.foodSearchReceived(new ArrayList<>(foodList));
        }
    }
}