package com.sphereinc.chairlift.api.facade;

import com.sphereinc.chairlift.api.entity.User;
import com.sphereinc.chairlift.api.entity.response.UserSearchResult;

import retrofit2.Callback;

public interface UserFacade {

    void getMe(Callback<User> callback);

    void getUser(int userId, Callback<User> callback);

    void searchByName(String searchValue, Callback<UserSearchResult> callback);

    void getDepartmentUsers(int departmentId, Callback<UserSearchResult> callback);
}
