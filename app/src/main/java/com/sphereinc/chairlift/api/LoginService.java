package com.sphereinc.chairlift.api;


import com.sphereinc.chairlift.api.entity.LoginDetails;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {

    @POST("/oauth/token")
    Call<LoginDetails> login(@Body HashMap<String, String> body);

    @POST("/oauth/token")
    Call<LoginDetails> relogin(@Body HashMap<String, String> body);

}
