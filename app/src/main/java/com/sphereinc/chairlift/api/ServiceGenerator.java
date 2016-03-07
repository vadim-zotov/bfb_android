package com.sphereinc.chairlift.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sphereinc.chairlift.api.deserialization.DateDeserializer;
import com.sphereinc.chairlift.api.deserialization.UserDeserializer;
import com.sphereinc.chairlift.api.entity.User;

import java.util.Date;

import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

public class ServiceGenerator {

    public static final String API_BASE_URL = "https://dev.betterfeedback.com";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(Date.class, new DateDeserializer())
//            .registerTypeAdapter(User.class, new UserDeserializer())
            .create();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson));

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }
}