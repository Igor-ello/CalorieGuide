package com.obsessed.calorieguide.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Arrays;

@Entity(tableName = "user_table")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String user_name;
    private String surname;
    private String email;
    private String password;
    private String BearerToken;
    private byte[] picture;
    private int lunch_id;
    private int breakfast_id;
    private int dinner_id;
    private int calories_goal;
    private int carbohydrates_goal;
    private int fats_goal;
    private int proteins_goal;
    private int calories_current;
    private int carbohydrates_current = 0;
    private int fats_current = 0;
    private int proteins_current = 0;

    public User() {}

    public User(int id, String name, String surname, String email, String password, String BearerToken) {
        this.id = id;
        this.user_name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.BearerToken = BearerToken;
    }

    public User(int id, String user_name, String surname, String email, String password, String bearerToken, int calories_goal, int carbohydrates_goal, int proteins_goal, int fats_goal) {
        this.id = id;
        this.user_name = user_name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        BearerToken = bearerToken;
        //TODO this.picture = picture;
        this.calories_goal = calories_goal;
        this.carbohydrates_goal = carbohydrates_goal;
        this.proteins_goal = proteins_goal;
        this.fats_goal = fats_goal;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", user_name='" + user_name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", BearerToken=" + (BearerToken != null ? "exists" : "null") +
                ", picture=" + (picture != null ? "exists" : "null") +
                ", lunch_id=" + lunch_id +
                ", breakfast_id=" + breakfast_id +
                ", dinner_id=" + dinner_id +
                ", calories_goal=" + calories_goal +
                ", carbohydrates_goal=" + carbohydrates_goal +
                ", fats_goal=" + fats_goal +
                ", proteins_goal=" + proteins_goal +
                ", calories_current=" + calories_current +
                ", carbohydrates_current=" + carbohydrates_current +
                ", fats_current=" + fats_current +
                ", proteins_current=" + proteins_current +
                '}';
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getBearerToken() {
        return BearerToken;
    }
    public void setBearerToken(String BearerToken) {
        this.BearerToken = BearerToken;
    }

    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


    public byte[] getPicture() {
        return picture;
    }
    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public int getLunch_id() {
        return lunch_id;
    }
    public void setLunch_id(int lunch_id) {
        this.lunch_id = lunch_id;
    }

    public int getBreakfast_id() {
        return breakfast_id;
    }
    public void setBreakfast_id(int breakfast_id) {
        this.breakfast_id = breakfast_id;
    }

    public int getDinner_id() {
        return dinner_id;
    }
    public void setDinner_id(int dinner_id) {
        this.dinner_id = dinner_id;
    }

    public int getCalories_goal() {
        return calories_goal;
    }
    public void setCalories_goal(int calories_goal) {
        this.calories_goal = calories_goal;
    }

    public int getCarbohydrates_goal() {
        return carbohydrates_goal;
    }
    public void setCarbohydrates_goal(int carbohydrates_goal) {
        this.carbohydrates_goal = carbohydrates_goal;
    }

    public int getFats_goal() {
        return fats_goal;
    }
    public void setFats_goal(int fats_goal) {
        this.fats_goal = fats_goal;
    }

    public int getProteins_goal() {
        return proteins_goal;
    }
    public void setProteins_goal(int proteins_goal) {
        this.proteins_goal = proteins_goal;
    }

    public int getCalories_current() {
        return calories_current;
    }
    public void setCalories_current(int calories_current) {
        this.calories_current = calories_current;
    }

    public int getCarbohydrates_current() {
        return carbohydrates_current;
    }
    public void setCarbohydrates_current(int carbonates_current) {
        this.carbohydrates_current = carbonates_current;
    }

    public int getFats_current() {
        return fats_current;
    }
    public void setFats_current(int fats_current) {
        this.fats_current = fats_current;
    }

    public int getProteins_current() {
        return proteins_current;
    }
    public void setProteins_current(int proteins_current) {
        this.proteins_current = proteins_current;
    }
}
