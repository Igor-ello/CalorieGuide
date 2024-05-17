package com.obsessed.calorieguide.data.models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Arrays;

@Entity(tableName = "food_table")
public class Food {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String food_name;
    private String description;
    private int calories;
    private int proteins;
    private int carbohydrates;
    private int fats;
    private int author_id;
    private int likes;
    private byte[] picture;
    private boolean isLiked;


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
        return arrayList;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", food_name='" + food_name + '\'' +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", proteins=" + proteins +
                ", carbohydrates=" + carbohydrates +
                ", fats=" + fats +
                ", author_id=" + author_id +
                ", likes=" + likes +
                ", picture=" + Arrays.toString(picture) +
                ", isLiked=" + isLiked +
                '}';
    }

    //Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getFood_name() {
        return food_name;
    }
    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }
    public void setCalories(int calories) {
        this.calories = calories;
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

    public int getAuthor_id() {
        return author_id;
    }
    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
    public int getLikes() {
        return likes;
    }

    public byte[] getPicture() {
        return picture;
    }
    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public boolean getIsLiked() {
        return isLiked;
    }
    public void setIsLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }

}
