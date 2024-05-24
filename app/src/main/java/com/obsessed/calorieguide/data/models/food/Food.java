package com.obsessed.calorieguide.data.models.food;


import androidx.room.Entity;

import com.obsessed.calorieguide.data.models.Intake;

import java.util.ArrayList;

@Entity(tableName = "food_table")
public class Food extends Intake {
    private String food_name;
    private int proteins;
    private int carbohydrates;
    private int fats;

    public Food() {
        this.type = "food";
    }

    public Food(String food_name, String description, int calories, int proteins, int carbohydrates, int fats, int author_id, byte[] picture) {
        this.type = "food";
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
        return arrayList;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", food_name='" + food_name + '\'' +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", proteins=" + proteins +
                ", carbohydrates=" + carbohydrates +
                ", fats=" + fats +
                ", author_id=" + author_id +
                ", likes=" + likes +
                ", picture=" + (picture != null ? "exists" : "null") +
                ", isLiked=" + isLiked +
                '}';
    }

    //Getters and Setters
    public String getFood_name() {
        return food_name;
    }
    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public int getProteins() {
        return proteins;
    }
    public void setProteins(int proteins) {
        this.proteins = proteins;
    }

    public int getCarbohydrates() {
        return carbohydrates;
    }
    public void setCarbohydrates(int carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public int getFats() {
        return fats;
    }
    public void setFats(int fats) {
        this.fats = fats;
    }
}
