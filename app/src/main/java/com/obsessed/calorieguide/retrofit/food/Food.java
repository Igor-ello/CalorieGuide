package com.obsessed.calorieguide.retrofit.food;


import java.util.ArrayList;
import java.util.Arrays;

public class Food {
    int id;
    String food_name;
    String description;
    int calories;
    int proteins;
    int carbohydrates;
    int fats;
    int author_id;
    int likes;
    byte[] picture;


    public Food(String food_name, String description, int calories, int proteins, int carbohydrates, int fats, int author_id, byte[] picture) {
        this.food_name = food_name;
        this.description = description;
        this.calories = calories;
        this.proteins = proteins;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
        this.author_id = author_id;
        this.picture = picture;
    }

    public ArrayList<Object> getValues() {
        ArrayList<Object> arrayList = new ArrayList<>();
        arrayList.add(food_name);
        arrayList.add(description);
        arrayList.add(calories);
        arrayList.add(proteins);
        arrayList.add(carbohydrates);
        arrayList.add(fats);
        arrayList.add(picture);
        return arrayList;
    }

    @Override
    public String toString() {
        return "Food{" +
                "food_name='" + food_name + '\'' +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", proteins=" + proteins +
                ", carbohydrates=" + carbohydrates +
                ", fats=" + fats +
                ", author_id=" + author_id +
                ", likes=" + likes +
                ", picture=" + Arrays.toString(picture) +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getFoodName() {
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

    public int getAuthorId() {
        return author_id;
    }

    public int getLikes() {
        return likes;
    }

    public byte[] getPicture() {
        return picture;
    }


}
