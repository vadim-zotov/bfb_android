package com.sphereinc.chairlift.api.facadeimpl;

import com.sphereinc.chairlift.api.ServiceGenerator;
import com.sphereinc.chairlift.api.entity.User;
import com.sphereinc.chairlift.api.entity.response.UserSearchResult;
import com.sphereinc.chairlift.api.UserService;
import com.sphereinc.chairlift.api.facade.BaseFacade;
import com.sphereinc.chairlift.api.facade.UserFacade;

import retrofit2.Callback;

public class UserFacadeImpl extends BaseFacade implements UserFacade {
    UserService userService = ServiceGenerator.createService(UserService.class);

    @Override
    public void getMe(Callback<User> callback) {
        userService.getMe("company,department,job_role,location,manager,role,permissions,additionalSkills,required_skills,interests,objectives",
                getAuthorizationString()).enqueue(callback);
    }

    @Override
    public void getUser(int userId, Callback<User> callback) {
        userService.getUser(userId, "company,department,job_role,location,manager,role,permissions,additionalSkills,required_skills,interests,objectives",
                getAuthorizationString()).enqueue(callback);
    }

    @Override
    public void searchByName(String searchValue, Callback<UserSearchResult> callback) {
        userService.search("name", searchValue,
                "users.first_name", 100, 0, "", getAuthorizationString()).enqueue(callback);
    }

    @Override
    public void getDepartmentUsers(int departmentId, Callback<UserSearchResult> callback) {
        userService.search("name", "",
                "users.first_name", 100, 0, "filters=department_id|" + departmentId, getAuthorizationString()).enqueue(callback);
    }

}
