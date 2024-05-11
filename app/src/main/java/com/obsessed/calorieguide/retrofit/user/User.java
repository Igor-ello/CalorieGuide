package com.obsessed.calorieguide.retrofit.user;

import java.util.Arrays;

public class User {
    int id;
    String user_name;
    String surname;
    String email;
    String password;
    String BearerToken;
    byte[] picture;
    int lunch_id;
    int breakfast_id;
    int dinner_id;
    int calories_goal;
    int carbohydrates_goal;
    int fats_goal;
    int proteins_goal;
    int calories_current;
    int carbonates_current = 0;
    int fats_current = 0;
    int proteins_current = 0;


    public User(int id, String name, String surname, String email, String password, String BearerToken) {
        this.id = id;
        this.user_name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.BearerToken = BearerToken;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", user_name='" + user_name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", BearerToken='" + BearerToken + '\'' +
                ", picture=" + Arrays.toString(picture) +
                ", lunch_id=" + lunch_id +
                ", breakfast_id=" + breakfast_id +
                ", dinner_id=" + dinner_id +
                ", calories_goal=" + calories_goal +
                ", carbohydrates_goal=" + carbohydrates_goal +
                ", fats_goal=" + fats_goal +
                ", proteins_goal=" + proteins_goal +
                ", calories_current=" + calories_current +
                ", carbonates_current=" + carbonates_current +
                ", fats_current=" + fats_current +
                ", proteins_current=" + proteins_current +
                '}';
    }

    // Getters
    public String getName() {
        return user_name;
    }
    public String getSurname() {
        return surname;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public String getBearerToken() {
        return BearerToken;
    }
    public byte[] getPicture() {
        return picture;
    }
    public int getLunchId() {
        return lunch_id;
    }
    public int getBreakfastId() {
        return breakfast_id;
    }
    public int getDinnerId() {
        return dinner_id;
    }
    public int getCaloriesGoal() {
        return calories_goal;
    }
    public int getCarbonatesGoal() {
        return carbohydrates_goal;
    }
    public int getFatsGoal() {
        return fats_goal;
    }
    public int getProteinsGoal() {
        return proteins_goal;
    }
    public int getCaloriesCurrent() {
        return calories_current;
    }
    public int getCarbohydratesCurrent() {
        return carbonates_current;
    }
    public int getFatsCurrent() {
        return fats_current;
    }
    public int getProteinsCurrent() {
        return proteins_current;
    }


    // Setters
    public void setName(String name) {
        this.user_name = name;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setCaloriesCurrent(int calories_current) {
        this.calories_current = calories_current;
    }
    public void setCarbohydratesCurrent(int carbonates_current) {
        this.carbonates_current = carbonates_current;
    }
    public void setProteinsCurrent(int proteins_current) {
        this.proteins_current = proteins_current;
    }
    public void setFatsCurrent(int fats_current) {
        this.fats_current = fats_current;
    }

    public void setCaloriesGoal(int calories_goal) {
        this.calories_goal = calories_goal;
    }

    public void setCarbohydratesGoal(int carbohydrates_goal) {
        this.carbohydrates_goal = carbohydrates_goal;
    }

    public void setFatsGoal(int fats_goal) {
        this.fats_goal = fats_goal;
    }

    public void setProteinsGoal(int proteins_goal) {
        this.proteins_goal = proteins_goal;
    }
}
