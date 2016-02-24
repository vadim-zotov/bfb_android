package com.sphereinc.chairlift.api.deserialization;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.sphereinc.chairlift.api.entity.User;

import java.lang.reflect.Type;

public class UserDeserializer implements JsonDeserializer<User> {
    @Override
    public User deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {

        //The deserialisation code is missing

        final User user = new User();
        user.setFirstName("Vadim");
        user.setLastName("Zotov");
        return user;
    }
}
