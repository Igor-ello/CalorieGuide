package com.obsessed.calorieguide.retrofit;

import android.graphics.Bitmap;

public class Food {
    int id;
    String food_name;
    String description;
    int calories;
    int proteins;
    int carbohydrates;
    int fats;
    int likes;
    Bitmap picture;


    public Food(int id, String food_name, String description, int calories, int proteins, int carbohydrates, int fats, int likes, Bitmap picture) {
        this.id = id;
        this.food_name = food_name;
        this.description = description;
        this.calories = calories;
        this.proteins = proteins;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
        this.likes = likes;
        this.picture = picture;
    }

    public int getId() {
        return id;
    }

    public String getFood_name() {
        return food_name;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public int getProteins() {
        return proteins;
    }

    public int getCarbohydrates() {
        return carbohydrates;
    }

    public int getFats() {
        return fats;
    }

    public int getLikes() {
        return likes;
    }

    public Bitmap getPicture() {
        return picture;
    }
}
