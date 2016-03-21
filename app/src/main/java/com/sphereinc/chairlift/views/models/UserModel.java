package com.sphereinc.chairlift.views.models;


import com.sphereinc.chairlift.api.entity.User;

import java.util.List;

public class UserModel implements TreeModel {
    private User user;

    public UserModel(User user) {
        this.user = user;
    }

    @Override
    public int getId() {
        if (user != null) {
            return user.getId();
        }
        return 0;
    }

    @Override
    public List<TreeModel> getChilds() {
        return null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
