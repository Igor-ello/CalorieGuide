package com.obsessed.calorieguide.retrofit.meal;

import java.util.List;

public class Meal {
    int id;
    String meal_name;
    int total_calories;
    int total_proteins;
    int total_fats;
    int total_carbohydrates;
    List<FoodIdQuantity> products_id;
    int author_id;
    String description;
    int likes;

    public Meal(String meal_name, List<FoodIdQuantity> products_id, int author_id, String description) {
        this.meal_name = meal_name;
        this.products_id = products_id;
        this.author_id = author_id;
        this.description = description;
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
                '}';
    }

    public int getId() {
        return id;
    }

    public String getMealName() {
        return meal_name;
    }

    public int getTotalCalories() {
        return total_calories;
    }

    public int getTotalProteins() {
        return total_proteins;
    }

    public int getTotalFats() {
        return total_fats;
    }

    public int getTotalCarbohydrates() {
        return total_carbohydrates;
    }

    public List<FoodIdQuantity> getFoodIds() {
        return products_id;
    }

    public int getAuthorId() {
        return author_id;
    }

    public String getDescription() {
        return description;
    }

    public int getLikes() {
        return likes;
    }
}
