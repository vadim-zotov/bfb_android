package com.sphereinc.chairlift.api.deserialization;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.sphereinc.chairlift.api.entity.User;
import com.sphereinc.chairlift.api.entity.response.DepartmentsSearchResult;

import java.lang.reflect.Type;

public class TestDeserializer implements JsonDeserializer<DepartmentsSearchResult> {
    @Override
    public DepartmentsSearchResult deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {

        //The deserialisation code is missing

        final User user = new User();
        user.setFirstName("Vadim");
        user.setLastName("Zotov");
        return new DepartmentsSearchResult();
    }
}
