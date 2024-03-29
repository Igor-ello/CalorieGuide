package com.obsessed.calorieguide.retrofit;

import android.graphics.Bitmap;

import java.util.List;

public class Food {
    //TODO взять нужную таблицу
//    int id;
//    String food_name;
//    String description;
//    int calories;
//    int proteins;
//    int carbohydrates;
//    int fats;
//    int likes;
//    Bitmap picture;
//
//    //Конструктор для адаптера
//    public Food(String food_name, int calories, int proteins, int carbohydrates, int fats, int likes, Bitmap picture) {
//        this.food_name = food_name;
//        this.calories = calories;
//        this.proteins = proteins;
//        this.carbohydrates = carbohydrates;
//        this.fats = fats;
//        this.likes = likes;
//        this.picture = picture;
//    }

    //__________ТЕСТОВЫЕ_ДАННЫЕ______________
    int id;
    String title;
    String description;
    int price;
    float discountPercentage;
    float rating;
    int stock;
    String brand;
    String category;
    String thumbnail;
    List<String> images;

    //Конструктор для адаптера
    public Food(String title, int price, int stock, String brand, List<String> images) {
        this.title = title;
        this.price = price;
        this.stock = stock;
        this.brand = brand;
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getBrand() {
        return brand;
    }

    public String getImage() {
        return images.get(0);
    }
}
