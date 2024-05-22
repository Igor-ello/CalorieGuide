package com.obsessed.calorieguide.data.remote.network.user;

public class RegistrationRequest {
    private String user_name, surname, email, password;
    private int calories_goal, carbohydrates_goal, fats_goal, proteins_goal;

    public RegistrationRequest(String user_name, String surname, String email, String password,
                               int calories_goal, int carbs_goal, int proteins_goal, int fats_goal) {
        this.user_name = user_name;
        this.surname = surname;
        this.email = email;
        this.password = password;

        this.calories_goal = calories_goal;
        this.carbohydrates_goal = carbs_goal;
        this.proteins_goal = proteins_goal;
        this.fats_goal = fats_goal;
    }

    @Override
    public String toString() {
        return "RegistrationRequest{" +
                "user_name='" + user_name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", calories_goal='" + calories_goal + '\'' +
                ", carbs_goal='" + carbohydrates_goal + '\'' +
                ", fats_goal='" + fats_goal + '\'' +
                ", proteins_goal='" + proteins_goal + '\'' +
                '}';
    }
}
