package com.sphereinc.chairlift.api.facadeimpl;

import com.sphereinc.chairlift.api.LoginService;
import com.sphereinc.chairlift.api.ServiceGenerator;
import com.sphereinc.chairlift.api.entity.LoginDetails;
import com.sphereinc.chairlift.api.facade.BaseFacade;
import com.sphereinc.chairlift.api.facade.LoginFacade;
import com.sphereinc.chairlift.common.Preferences;

import java.util.HashMap;

import retrofit2.Callback;

public class LoginFacadeImpl extends BaseFacade implements LoginFacade {
    LoginService loginService = ServiceGenerator.createService(LoginService.class);

    @Override
    public void login(String email, String password, Callback<LoginDetails> callback) {
        HashMap<String, String> gson = new HashMap<>();
        gson.put("grant_type", "password");
        gson.put("username", email);
        gson.put("password", password);

        loginService.login(gson).enqueue(callback);
    }

    @Override
    public void relogin(Callback<LoginDetails> callback) {
        HashMap<String, String> gson = new HashMap<>();
        gson.put("grant_type", "refresh_token");
        gson.put("refresh_token", Preferences.getInstance().refreshToken());
        loginService.relogin(gson).enqueue(callback);
    }
}
