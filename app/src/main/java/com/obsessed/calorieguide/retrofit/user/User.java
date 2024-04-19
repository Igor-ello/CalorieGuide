package com.obsessed.calorieguide.retrofit.user;

public class User {
    int id;
    String user_name;
    String surname;
    String email;
    String password;
    String BearerToken;

    public User(int id, String name, String surname, String email, String password, String BearerToken) {
        this.id = id;
        this.user_name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.BearerToken = BearerToken;
    }

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
}
