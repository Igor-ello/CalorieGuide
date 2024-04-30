package com.obsessed.calorieguide.retrofit.meal;

public class Meal {
    int id;
    String meal_name;
    int total_calories;
    int total_proteins;
    int total_fats;
    int total_carbohydrates;
    int[] products_id;
    String author_id;
    String description;
    int likes;

    public Meal(String meal_name, int[] products_id, String author_id, String description) {
        this.meal_name = meal_name;
        this.products_id = products_id;
        this.author_id = author_id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getMeal_name() {
        return meal_name;
    }

    public int getTotal_calories() {
        return total_calories;
    }

    public int getTotal_proteins() {
        return total_proteins;
    }

    public int getTotal_fats() {
        return total_fats;
    }

    public int getTotal_carbohydrates() {
        return total_carbohydrates;
    }

    public int[] getProducts_id() {
        return products_id;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public String getDescription() {
        return description;
    }

    public int getLikes() {
        return likes;
    }
}
