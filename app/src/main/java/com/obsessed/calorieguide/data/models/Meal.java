package com.obsessed.calorieguide.data.models;

import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.obsessed.calorieguide.data.local.room.converters.FoodIdQuantityConverter;
import com.obsessed.calorieguide.data.models.day.Intake;
import com.obsessed.calorieguide.data.models.food.FoodIdQuantity;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "meal_table")
@TypeConverters({FoodIdQuantityConverter.class})
public class Meal extends Intake {
    private String meal_name;
    private List<FoodIdQuantity> products_id;
    private int total_calories;
    private int total_proteins;
    private int total_fats;
    private int total_carbohydrates;

    public Meal() {
        this.type = "meal";
    }

    public Meal(String meal_name, List<FoodIdQuantity> products_id, int author_id, String description, byte[] picture) {
        this.type = "meal";
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
                ", type='" + type + '\'' +
                ", meal_name='" + meal_name + '\'' +
                ", total_calories=" + total_calories +
                ", total_proteins=" + total_proteins +
                ", total_fats=" + total_fats +
                ", total_carbohydrates=" + total_carbohydrates +
                ", products_id=" + products_id +
                ", author_id=" + author_id +
                ", description='" + description + '\'' +
                ", likes=" + likes +
                ", picture=" + (picture != null ? "exists" : "null") +
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

    public void setProducts_id(List<FoodIdQuantity> products_id) {
        this.products_id = products_id;
    }
}
