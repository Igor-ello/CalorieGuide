package com.obsessed.calorieguide.network.meal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Meal {
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

    public List<FoodIdQuantity> getFoodIdQuantities() {
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

    public byte[] getPicture() {
        return picture;
    }

    public boolean getIsLiked() {
        return isLiked;
    }
    public void setIsLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }
}
