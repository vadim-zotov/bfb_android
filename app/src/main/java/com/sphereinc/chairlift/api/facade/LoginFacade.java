package com.sphereinc.chairlift.api.facade;

import com.sphereinc.chairlift.api.entity.LoginDetails;

import retrofit2.Callback;

public interface LoginFacade {
    void login(String email, String password, Callback<LoginDetails> callback);
    void relogin(Callback<LoginDetails> callback);
}
