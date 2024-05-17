package com.obsessed.calorieguide.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.obsessed.calorieguide.data.local.room.Converters;
import com.obsessed.calorieguide.network.meal.FoodIdQuantity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity(tableName = "meal_table")
@TypeConverters({Converters.class})
public class Meal {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String meal_name;
    private int total_calories;
    private int total_proteins;
    private int total_fats;
    private int total_carbohydrates;
    private List<FoodIdQuantity> products_id;
    private int author_id;
    private String description;
    private int likes;
    private byte[] picture;
    private boolean isLiked;

    public Meal(String meal_name, List<FoodIdQuantity> products_id, int author_id, String description, byte[] picture) {
        this.meal_name = meal_name;
        this.products_id = products_id;
        this.author_id = author_id;
        this.description = description;
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", meal_name='" + meal_name + '\'' +
                ", total_calories=" + total_calories +
                ", total_proteins=" + total_proteins +
                ", total_fats=" + total_fats +
                ", total_carbohydrates=" + total_carbohydrates +
                ", products_id=" + products_id +
                ", author_id=" + author_id +
                ", description='" + description + '\'' +
                ", likes=" + likes +
                ", picture=" + Arrays.toString(picture) +
                ", isLiked=" + isLiked +
                '}';
    }

    public ArrayList<Object> getValues() {
        ArrayList<Object> arrayList = new ArrayList<>();
        arrayList.add(meal_name);
        arrayList.add(description);
        return arrayList;
    }

    //Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getMeal_name() {
        return meal_name;
    }
    public void setMeal_name(String meal_name) {
        this.meal_name = meal_name;
    }

    public int getTotal_calories() {
        return total_calories;
    }
    public void setTotal_calories(int total_calories) {
        this.total_calories = total_calories;
    }

    public int getTotal_proteins() {
        return total_proteins;
    }
    public void setTotal_proteins(int total_proteins) {
        this.total_proteins = total_proteins;
    }

    public int getTotal_fats() {
        return total_fats;
    }
    public void setTotal_fats(int total_fats) {
        this.total_fats = total_fats;
    }

    public int getTotal_carbohydrates() {
        return total_carbohydrates;
    }
    public void setTotal_carbohydrates(int total_carbohydrates) {
        this.total_carbohydrates = total_carbohydrates;
    }

    public List<FoodIdQuantity> getProducts_id() {
        return products_id;
    }
    public List<FoodIdQuantity> getFoodIdQuantities() {
        return products_id;
    }
    public void setFoodIdQuantities(List<FoodIdQuantity> products_id) {
        this.products_id = products_id;
    }

    public int getAuthor_id() {
        return author_id;
    }
    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikes() {
        return likes;
    }
    public void setLikes(int likes) {
        this.likes = likes;
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
