package com.sphereinc.chairlift.common;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    private static Preferences mInstance;

    private Context mContext;

    public static Preferences getInstance() {

        if (mInstance == null)
            mInstance = new Preferences(ApplicationContextProvider.getContext());

        return mInstance;
    }

    private Preferences(Context context) {
        mContext = context;
    }

    private SharedPreferences getPrefs() {
        return mContext.getSharedPreferences("preferences",
                Context.MODE_PRIVATE);
    }

    public void clearStoredData() {
        setAccessToken("");
        setRefreshToken(null);
        setUserDepartmentId(-1);
        setUserName("");
        setUserMail("");
        setUserRole("");
        setUserAvatarUrl("");
        setRememberLogin(false);
    }

    private void saveString(String key, String value) {

        if ("null".equals(value))
            value = "";

        SharedPreferences prefs = getPrefs();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private void saveInt(String key, int value) {
        SharedPreferences prefs = getPrefs();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    private void saveLong(String key, long value) {
        SharedPreferences prefs = getPrefs();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    private void saveBoolean(String key, boolean value) {
        SharedPreferences prefs = getPrefs();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }


    public Preferences setRememberLogin(Boolean rememberLogin) {
        saveBoolean(Keys.REMEMBER_LOGIN, rememberLogin);
        return this;
    }

    public Preferences setAccessToken(String accessToken) {
        saveString(Keys.ACCESS_TOKEN, accessToken);
        return this;
    }

    public Preferences setRefreshToken(String refreshToken) {
        saveString(Keys.REFRESH_TOKEN, refreshToken);
        return this;
    }

    public Preferences setUserDepartmentId(int departmentId) {
        saveInt(Keys.USER_DEPARTMENT_ID, departmentId);
        return this;
    }

    public Preferences setUserName(String userName) {
        saveString(Keys.USER_NAME, userName);
        return this;
    }

    public Preferences setUserMail(String userMail) {
        saveString(Keys.USER_MAIL, userMail);
        return this;
    }

    public Preferences setUserAvatarUrl(String userAvatarUrl) {
        saveString(Keys.USER_AVATAR_URL, userAvatarUrl);
        return this;
    }

    public boolean rememberLogin() {
        return getPrefs().getBoolean(Keys.REMEMBER_LOGIN, false);
    }

    public Preferences setUserRole(String userRole) {
        saveString(Keys.USER_ROLE, userRole);
        return this;
    }

    public String accessToken() {
        return getPrefs().getString(Keys.ACCESS_TOKEN, null);
    }

    public String refreshToken() {
        return getPrefs().getString(Keys.REFRESH_TOKEN, null);
    }

    public int userDepartmentId() {
        return getPrefs().getInt(Keys.USER_DEPARTMENT_ID, -1);
    }

    public String userName() {
        return getPrefs().getString(Keys.USER_NAME, null);
    }

    public String userRole() {
        return getPrefs().getString(Keys.USER_ROLE, null);
    }

    public String userMail() {
        return getPrefs().getString(Keys.USER_MAIL, null);
    }

    public String userAvatarUrl() {
        return getPrefs().getString(Keys.USER_AVATAR_URL, null);
    }


}
