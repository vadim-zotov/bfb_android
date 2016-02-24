package com.sphereinc.chairlift.api.facade;


import com.sphereinc.chairlift.common.Preferences;

public class BaseFacade {

    protected String getAuthorizationString() {
        return "Bearer " + Preferences.getInstance().accessToken();
    }
}
