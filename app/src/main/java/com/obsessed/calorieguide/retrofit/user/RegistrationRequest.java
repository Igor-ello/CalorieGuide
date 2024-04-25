package com.obsessed.calorieguide.retrofit.user;

public class RegistrationRequest {
    private String user_name;
    private String surname;
    private String email;
    private String password;

    public RegistrationRequest(String user_name, String surname, String email, String password) {
        this.user_name = user_name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "RegistrationRequest{" +
                "user_name='" + user_name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
