package com.obsessed.calorieguide.data.repository.async_task.food;

import android.os.AsyncTask;

import com.obsessed.calorieguide.data.callback.food.CallbackGetAllFood;
import com.obsessed.calorieguide.data.local.dao.FoodDao;
import com.obsessed.calorieguide.data.models.food.Food;

import java.util.ArrayList;
import java.util.List;

public class GetAllFoodTask extends AsyncTask<Void, Void, List<Food>> {
    private final FoodDao foodDao;
    private final String sortType;
    private final int twoDecade;
    private final int userId;
    private Exception exception;
    private CallbackGetAllFood callback;

    public GetAllFoodTask(FoodDao foodDao, String sortType, int twoDecade, int userId, CallbackGetAllFood callback) {
        this.foodDao = foodDao;
        this.sortType = sortType;
        this.twoDecade = twoDecade;
        this.userId = userId;
        this.callback = callback;
    }

    @Override
    protected List<Food> doInBackground(Void... voids) {
        List<Food> foodList = null;
        int offset = (twoDecade - 1) * 20;
        int limit = 20;

        try {
            switch (sortType) {
                case "likesAsc":
                    foodList = foodDao.getFoodByLikesAsc(offset, limit);
                    break;
                case "likesDesc":
                    foodList = foodDao.getFoodByLikesDesc(offset, limit);
                    break;
                case "fromOldest":
                    foodList = foodDao.getFoodFromOldest(offset, limit);
                    break;
                case "fromNewest":
                default:
                    foodList = foodDao.getFoodFromNewest(offset, limit);
                    break;
            }

            if (userId != 0 && foodList != null) {
                for (Food food : foodList) {
                    boolean isLiked = foodDao.doesUserLikeFood(userId, food.getId());
                    food.setIsLiked(isLiked);
                }
            }
        } catch (Exception e) {
            this.exception = e;
        }
        return foodList;
    }

    @Override
    protected void onPostExecute(List<Food> foodList) {
        if (exception != null) {
            // Handle the error
        } else {
            callback.onAllFoodReceived(new ArrayList<>(foodList));
        }
    }
}