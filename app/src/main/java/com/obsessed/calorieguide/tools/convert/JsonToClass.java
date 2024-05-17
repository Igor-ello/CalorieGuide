package com.obsessed.calorieguide.tools.convert;

import com.google.gson.JsonObject;
import com.obsessed.calorieguide.data.models.User;

public class JsonToClass {
    public static User getUser(JsonObject jsonObject) {
        User user = new User(
                jsonObject.get("id").getAsInt(),
                jsonObject.get("user_name").getAsString(),
                jsonObject.get("surname").getAsString(),
                jsonObject.get("email").getAsString(),
                jsonObject.get("password").getAsString(),
                jsonObject.get("BearerToken").getAsString());
        return user;
    }
}
